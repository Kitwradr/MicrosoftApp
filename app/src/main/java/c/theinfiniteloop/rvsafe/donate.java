package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.ArrayList;
import java.util.List;

public class donate extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;


    public static boolean button1pressed = false;
    public static boolean button2pressed = false;
    public static boolean button3pressed = false;
    public static boolean button4pressed = false;


    public static donate newInstance() {
        donate fragment = new donate();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donate);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_donate, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final Button button1 = view.findViewById(R.id.button1);
        final Button button2 = view.findViewById(R.id.button2);
        final Button button3 = view.findViewById(R.id.button3);
        final Button button4 = view.findViewById(R.id.button4);
        Button button5 = view.findViewById(R.id.button5);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (button1pressed) {


                    button1.setBackgroundResource(R.drawable.oval_button_white);

                    button1pressed = false;
                } else {
                    button1.setBackgroundResource(R.drawable.oval_button_light_green);
                    button1pressed = true;
                }


            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (button2pressed) {


                    button2.setBackgroundResource(R.drawable.oval_button_white);

                    button2pressed = false;
                } else {
                    button2.setBackgroundResource(R.drawable.oval_button_light_green);
                    button2pressed = true;
                }


            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (button3pressed) {


                    button3.setBackgroundResource(R.drawable.oval_button_white);

                    button3pressed = false;
                } else {
                    button3.setBackgroundResource(R.drawable.oval_button_light_green);
                    button3pressed = true;
                }


            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (button4pressed) {


                    button4.setBackgroundResource(R.drawable.oval_button_white);

                    button4pressed = false;
                } else {
                    button4.setBackgroundResource(R.drawable.oval_button_light_green);
                    button4pressed = true;
                }


            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TelephonyManager tmgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                String phonenumber = tmgr.getLine1Number();





                DonateDetails donateDetails=new DonateDetails();

                donateDetails.setPhone_number("12345");
                donateDetails.setAddress("useraddress");
                donateDetails.setCity("usercity");
                List<String> items=new ArrayList<String>();

                if(button1pressed)
                {
                    items.add("MONEY");

                }
                if(button2pressed)
                {

                    items.add("FOOD GRAINS");
                }
                if(button3pressed)
                {
                    items.add("CLOTHES");
                }
                if(button4pressed)
                {
                    items.add("ACCESSORIES");
                }

                donateDetails.setItems(items);

             //   new postDonateAsync().execute(donateDetails);


             //   Toast.makeText(getContext(),"THANK YOU",Toast.LENGTH_SHORT).show();

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
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(NGO1));




    }

    private class postDonateAsync extends AsyncTask<DonateDetails, Void, Void>
    {

        @Override
        protected Void doInBackground(DonateDetails... data) {

            try {
                String postUrl = "https://aztests.azurewebsites.net/ngo/resources/add";
                Gson gson = new Gson();
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(postUrl);
                StringEntity postingString = new StringEntity(gson.toJson(data));

                post.setEntity(postingString);
                post.setHeader("Content-type", "application/json");

                HttpResponse response = httpClient.execute(post);


                System.out.println("\nSending 'POST' request to URL : " + postUrl);
                int code = response.getStatusLine().getStatusCode();
                System.out.println("Exited with status code of " + code);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }

    }







}
