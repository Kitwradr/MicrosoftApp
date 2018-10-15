package c.theinfiniteloop.rvsafe;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.List;

public class Rescuer_View extends FragmentActivity implements OnMapReadyCallback {






    private static final String TAG = Rescuer_View.class.getSimpleName();

    private static final LatLngBounds NETHERLANDS = new LatLngBounds(
            new LatLng(7.798000, 68.14712), new LatLng(37.090000, 97.34466));






    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer__view);
        TextView rescueText=findViewById(R.id.rescuetext);
        rescueText.bringToFront();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (savedInstanceState == null) {
            setupMapFragment();
        }

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
        mMap = googleMap;



        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback()
        {
            @Override
            public void onMapLoaded()
            {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(NETHERLANDS, 0));
            }
        });

        ClusterManager<ClusterItemsTrial> clusterManager = new ClusterManager<>(this, googleMap);
        clusterManager.setCallbacks(new ClusterManager.Callbacks<ClusterItemsTrial>()
        {
            @Override
            public boolean onClusterClick(@NonNull Cluster<ClusterItemsTrial> cluster) {
                Log.d(TAG, "onClusterClick");
                return false;
            }

            @Override
            public boolean onClusterItemClick(@NonNull ClusterItemsTrial clusterItem) {
                Log.d(TAG, "onClusterItemClick");
                return false;
            }
        });











        googleMap.setOnCameraIdleListener(clusterManager);














        List<ClusterItemsTrial> clusterItems = new ArrayList<>();
        for (int i = 0; i < 2000; i++)
        {
            clusterItems.add(new ClusterItemsTrial
                    (
                    RandomLocationGenerator.generate(NETHERLANDS)));


        }
        clusterManager.setItems(clusterItems);




  /*      IconStyle.Builder custombuilder=new IconStyle.Builder(this);
        custombuilder.setClusterBackgroundColor(Color.GREEN);
        custombuilder.setClusterIconResId(R.drawable.ic_location_on_black_24dp);
        IconStyle customiconstyle=new IconStyle(custombuilder);
        DefaultIconGenerator customicongenerator=new DefaultIconGenerator(this);
        customicongenerator.setIconStyle(customiconstyle);

        ClusterManager<ClusterItemsTrial> clusterManager2 = new ClusterManager<>(this, googleMap);
        googleMap.setOnCameraIdleListener(clusterManager2);

        clusterManager2.setCallbacks(new ClusterManager.Callbacks<ClusterItemsTrial>()
        {
            @Override
            public boolean onClusterClick(@NonNull Cluster<ClusterItemsTrial> cluster) {
                Log.d(TAG, "onClusterClick");
                return false;
            }

            @Override
            public boolean onClusterItemClick(@NonNull ClusterItemsTrial clusterItem) {
                Log.d(TAG, "onClusterItemClick");
                return false;
            }
        });





        clusterManager2.setIconGenerator(customicongenerator);


        List<ClusterItemsTrial> clusterItems2 = new ArrayList<>();
        for (int i = 0; i < 2000; i++)
        {
            clusterItems2.add(new ClusterItemsTrial(
                    RandomLocationGenerator.generate(NETHERLANDS)));
    }

        clusterManager2.setItems(clusterItems2); */






        // Add a marker in Sydney and move the camera

    }

    private void setupMapFragment()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.setRetainInstance(true);
        mapFragment.getMapAsync(this);
    }
}
