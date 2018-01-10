package com.logistics.hypernym.logistic;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.logistics.hypernym.logistic.adapters.AssetsAdapter;
import com.logistics.hypernym.logistic.models.AssestData;
import com.shitij.goyal.slidebutton.SwipeButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shamis on 15-Dec-17.
 */

public class ActiveJobActivity extends AppCompatActivity {
    private List<AssestData> assestData = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    ImageView info_img;
    Dialog summary,info;
    SwipeButton swipeButton;
    Button btn_okk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_job);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        adapter=new AssetsAdapter(assestData,this);
        adapter.notifyItemInserted(0);
        recyclerView.getLayoutManager().scrollToPosition(adapter.getItemCount());
        recyclerView.setAdapter(adapter);

        swipeButton = (SwipeButton)findViewById(R.id.btn_slide);
        info_img=(ImageView)findViewById(R.id.info);

        info_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info = new Dialog(ActiveJobActivity.this);
                info.setContentView(R.layout.dialog_truck_info);
                info.show();
                info.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
            }
        });

        swipeButton.addOnSwipeCallback(new SwipeButton.Swipe() {
            @Override
            public void onButtonPress() {

            }

            @Override
            public void onSwipeCancel() {

            }

            @Override
            public void onSwipeConfirm() {
                summary = new Dialog(ActiveJobActivity.this);
                summary.setContentView(R.layout.dialog_summary_detail);
                btn_okk= (Button)summary.findViewById(R.id.btn_ok);

                summary.show();
                summary.getWindow().setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
                btn_okk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        summary.hide();
                        Intent intent=new Intent(ActiveJobActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
        add();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeActivity.class));
    }

    public void add()
    {
        AssestData listitem=new AssestData("abc","Vehicle");
        assestData.add(listitem);
        AssestData listitem1=new AssestData("abc","Vehicle");
        assestData.add(listitem1);
        AssestData listite2=new AssestData("abc","Vehicle");
        assestData.add(listite2);

    }
}