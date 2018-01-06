package com.logistics.hypernym.logistic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logistics.hypernym.logistic.adapters.DrawerAdapter;
import com.logistics.hypernym.logistic.dialogs.SimpleDialog;
import com.logistics.hypernym.logistic.enumerations.AnimationEnum;
import com.logistics.hypernym.logistic.fragments.DistanceFragment;
import com.logistics.hypernym.logistic.fragments.HomeFragment;
import com.logistics.hypernym.logistic.fragments.JobFragment;
import com.logistics.hypernym.logistic.fragments.Profile_Fragment;
import com.logistics.hypernym.logistic.models.Drawer;
import com.logistics.hypernym.logistic.models.DrawerItemSelectedEvent;
import com.logistics.hypernym.logistic.models.NotificationData;
import com.logistics.hypernym.logistic.toolbox.OnItemClickListener;
import com.logistics.hypernym.logistic.toolbox.ToolbarListener;
import com.logistics.hypernym.logistic.utils.ActivityUtils;
import com.logistics.hypernym.logistic.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * Created by shamis on 08-Dec-17.
 */

public class HomeActivity extends AppCompatActivity implements ToolbarListener, OnItemClickListener {
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private RecyclerView mRecyclerView;
    private DrawerAdapter mDrawerAdapter;
    private TextView mNumberOfCartItemsText;
    private BlurView mBlurView;
    private final float mRadius = 10;
    SharedPreferences sharedPreferences;
    ArrayList<NotificationData> infoList;
    private SimpleDialog mSimpleDialog;
    private RelativeLayout mBranchSelectionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mBlurView = (BlurView) findViewById(R.id.blurView);


        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list", null);
        Type type = new TypeToken<ArrayList<NotificationData>>() {}.getType();
        infoList = gson.fromJson(json, type);


        toolbarSetup();
        initDrawer();
        String fragmentName = getIntent().getStringExtra(Constants.FRAGMENT_NAME);
        Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
        if (!TextUtils.isEmpty(fragmentName)) {
            Fragment fragment = Fragment.instantiate(this, fragmentName);
            if (bundle != null)
                fragment.setArguments(bundle);
            addFragment(fragment);
        } else {
            addFragment(new HomeFragment());
        }
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    private void initDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mRecyclerView = (RecyclerView) navigationView.findViewById(R.id.recycler_drawer);
        mDrawerAdapter = new DrawerAdapter(this);
        mDrawerAdapter.addAll(getData());
        mRecyclerView.setAdapter(mDrawerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final View decorView = getWindow().getDecorView();
        //Activity's root View. Can also be root View of your layout
        final View rootView = decorView.findViewById(android.R.id.content);
        //set background, if your root layout doesn't have one
        final Drawable
                windowBackground = decorView.getBackground();

        mBlurView.setupWith(rootView)
                .windowBackground(windowBackground)
                .blurAlgorithm(new RenderScriptBlur(this, true)) //Preferable algorithm, needs RenderScript support mode enabled
                .blurRadius(mRadius);
    }

    public void addFragment(final Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mSimpleDialog = new SimpleDialog(this, null, getString(R.string.msg_exit),
                    getString(R.string.button_cancel), getString(R.string.button_ok), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.button_positive:
                            mSimpleDialog.dismiss();
                            HomeActivity.this.finish();
                            break;
                        case R.id.button_negative:
                            mSimpleDialog.dismiss();
                            break;
                    }
                }
            });
            mSimpleDialog.show();
        }
    }

    @Override
    public void setTitle(String title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    public void toolbarSetup() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(" ");
        ActivityUtils.centerToolbarTitle(mToolbar,true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private List<Drawer> getData() {
        List<Drawer> mItems = new ArrayList<>();
        mItems.add(new Drawer(R.drawable.home, getString(R.string.drawer_home), true));
        mItems.add(new Drawer(R.drawable.job, getString(R.string.drawer_job), false));
        mItems.add(new Drawer(R.drawable.distance, getString(R.string.drawer_distance), false));
        mItems.add(new Drawer(R.drawable.config_ic_drawer_profile, getString(R.string.drawer_profile), false));
        return mItems;
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        Drawer item = (Drawer) data;
        if (item.isSelected) {
            closeDrawer();
            return;
        }
        switch (position) {
            case 0:
                addFragment(new HomeFragment());
                break;
            case 1:
                addFragment(new JobFragment());
                break;
            case 2:
                addFragment(new DistanceFragment());
                break;
            case 3:
                addFragment(new Profile_Fragment());
                break;
            case 4:


                break;
        }
        closeDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!WebSocketService.isWebSocketConnected) {
//            WebSocketService.WS();
//        }
    }

    private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);


        View view = menu.findItem(R.id.notification_bell).getActionView();
       mNumberOfCartItemsText = (TextView) view.findViewById(R.id.text_number_of_cart_items);

        if ( infoList== null) {
           mNumberOfCartItemsText.setText("0");
        } else {
           mNumberOfCartItemsText.setText(String.valueOf(infoList.size()));
       }
       view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),JobNotifyActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                finish();

            }
        });
//        ImageView cartImage = (ImageView) view.findViewById(R.id.image_cart);
//        cartImage.setColorFilter(ContextCompat.getColor(this, R.color.colorToolbarIcon));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.image_cart:
                break;
//                // app icon in action bar clicked; goto parent activity.
//                this.finish();
//                return true;
//            case R.id.action_cart:
//                break;
//            case R.id.action_settings:
//                AppUtils.makeToast(this, getString(R.string.msg_settings_not_available));
//                break;
//            default:
                // ...
        }
        return super.onOptionsItemSelected(item);
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(DrawerItemSelectedEvent event) {
        
        for (int i = 0; i < mDrawerAdapter.getItemCount(); i++) {
            if (mDrawerAdapter.getItems().get(i).title.equalsIgnoreCase(event.drawerTitle)) {
                mDrawerAdapter.getItems().get(i).isSelected = true;
            } else {
                mDrawerAdapter.getItems().get(i).isSelected = false;
            }
        }
        mDrawerAdapter.notifyDataSetChanged();
//        Toast.makeText(this, "what is you problem", Toast.LENGTH_SHORT).show();
    }
}
