package com.logistics.hypernym.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.logistics.hypernym.logistic.adapters.JobNotifiyAdapter;
import com.logistics.hypernym.logistic.fragments.JobFragment;
import com.logistics.hypernym.logistic.models.NotificationData;
import com.logistics.hypernym.logistic.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamis on 15-Dec-17.
 */

public class JobDetailActivity  extends AppCompatActivity {

    ImageView img_bk_arrow;
    Button btn_start,btn_cancel;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityUtils.startActivityForResult(this, JobDetailActivity.class,HomeActivity.class.getName(),null,1 );
//        Intent intent=new Intent(JobDetailActivity.this,JobNotifyActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_type);
        getSupportActionBar().hide();

        img_bk_arrow=(ImageView)findViewById(R.id.backarrow);
        btn_start=(Button) findViewById(R.id.btn_startjob);
        btn_cancel=(Button) findViewById(R.id.btn_canceljob);

        img_bk_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobDetailActivity.this,JobNotifyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobDetailActivity.this,ActiveJobActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JobDetailActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}