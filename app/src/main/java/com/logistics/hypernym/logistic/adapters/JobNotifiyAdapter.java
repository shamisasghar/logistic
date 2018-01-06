package com.logistics.hypernym.logistic.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.hypernym.logistic.JobDetailActivity;
import com.logistics.hypernym.logistic.JobNotifyActivity;
import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.models.NotificationData;

import java.util.List;

/**
 * Created by Metis on 14-Dec-17.
 */

public class JobNotifiyAdapter extends RecyclerView.Adapter<JobNotifiyAdapter.ViewHolder> {
    private List<NotificationData> notificationDatas;

    public Context context;
    public JobNotifiyAdapter(List<NotificationData> truckData, Context context) {
        this.notificationDatas = truckData;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_notify, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(JobNotifiyAdapter.ViewHolder holder, int position) {
        NotificationData nm=notificationDatas.get(position);
        holder.job.setText(nm.getTitle());
    }
    @Override
    public int getItemCount() {
        return notificationDatas.size();}
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView job;
        ImageView imageView;
        private   ViewHolder(final View itemView) {
            super(itemView);

            job = (TextView) itemView.findViewById(R.id.jobname);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            String j=job.getText().toString();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  Toast.makeText(context, "notify", Toast.LENGTH_SHORT).show();

//                    if(job.getText().toString().equals("Job Notification")) {
//                        Toast.makeText(context, job.getText(),Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(context, JobDetailActivity.class);
                       context.startActivity(intent);

                    ((Activity)context).finish();


//                    }
//
//                    else
//                    {
//                        Toast.makeText(context, "not selected", Toast.LENGTH_SHORT).show();
//                    }
                }
            });

        }

    }
}