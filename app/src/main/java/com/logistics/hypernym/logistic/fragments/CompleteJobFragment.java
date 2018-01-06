package com.logistics.hypernym.logistic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.logistics.hypernym.logistic.api.ApiInterface;
import com.logistics.hypernym.logistic.models.JobInfo_;
import com.logistics.hypernym.logistic.models.Respone_Completed_job;
import com.logistics.hypernym.logistic.models.WebAPIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shamis on 10-Dec-17.
 */


public class CompleteJobFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CompleteJobAdapter completeJobAdapter;
    private List<JobInfo_> jobInfo_s;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_view_pager_compltd,container,false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_complete);
        layoutManager=new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);


        ApiInterface.retrofit.getalldata(12,55).enqueue(new Callback<WebAPIResponse<Respone_Completed_job>>() {
            @Override
            public void onResponse(Call<WebAPIResponse<Respone_Completed_job>> call, Response<WebAPIResponse<Respone_Completed_job>> response) {
                if (response.body().status){
//                    Toast.makeText(getContext(), "Response GGetS", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getContext(), "List Detail"+Integer.toString(response.body().response.job_info.size()), Toast.LENGTH_SHORT).show();


                    jobInfo_s=response.body().response.job_info;

                    completeJobAdapter=new CompleteJobAdapter(jobInfo_s);
                    recyclerView.setAdapter(completeJobAdapter);
//                    compltdgyjy_job.setText(Integer.toString(jobInfo_s.size()));


//                   Integer[] hhdh=new Integer[jobInfo_s.size()];
///

                   // compltd_job.setText(hhdh.length);
//                   for (int i = 0; i < jobInfo_s.size(); i++) {
//                       hhdh[i] = jobInfo_s.get(i).getJob_status();
//
//                       Toast.makeText(getContext(), hhdh[i], Toast.LENGTH_SHORT).show();


     //              }
                }
            }

            @Override
            public void onFailure(Call<WebAPIResponse<Respone_Completed_job>> call, Throwable t) {
                Toast.makeText(getContext(), "Network failure", Toast.LENGTH_SHORT).show();
            }
        });

       // CompleteJobAdapter completeJobAdapter=new CompleteJobAdapter();
        //recyclerView.setAdapter(completeJobAdapter);
       // recyclerView1.setAdapter(truckAdapter);

     //   recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

//    public void add()
//    {
//
//        CompletedJobData listitem=new CompletedJobData("xyz","complete","12:00","02:00");
//        completedJobDatas.add(listitem);
//
//
//    }


}
