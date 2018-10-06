package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

public class RvAzure_StuckInThisDisaster extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure__stuck_in_this_disaster);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





      SmsReceiver.bindListener(new SmsListener()
      {
          @Override
          public void messageReceived(String messageText)
          {


               if(!isInternetConnection()&&messageText.startsWith("RVSAFE DISTRESS HELPLINE"))
               {
                   Toast.makeText(getApplicationContext(),messageText,Toast.LENGTH_SHORT).show();
               }

          }
      });






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
    public void onMapReady(GoogleMap googleMap) {
        Random rand  = new Random();

        ArrayList<LatLng> locationsList = new ArrayList<LatLng>() ;

        mMap = googleMap;

        for (int i=0;i<5;i++)
        {
            double min1 = -30,max1 = -37 ,min2 = 145 , max2 = 155;

            LatLng location = new LatLng(rand.nextDouble()*max1+min1,rand.nextDouble()*max2+min2);

            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            locationsList.add(location);
            //Log.d("mytag",location.toString());
        }
        for (int i=0;i<5;i++)
        {
            double min1 = -30,max1 = -37 ,min2 = 145 , max2 = 155;

            LatLng location = new LatLng(rand.nextDouble()*max1+min1,rand.nextDouble()*max2+min2);

            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            locationsList.add(location);
            //Log.d("mytag",location.toString());
        }
        for (int i=0;i<5;i++)
        {
            double min1 = -30,max1 = -37 ,min2 = 145 , max2 = 155;

            LatLng location = new LatLng(rand.nextDouble()*max1+min1,rand.nextDouble()*max2+min2);

            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Marker in Sydney")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

            locationsList.add(location);
            //Log.d("mytag",location.toString());
        }


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-33.87365, 151.20689))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }




    public  boolean isInternetConnection()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }












}
