package com.logistics.hypernym.logistic.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logistics.hypernym.logistic.FrameActivity;
import com.logistics.hypernym.logistic.HomeActivity;
import com.logistics.hypernym.logistic.LoginActivity;
import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.adapters.CompleteJobAdapter;
import com.logistics.hypernym.logistic.api.ApiInterface;
import com.logistics.hypernym.logistic.models.DrawerItemSelectedEvent;
import com.logistics.hypernym.logistic.models.Profile;
import com.logistics.hypernym.logistic.models.Respone_Completed_job;
import com.logistics.hypernym.logistic.models.WebAPIResponse;
import com.logistics.hypernym.logistic.toolbox.ToolbarListener;
import com.logistics.hypernym.logistic.utils.ActivityUtils;
import com.logistics.hypernym.logistic.utils.LoginUtils;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by shamis on 14-Dec-17.
 */

public class Profile_Fragment extends Fragment implements View.OnClickListener, ToolbarListener {
    private ViewHolder mHolder;
    TextView email, drivername, driverid;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private SwipeRefreshLayout swipelayout;
    SharedPreferences pref;
    String getUserAssociatedEntity,Email,Driver_name,Driver_id,Driver_photo;
    CircleImageView img_profile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Profile");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        email = (TextView) view.findViewById(R.id.txt_Email);
        drivername = (TextView) view.findViewById(R.id.txt_drivername);
        driverid = (TextView) view.findViewById(R.id.txt_driverid);
        img_profile=(CircleImageView) view.findViewById(R.id.img_driver_profile);
        swipelayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe);
        pref = getActivity().getSharedPreferences("TAG", MODE_PRIVATE);

        Email = pref.getString("Email", "");
        Driver_photo = pref.getString("Url", "");
        Driver_name = pref.getString("Name", "");
        Driver_id = pref.getString("Id", "");
        email.setText(Email);
        Glide.with(getContext()).load(Driver_photo).into(img_profile);
        drivername.setText(Driver_name);
        driverid.setText(Driver_id);
        swipelayout();

//        ApiInterface.retrofit.getprofile(12).enqueue(new Callback<WebAPIResponse<Profile>>() {
//            @Override
//            public void onResponse(Call<WebAPIResponse<Profile>> call, Response<WebAPIResponse<Profile>> response) {
//                if (response.body().status) {
//
//                    String driverName, driverId;
//                    String url=response.body().response.getPhoto();
//                    driverName = response.body().response.getName();
//                    driverId = Integer.toString(response.body().response.getId());
//                    drivername.setText(driverName);
//                    driverid.setText(driverId);
//                    Glide.with(getContext()).load(url).into(img_profile);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WebAPIResponse<Profile>> call, Throwable t) {
//
//                Snackbar snackbar = Snackbar.make(swipelayout, "Establish Network Connection!", Snackbar.LENGTH_SHORT);
//                View sbView = snackbar.getView();
//                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
//                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDialogToolbarText));
//                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                snackbar.show();
//            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        EventBus.getDefault().post(new DrawerItemSelectedEvent(getString(R.string.drawer_profile)));
        mHolder.btn_sgnout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signout:
                LoginUtils.clearUser(getContext());
                startActivity(new Intent(this.getContext(), LoginActivity.class));
                getActivity().finish();
                break;
        }

    }

    @Override
    public void setTitle(String title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new DrawerItemSelectedEvent(getString(R.string.drawer_profile)));
    }

    public static class ViewHolder {

        Button btn_sgnout;

        public ViewHolder(View view) {
            btn_sgnout = (Button) view.findViewById(R.id.btn_signout);

        }
    }

    public void swipelayout() {
        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipelayout.setRefreshing(true);


                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipelayout.setRefreshing(false);

                        getUserAssociatedEntity = LoginUtils.getUserAssociatedEntity(getContext());
                        ApiInterface.retrofit.getprofile(Integer.parseInt(getUserAssociatedEntity)).enqueue(new Callback<WebAPIResponse<Profile>>() {
                            @Override
                            public void onResponse(Call<WebAPIResponse<Profile>> call, Response<WebAPIResponse<Profile>> response) {
                                if (response.body().status) {

                                    String driverName,driverId;

                                    driverName = response.body().response.getName();
                                    driverId = Integer.toString(response.body().response.getId());
                                    String url=response.body().response.getPhoto();
                                    drivername.setText(driverName);
                                    driverid.setText(driverId);
                                    Glide.with(getContext()).load(url).into(img_profile);

                                }
                            }

                            @Override
                            public void onFailure(Call<WebAPIResponse<Profile>> call, Throwable t) {

                                Snackbar snackbar = Snackbar.make(swipelayout, "Establish Network Connection!", Snackbar.LENGTH_SHORT);
                                View sbView = snackbar.getView();
                                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                sbView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDialogToolbarText));
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                snackbar.show();
                            }
                        });
                    }
                }, 3000);
            }
        });


    }


}

