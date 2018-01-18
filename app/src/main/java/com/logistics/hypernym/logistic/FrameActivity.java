package com.logistics.hypernym.logistic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.hypernym.logistic.dialogs.SimpleDialog;
import com.logistics.hypernym.logistic.fragments.JobFragment;
import com.logistics.hypernym.logistic.fragments.JobNoticationFragment;
import com.logistics.hypernym.logistic.toolbox.ToolbarListener;
import com.logistics.hypernym.logistic.utils.ActivityUtils;
import com.logistics.hypernym.logistic.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by shamis on 08-Dec-17.
 */

public class FrameActivity extends AppCompatActivity implements ToolbarListener {
    private Toolbar mToolbar;
    private SimpleDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        View view = findViewById(R.id.drawer_layout);
        toolbarSetup();
        String fragmentName = getIntent().getStringExtra(Constants.FRAGMENT_NAME);
        Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
        if (!TextUtils.isEmpty(fragmentName)) {
            Fragment fragment = Fragment.instantiate(this, fragmentName);
            if (bundle != null)
                fragment.setArguments(bundle);
            addFragment(fragment);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void toolbarSetup()
    {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle(" ");
        ActivityUtils.centerToolbarTitle(mToolbar,true);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            Intent intent = new Intent(FrameActivity.this, HomeActivity.class);
            startActivity(intent);
            FrameActivity.this.finish();
//            ActivityUtils.startHomeActivity(this, HomeActivity.class, CategoriesFragment.class.getName());
        }
        else {
            FrameActivity.this.finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//        View view = menu.findItem(R.id.action_cart).getActionView();
//        mNumberOfCartItemsText = (TextView) view.findViewById(R.id.text_number_of_cart_items);
//        //---------------------------
//        List<Cart> list;
//        list = ScheduleUtils.getCart(this);
//        if (list == null) {
//            mNumberOfCartItemsText.setText("0");
//        } else {
//            mNumberOfCartItemsText.setText(String.valueOf(list.size()));
//        }
//
//        //------------------
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtils.startActivity(FrameActivity.this, FrameActivity.class, MyCartFragment.class.getName(), null, AnimationEnum.VERTICAL);
//            }
//        });
//        ImageView cartImage = (ImageView) view.findViewById(R.id.image_cart);
//        cartImage.setColorFilter(ContextCompat.getColor(this, R.color.colorToolbarIcon));
//        return true;
//    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            case R.id.notification_bell:
                startActivity(new Intent(this,JobNoticationFragment.class));
                finish();
              //  ActivityUtils.startActivity(this.getApplicationContext(), FrameActivity.class,JobNotifyActivity.class.getName(),null);
                  return true;
            default:
                // ...
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
