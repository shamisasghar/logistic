package com.logistics.hypernym.logistic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.adapters.CompleteJobAdapter;
import com.logistics.hypernym.logistic.adapters.FailedJobAdapter;
import com.logistics.hypernym.logistic.api.ApiInterface;
import com.logistics.hypernym.logistic.models.JobInfo_;
import com.logistics.hypernym.logistic.models.Respone_Completed_job;
import com.logistics.hypernym.logistic.models.WebAPIResponse;
import com.logistics.hypernym.logistic.utils.LoginUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shamis on 10-Dec-17.
 */

public class FailedJobFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FailedJobAdapter failedJobAdapter;
    private List<JobInfo_> jobInfo_s;
    String getUserAssociatedEntity;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_view_pager_failed,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_complete);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getUserAssociatedEntity= LoginUtils.getUserAssociatedEntity(getContext());
        ApiInterface.retrofit.getalldata(Integer.parseInt(getUserAssociatedEntity),54).enqueue(new Callback<WebAPIResponse<Respone_Completed_job>>() {
            @Override
            public void onResponse(Call<WebAPIResponse<Respone_Completed_job>> call, Response<WebAPIResponse<Respone_Completed_job>> response) {
                if (response.body().status){

                   Toast.makeText(getContext(), "List Detail"+Integer.toString(response.body().response.job_info.size()), Toast.LENGTH_SHORT).show();
                    jobInfo_s=response.body().response.job_info;
                    failedJobAdapter=new FailedJobAdapter(jobInfo_s);
                    recyclerView.setAdapter(failedJobAdapter);
                }
            }

            @Override
            public void onFailure(Call<WebAPIResponse<Respone_Completed_job>> call, Throwable t) {
            }
        });

        return view;
    }

}