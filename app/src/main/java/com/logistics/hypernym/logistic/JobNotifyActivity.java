package com.logistics.hypernym.logistic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.adapters.JobNotifiyAdapter;
import com.logistics.hypernym.logistic.models.NotificationData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Metis on 14-Dec-17.
 */

public class JobNotifyActivity  extends AppCompatActivity
{
    ArrayList<NotificationData> notificationDatas = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_job_notify);
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notification_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new JobNotifiyAdapter(notificationDatas,this);
        recyclerView.setAdapter(adapter);


        NotificationData listitem = new NotificationData("Job Notification", R.drawable.jobnotify);
        notificationDatas.add(listitem);
        listitem = new NotificationData("Job Notification", R.drawable.jobnotify);
        notificationDatas.add(listitem);
        listitem = new NotificationData("Job Notification", R.drawable.jobnotify);
        notificationDatas.add(listitem);
        listitem = new NotificationData("Job Notification", R.drawable.jobnotify);
        notificationDatas.add(listitem);
        listitem = new NotificationData("Job Notification", R.drawable.jobnotify);
        notificationDatas.add(listitem);
        listitem = new NotificationData("Job Notification", R.drawable.jobnotify);
        notificationDatas.add(listitem);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationDatas);
        editor.putString("list", json);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeActivity.class));
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
        finish();
    }

}

