package com.logistics.hypernym.logistic.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logistics.hypernym.logistic.FrameActivity;
import com.logistics.hypernym.logistic.JobNotifyActivity;
import com.logistics.hypernym.logistic.R;
import com.logistics.hypernym.logistic.models.DrawerItemSelectedEvent;
import com.logistics.hypernym.logistic.models.NotificationData;
import com.logistics.hypernym.logistic.toolbox.ToolbarListener;
import com.logistics.hypernym.logistic.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by shamis on 08-Dec-17.
 */

public class HomeFragment extends Fragment implements View.OnClickListener,OnMapReadyCallback{
    private ViewHolder mHolder;
    MapView mMapView;
    private GoogleMap googleMap;
    LocationManager locationManager;
    Context fContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fContext=context;
        if (context instanceof ToolbarListener) {
            ((ToolbarListener) context).setTitle("Home");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapView);


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng pos;
                    Location l;
                    l = (Location) location;
                    pos = new LatLng(l.getLatitude(), l.getLongitude());
                    if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    if (googleMap!=null){
                        googleMap.setMyLocationEnabled(true);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15.4f));
                        googleMap.setTrafficEnabled(true);
                    }


//                 googleMap.addMarker(new MarkerOptions().position(pos).title("Driver Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.maptruck)));

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });

        }
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);
        return view;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHolder = new ViewHolder(view);
        EventBus.getDefault().post(new DrawerItemSelectedEvent(getString(R.string.drawer_home)));
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new DrawerItemSelectedEvent(getString(R.string.drawer_home)));
    }

    @Override
    public void onClick(View view) {
    //    ActivityUtils.startActivity(getActivity(), FrameActivity.class,DistanceFragment.class.getName(),null);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
    }

    public static class ViewHolder {


        public ViewHolder(final View view) {


        }

    }

}