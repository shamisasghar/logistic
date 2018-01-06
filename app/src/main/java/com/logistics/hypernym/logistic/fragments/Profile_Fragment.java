package com.logistics.hypernym.logistic.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.logistics.hypernym.logistic.FrameActivity;
import com.logistics.hypernym.logistic.HomeActivity;
import com.logistics.hypernym.logistic.LoginActivity;
import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.models.DrawerItemSelectedEvent;
import com.logistics.hypernym.logistic.toolbox.ToolbarListener;
import com.logistics.hypernym.logistic.utils.ActivityUtils;
import com.logistics.hypernym.logistic.utils.LoginUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by shamis on 14-Dec-17.
 */

public class Profile_Fragment extends Fragment implements View.OnClickListener,ToolbarListener {
    private ViewHolder mHolder;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

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
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        EventBus.getDefault().post(new DrawerItemSelectedEvent(getString(R.string.drawer_profile)));
        mHolder.btn_sgnout.setOnClickListener(this);

//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
   //     ActivityUtils.startActivity(getActivity(), FrameActivity.class,HomeFragment.class.getName(),null);
        switch (view.getId())
        {
            case R.id.btn_signout:
                LoginUtils.clearUser(getContext());
                startActivity(new Intent(this.getContext(),LoginActivity.class));
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
}

