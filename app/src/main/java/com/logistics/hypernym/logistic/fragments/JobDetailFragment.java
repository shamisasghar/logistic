package com.logistics.hypernym.logistic.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.hypernym.logistic.ActiveJobActivity;
import com.logistics.hypernym.logistic.FrameActivity;
import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.api.ApiInterface;
import com.logistics.hypernym.logistic.models.JobDetail;
import com.logistics.hypernym.logistic.models.PayloadNotification;
import com.logistics.hypernym.logistic.models.StartJob;
import com.logistics.hypernym.logistic.models.WebAPIResponse;
import com.logistics.hypernym.logistic.toolbox.ToolbarListener;
import com.logistics.hypernym.logistic.utils.ActivityUtils;
import com.logistics.hypernym.logistic.utils.AppUtils;
import com.logistics.hypernym.logistic.utils.Constants;
import com.logistics.hypernym.logistic.utils.GsonUtils;
import com.logistics.hypernym.logistic.utils.LoginUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Metis on 15-Jan-18.
 */

public class JobDetailFragment extends Fragment {
    View view;
    Context fContext;
    Button btn_start, btn_cancel;
    TextView jbname, jbstatus, jbstart, jbend, decrptin;
    String getUserAssociatedEntity, actual_start_time;
    private SwipeRefreshLayout swipelayout;
    PayloadNotification payloadNotification;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fContext = context;
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Job Details");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_job_detail, container, false);

        btn_start = (Button) view.findViewById(R.id.btn_startjob);
        btn_cancel = (Button) view.findViewById(R.id.btn_canceljob);
        swipelayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe);
        jbname = (TextView) view.findViewById(R.id.txt_jobname);
        jbstatus = (TextView) view.findViewById(R.id.txt_status);
        jbstart = (TextView) view.findViewById(R.id.txt_starttime);
        jbend = (TextView) view.findViewById(R.id.txt_endtime);
        decrptin = (TextView) view.findViewById(R.id.txt_description);
        getUserAssociatedEntity = LoginUtils.getUserAssociatedEntity(getActivity());
        pref = getActivity().getSharedPreferences("TAG", MODE_PRIVATE);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        actual_start_time = df.format(c.getTime());
//         Now formattedDate have current date/time
       // Toast.makeText(getActivity(), actual_start_time, Toast.LENGTH_SHORT).show();


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("job_id",13);
                body.put("actual_start_time", actual_start_time);
                body.put("driver_id", Integer.parseInt(getUserAssociatedEntity));

                ApiInterface.retrofit.startjob(body).enqueue(new Callback<WebAPIResponse<StartJob>>() {
                    @Override
                    public void onResponse(Call<WebAPIResponse<StartJob>> call, Response<WebAPIResponse<StartJob>> response) {
//                        if (response.body().status) {
                        String strlat,strlng,endlat,endlng;
                        strlat= String.valueOf(response.body().response.getJobStartLat());
                        strlng= String.valueOf(response.body().response.getJobStartLng());
                        endlat= String.valueOf(response.body().response.getJobEndLat());
                        endlng= String.valueOf(response.body().response.getJobEndLng());

                        editor = pref.edit();
                        editor.putString("Startlat", strlat);
                        editor.putString("Startlng", strlng);
                        editor.putString("Endlat", endlat);
                        editor.putString("Endlng", endlng);
                        editor.putString("Actualstart", actual_start_time);
                        editor.commit();

                        Intent intent = new Intent(getContext(), ActiveJobActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }

                    @Override
                    public void onFailure(Call<WebAPIResponse<StartJob>> call, Throwable t) {

                        Snackbar snackbar = Snackbar.make(swipelayout, "Establish Network Connection!", Snackbar.LENGTH_SHORT);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDialogToolbarText));
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        snackbar.show();
                    }
                });


            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameActivity frameActivity=(FrameActivity)getActivity();
                frameActivity.onBackPressed();

             //   ActivityUtils.startActivityForResult(getActivity(), FrameActivity.class, JobNoticationFragment.class.getName(), null, 1);
//                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        Bundle extras = getActivity().getIntent().getExtras();
        Bundle value = null;
        if (extras != null) {
            value = extras.getBundle(Constants.DATA);
            if (value != null) {
                payloadNotification = GsonUtils.fromJson(value.getString(Constants.PAYLOAD), PayloadNotification.class);
            }
            else {
                ApiInterface.retrofit.getalldata(13).enqueue(new Callback<WebAPIResponse<JobDetail>>() {
                    @Override
                    public void onResponse(Call<WebAPIResponse<JobDetail>> call, Response<WebAPIResponse<JobDetail>> response) {
                        if (response.body().status) {

                            jbname.setText(response.body().response.getName());
                            jbstatus.setText(response.body().response.getStatus());
                            jbstart.setText(AppUtils.getFormattedDate(response.body().response.getJobStartDatetime()) + " " + AppUtils.getTime(response.body().response.getJobStartDatetime()));
                            jbend.setText(AppUtils.getFormattedDate(response.body().response.getJobEndDatetime()) + " " + AppUtils.getTime(response.body().response.getJobEndDatetime()));
                            decrptin.setText(response.body().response.getDescription());
                            String strttime,endtime;

                            strttime=AppUtils.getFormattedDate(response.body().response.getJobStartDatetime()) + " " + AppUtils.getTime(response.body().response.getJobStartDatetime());
                            endtime=AppUtils.getFormattedDate(response.body().response.getJobEndDatetime()) + " " + AppUtils.getTime(response.body().response.getJobEndDatetime());
                            editor = pref.edit();
                           editor.putString("Startjob",strttime);
                           editor.putString("Startend",endtime);
                           editor.commit();

                        }
                    }

                    @Override
                    public void onFailure(Call<WebAPIResponse<JobDetail>> call, Throwable t) {

                        Snackbar snackbar = Snackbar.make(swipelayout, "Establish Network Connection!", Snackbar.LENGTH_SHORT);
                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDialogToolbarText));
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        snackbar.show();
                    }
                });
            }
        }


        return view;

    }


}
