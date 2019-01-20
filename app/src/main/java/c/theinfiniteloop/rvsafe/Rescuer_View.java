package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Rescuer_View extends FragmentActivity implements OnMapReadyCallback {

    private DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();


    RvAzure_GPStracker mygps;

    private static final int minimumtimeofrequest = 100;

    private static final int minimumdistanceofrequest = 1;


    private static final String TAG = Rescuer_View.class.getSimpleName();

    private static final LatLngBounds NETHERLANDS = new LatLngBounds(new LatLng(12.736903,77.380965 ), new LatLng(13.165234, 77.834417));


    TextView number_of_people;
    TextView women;
    TextView children;
    TextView elders;


    ImageView landmark1;
    ImageView landmark2;
    ImageView landmark3;


    Button download;

    private GoogleMap mMap;
    List<ClusterItemsTrial> clusterItems;
    ClusterManager<ClusterItemsTrial> clusterManager;
    public Boolean detailsDownloaded = false;
    public List<Rv_Azure_FaceAPIDetails> apiList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescuer__view);
        Button startmission = findViewById(R.id.startmission);
        download=findViewById(R.id.download);
        if(!isStoragePermissionGranted())
        {


        }




        LinearLayout medicallayout=findViewById(R.id.medical_id_layout);

        number_of_people = findViewById(R.id.number_of_people_trapped);
        elders = findViewById(R.id.number_of_elders);
        women = findViewById(R.id.number_of_women);
        children = findViewById(R.id.number_of_children);


        landmark1 = findViewById(R.id.landmark1);
        landmark2 = findViewById(R.id.landmark2);
        landmark3 = findViewById(R.id.landmark3);


     //   rescueText.bringToFront();
        startmission.bringToFront();
//        pindrop.bringToFront();

        startmission.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            startmission.setVisibility(View.GONE);
            medicallayout.bringToFront();
            medicallayout.setVisibility(View.VISIBLE);
            }
        });


        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //write code here
                list.clear();

                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle("Medical Report Downloading... ");
                request.setDescription("Downloading Medical Report");
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/RVSAFE/"  + "/" + "Report" + ".pdf");


                refid = downloadManager.enqueue(request);


                Log.e("OUT", "" + refid);

                list.add(refid);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (savedInstanceState == null)
        {
            setupMapFragment();
        }

        new RetrieveImagesAsync().execute();


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mygps = new RvAzure_GPStracker(getApplicationContext(), locationManager);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        boolean gpsenabled = false;
        boolean networkenabled = false;

        gpsenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkenabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsenabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        } else if (networkenabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        } else {
            Toast.makeText(getApplicationContext(), "TURN ON GPS", Toast.LENGTH_SHORT).show();
        }

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        //Instead of 0 in the URL get the text from input field
        Download_Uri = Uri.parse("http://aztests.azurewebsites.net/victims/get/"+"0"+"/medical");




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){


            // permission granted

        }
    }
    @Override
    protected void onDestroy() {


        super.onDestroy();

        unregisterReceiver(onComplete);



    }
    public  boolean isStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
                {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
               }
        }
        else
            { //permission is automatically granted on sdk<23 upon installation
            return true;
            }
    }

    BroadcastReceiver onComplete = new BroadcastReceiver()
    {

        public void onReceive(Context ctxt, Intent intent) {

            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            list.remove(referenceId);


            if (list.isEmpty())
            {


                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(Rescuer_View.this,"234")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("GadgetSaint")
                                .setContentText("All Download completed");


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }

        }
    };


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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);




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







        clusterManager = new ClusterManager<>(this, googleMap);
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

                if(detailsDownloaded);
                {
                    update_gui(clusterItem.getTitle());
                }

                Log.d(TAG, "onClusterItemClick");
                return false;
            }
        });











        googleMap.setOnCameraIdleListener(clusterManager);




        clusterItems = new ArrayList<>();
        for (int i = 0; i < 400; i++)
        {

            if (i<100)
            {
                clusterItems.add(new ClusterItemsTrial(RandomLocationGenerator.generate(NETHERLANDS),"","user 1","2"));


            }
            else if(i<200)
            {

                clusterItems.add(new ClusterItemsTrial(RandomLocationGenerator.generate(NETHERLANDS),"","user 2","1"));


            }
            else
            {
                clusterItems.add(new ClusterItemsTrial

                        (RandomLocationGenerator.generate(NETHERLANDS),"","user 3","0"));

            }




        }


        clusterItems.add(new ClusterItemsTrial(new LatLng(mygps.getLatitude(),mygps.getLongitude()),"","user 0","2"));




        // clusterManager

        clusterManager.setItems(clusterItems,false);





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

//        clusterItems.add(new ClusterItemsTrial((new LatLng(Double.parseDouble(obj.getLat()),Double.parseDouble(obj.getLong()))),"user 0","HIGH PRIORITY",""+obj.getPriority()));
//        clusterManager.setItems(clusterItems,true);
//        clusterManager.setItems(clusterItems,false);
//






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



if(apiList!=null) {
    for (int i = 0; i < apiList.size(); i++) {


        System.out.println("RESCUER VIEW " + apiList.get(i));

        try {
            if (("user " + apiList.get(i).getUserid()).matches(markerID)) {
                Rv_Azure_FaceAPIDetails obj = apiList.get(i);
                System.out.println("marker ID = " + markerID);

                elders.setText("NUMBER OF ELDERS : " + obj.getElders());
                children.setText("NUMBER OF CHILDREN : " + obj.getChildren());
                number_of_people.setText("NUMBER OF PEOPLE : " + obj.getNumstuck());
                women.setText("NUMBER OF WOMEN : " + obj.getFemale());





                /*use picasso to update image urls*/

                System.out.println(obj.getBlobs()[0] + obj.getBlobs()[1] + obj.getBlobs()[2]);

                Picasso.get().load(obj.getBlobs()[0]).placeholder(R.drawable.ic_photo_black_24dp).error(R.drawable.ic_photo_black_24dp).into(landmark1);
                Picasso.get().load(obj.getBlobs()[1]).placeholder(R.drawable.ic_photo_black_24dp).error(R.drawable.ic_photo_black_24dp).into(landmark2);
                Picasso.get().load(obj.getBlobs()[2]).placeholder(R.drawable.ic_photo_black_24dp).error(R.drawable.ic_photo_black_24dp).into(landmark3);


                return;
                // landmark1.setImageResource();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
      }
    }

    }

    public  class RetrieveImagesAsync extends AsyncTask<Void, Void, List<Rv_Azure_FaceAPIDetails>>
    {

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
        protected void onPostExecute(List<Rv_Azure_FaceAPIDetails> details)
        {

            detailsDownloaded = true;



            apiList = details;

       //     addnewpoint();


        if(apiList!=null)
        {
            for (int i = 0; i < apiList.size(); i++)
            {

                Log.i("user id priority", apiList.get(i).getUserid() + " " + apiList.get(i).getPriority());

            }

        }



        }
    }

    public  void addnewpoint()
    {

        clusterItems.add(new ClusterItemsTrial(new LatLng(mygps.getLatitude(),mygps.getLongitude()),"","user 0","0"));


        clusterManager.setItems(clusterItems,false);



    }

	




}
