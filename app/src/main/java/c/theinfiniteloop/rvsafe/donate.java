package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class donate extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    public static boolean button1pressed =false;
    public static boolean button2pressed =false;
    public static boolean button3pressed =false;
    public static boolean button4pressed =false;


    public static donate newInstance()
    {
        donate fragment = new donate();
        return fragment;
    }





    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donate);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_donate, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final Button button1=view.findViewById(R.id.button1);
        final Button button2=view.findViewById(R.id.button2);
        final Button button3=view.findViewById(R.id.button3);
        final Button button4=view.findViewById(R.id.button4);
        Button button5=view.findViewById(R.id.button5);




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(button1pressed)
                {


                     button1.setBackgroundResource(R.drawable.oval_button_white);

                    button1pressed=false;
                }
                else
                {
                    button1.setBackgroundResource(R.drawable.oval_button_light_green);
                    button1pressed=true;
                }


            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(button2pressed)
                {


                    button2.setBackgroundResource(R.drawable.oval_button_white);

                    button2pressed=false;
                }
                else
                {
                    button2.setBackgroundResource(R.drawable.oval_button_light_green);
                    button2pressed=true;
                }


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(button3pressed)
                {


                    button3.setBackgroundResource(R.drawable.oval_button_white);

                    button3pressed=false;
                }
                else
                {
                    button3.setBackgroundResource(R.drawable.oval_button_light_green);
                    button3pressed=true;
                }


            }
        });



        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(button4pressed)
                {


                    button4.setBackgroundResource(R.drawable.oval_button_white);

                    button4pressed=false;
                }
                else
                {
                    button4.setBackgroundResource(R.drawable.oval_button_light_green);
                    button4pressed=true;
                }


            }
        });










        return view;

    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

      LatLng NGO1 = new LatLng(-34.0, 151.0);
//      //  mMap.addMarker(new MarkerOptions().position(india).title("Marker in India"));
//
//        Circle circle=mMap.addCircle(new CircleOptions()
//        .center(NGO1)
//        .radius(10000)
//        .strokeColor(Color.parseColor("#FF0000"))
//        .fillColor(Color.parseColor("#33FF0000"))
//        );
//
//        LatLng NGO2 = new LatLng(-50.0, 500.0);
//        //  mMap.addMarker(new MarkerOptions().position(india).title("Marker in India"));
//
//        Circle circle2=mMap.addCircle(new CircleOptions()
//                .center(NGO1)
//                .radius(10000)
//                .strokeColor(Color.parseColor("#00FF00"))
//                .fillColor(Color.parseColor("#3300FF00"))
//        );
//
//        Circle circle3=mMap.addCircle(new CircleOptions()
//                .center(NGO1)
//                .radius(10000)
//                .strokeColor(Color.parseColor("#0000FF"))
//                .fillColor(Color.parseColor("#330000FF"))
//        );





        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(NGO1));




    }









}
