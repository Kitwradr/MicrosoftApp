package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


public class RvAzure_StuckInThisDisaster extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    SharedPreferences sharedPreferences;



    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_VICTIM_IMAGE_IN_ALBUM = 1;
    private Uri mUriPhotoTaken;
    ImageView victimimage;
    private String victimimagepathtag="VICTIM IMAGE";
    String VICTIM_PREF="VICTIM-URL";
    String victimrestoredPath;
    Button victimimageupload;



    private String DistressMessageNearestGroupLat;
    private String DistressMessageNearestGroupLon;
    private String DistressMessageNearestGroupContact;


    private String DistressMessageNearestRescueGroupLat;
    private String DistressMessageNearestRescueGroupLon;
    private String DistressMessageNearestRescueGroupContact;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure__stuck_in_this_disaster);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        victimimage=(ImageView)findViewById(R.id.victimimage1);
        victimimageupload=(Button)findViewById(R.id.victimuploadbutton);



        sharedPreferences=this.getSharedPreferences(VICTIM_PREF, Context.MODE_PRIVATE);
        victimrestoredPath = sharedPreferences.getString(victimimagepathtag,null);

        if(victimrestoredPath!=null)
        {

            victimimage.setImageBitmap(BitmapFactory.decodeFile(victimrestoredPath));
            victimimage.setScaleType(ImageView.ScaleType.FIT_XY);

        }


        victimimage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent victimIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent

                startActivityForResult(victimIntent, REQUEST_SELECT_VICTIM_IMAGE_IN_ALBUM);



            }
        });

        victimimageupload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            if(victimrestoredPath!=null) {
                try {
                    System.out.print("PATH ---------------------------sdfbisdfu---sdbj"+victimrestoredPath);
                   new Uploadasynch().execute(victimrestoredPath);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.v("myApp", "Some error came up");
                }


            }


            }
        });





      SmsReceiver.bindListener(new SmsListener()
      {
          @Override
          public void messageReceived(String messageText)
          {


               if(!isInternetConnection()&&messageText.startsWith("RVSAFE DISTRESS HELPLINE"))
               {
                   Toast.makeText(getApplicationContext(),messageText,Toast.LENGTH_SHORT).show();


                   String[] format=new String[]{"NGLA","NGLO","NGC","NRGLA","NRGLO","NRGC"};


                  DistressMessageNearestGroupLat = messageText.split(format[0])[1].split(" ")[0];
                  DistressMessageNearestGroupLon = messageText.split(format[1])[1].split(" ")[0];
                  DistressMessageNearestGroupContact = messageText.split(format[2])[1].split(" ")[0];


                   DistressMessageNearestRescueGroupLat = messageText.split(format[3])[1].split(" ")[0];
                   DistressMessageNearestRescueGroupLon = messageText.split(format[4])[1].split(" ")[0];
                   DistressMessageNearestRescueGroupContact = messageText.split(format[5])[1];


                   updatelocale();

               }

          }
      });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
      //  outState.putParcelable("ImageUri", mUriPhotoTaken);
    }


    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    //    mUriPhotoTaken = savedInstanceState.getParcelable("ImageUri");

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_SELECT_VICTIM_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_OK&&data!=null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = this.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    SharedPreferences.Editor editor = this.getSharedPreferences(VICTIM_PREF, Context.MODE_PRIVATE).edit();
                    editor.putString(victimimagepathtag, picturePath);
                    editor.apply();

                    victimrestoredPath = picturePath;

                    victimimage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    victimimage.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;
            default:
                break;
        }
    }




    public  void  updatelocale()
    {
        LatLng affectedlatlon = new LatLng(Float.parseFloat(DistressMessageNearestGroupLat), Float.parseFloat(DistressMessageNearestGroupLon));
        LatLng rescuelatlang = new LatLng(Float.parseFloat(DistressMessageNearestRescueGroupLat), Float.parseFloat(DistressMessageNearestRescueGroupLon));
       Log.i("TRIAL RESCUE",""+Float.parseFloat(DistressMessageNearestRescueGroupLat)+""+Float.parseFloat(DistressMessageNearestRescueGroupLon));
        mMap.addMarker(new MarkerOptions().position(rescuelatlang).title("NEAREST RESCUE GROUP").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions().position(affectedlatlon).title("NEAREST AFFECTED GROUP").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

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

     mMap=googleMap;
      /*  Random rand  = new Random();

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
        }*/


        // Add a marker in Sydney and move the camera
   /*     LatLng sydney = new LatLng(Float.parseFloat(DistressMessageNearestGroupLat), Float.parseFloat(DistressMessageNearestRescueGroupContact));

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(-33.87365, 151.20689))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }




    public  boolean isInternetConnection()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }












}
