package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Rescuer_View extends FragmentActivity implements OnMapReadyCallback
{


    private static final String TAG = Rescuer_View.class.getSimpleName();

    private static final LatLngBounds NETHERLANDS = new LatLngBounds(new LatLng(7.798000, 68.14712), new LatLng(37.090000, 97.34466));



    TextView number_of_people;
    TextView women;
    TextView children;
    TextView elders;


    ImageView landmark1;
    ImageView landmark2;
    ImageView landmark3;



    private GoogleMap mMap;

    public Boolean detailsDownloaded = false;
    public List<Rv_Azure_FaceAPIDetails> apiList ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer__view);
        TextView rescueText=findViewById(R.id.rescuetext);


        number_of_people =findViewById(R.id.number_of_people_trapped);
        elders=findViewById(R.id.number_of_elders);
        women=findViewById(R.id.number_of_women);
        children=findViewById(R.id.number_of_children);


        landmark1=findViewById(R.id.landmark1);
        landmark2=findViewById(R.id.landmark2);
        landmark3=findViewById(R.id.landmark3);


        rescueText.bringToFront();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (savedInstanceState == null)
        {
            setupMapFragment();
        }

        new RetrieveImagesAsync().execute();


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


      /*ArrayList<LatLng> points =new ArrayList<>();

        for (int i = 0; i < 20; i++)
        {

            ClusterItemsTrial a=new ClusterItemsTrial(RandomLocationGenerator.generate(NETHERLANDS));

            points.add(new LatLng(a.getLatitude(),a.getLongitude()));

            }

        PolylineOptions polylineOptions=new PolylineOptions().width(5).color(Color.GREEN).geodesic(true);


       for(int i=0;i<points.size();i++)
       {
           polylineOptions.add(points.get(i));

       }
       googleMap.addPolyline(polylineOptions);*/

















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
            public boolean onClusterClick(@NonNull Cluster<ClusterItemsTrial> cluster)
            {
                Log.d(TAG, "onClusterClick");
                return false;
            }

            @Override
            public boolean onClusterItemClick(@NonNull ClusterItemsTrial clusterItem)
            {


               /*call the id function here*/

                while(!detailsDownloaded);
                update_gui(clusterItem.getSnippet());


                Log.d(TAG, "onClusterItemClick");
                return false;
            }
        });











        googleMap.setOnCameraIdleListener(clusterManager);




        List<ClusterItemsTrial> clusterItems = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {

            if (i < 1000)
            {
                clusterItems.add(new ClusterItemsTrial(RandomLocationGenerator.generate(NETHERLANDS),"MODERATE PRIORITY","user x","1"));
            }
            else
            {



                clusterItems.add(new ClusterItemsTrial

                        (RandomLocationGenerator.generate(NETHERLANDS),"user 1","LOW PRIORITY","0"));


            }





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



    public  void update_gui(String markerID)
    {
        Rv_Azure_FaceAPIDetails obj = apiList.get(0);

        System.out.println("marker ID = "+markerID);

        elders.setText("NUMBER OF ELDERS : "+obj.getElders());
        children.setText("NUMBER OF CHILDREN : "+obj.getChildren());
        number_of_people.setText("NUMBER OF PEOPLE : "+obj.getNumstuck());
        women.setText("NUMBER OF WOMEN : "+obj.getFemale());


        /*use picasso to update image urls*/

        System.out.println(obj.getBlobs()[0]+obj.getBlobs()[1]+obj.getBlobs()[2]);

        Picasso.get().load(obj.getBlobs()[0]).placeholder(R.drawable.keralafloodsimage1).error(R.drawable.tamilnadutsunami).into(landmark1);
        Picasso.get().load(obj.getBlobs()[1]).placeholder(R.drawable.keralafloodsimage1).error(R.drawable.tamilnadutsunami).into(landmark2);
        Picasso.get().load(obj.getBlobs()[2]).placeholder(R.drawable.keralafloodsimage1).error(R.drawable.tamilnadutsunami).into(landmark3);


        // landmark1.setImageResource();

    }

    public  class RetrieveImagesAsync extends AsyncTask<Void, Void, List<Rv_Azure_FaceAPIDetails>> {

        ArrayList<Rv_Azure_FaceAPIDetails> list;

        protected List<Rv_Azure_FaceAPIDetails> doInBackground(Void... urls) {
            try {


                URL urlObj = new URL("https://aztests.azurewebsites.net/rescuer/mapdata");
                //URL urlObj = new URL("http://192.168.43.27:8080/facial");
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true);
                //conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestProperty("Content-Type", "image/jpeg");

                OutputStream outputStream = conn.getOutputStream();

                //System.out.println("PATH--------------------------------------------"+path);

                //System.out.println("-===============================theadefeqfqfwqwf "+ bytes.length);



                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //Get response from server
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code : " + responseCode);
                // read in the response from the server
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }
                System.out.println("Response is "+response);
                // close the input stream
                in.close();

                Gson gson = new Gson();

                JSONObject responseObject = new JSONObject(response.toString());

                JSONObject imageDetails = responseObject.getJSONObject("data");

                Iterator iter1 = imageDetails.keys();

                list = new ArrayList<Rv_Azure_FaceAPIDetails>();

                while (iter1.hasNext())
                {
                    Rv_Azure_FaceAPIDetails obj = new Rv_Azure_FaceAPIDetails();

                    String key = (String) iter1.next();
                    String value = imageDetails.getString(key);

                    obj = gson.fromJson(value.toString(), Rv_Azure_FaceAPIDetails.class);
                    System.out.println(obj);
                    list.add(obj);

                }




                outputStream.close();



            } catch (Exception e) {
                e.printStackTrace();


            }

            return list;

        }


        @Override
        protected void onPostExecute(List<Rv_Azure_FaceAPIDetails> details) {

            detailsDownloaded = true;
            apiList = details;

        }
    }
	




}
