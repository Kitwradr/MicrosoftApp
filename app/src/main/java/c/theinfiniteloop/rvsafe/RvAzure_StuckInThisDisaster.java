package c.theinfiniteloop.rvsafe;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class RvAzure_StuckInThisDisaster extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    SharedPreferences sharedPreferences;
    LocationManager lm;
    RvAzure_GPStracker mygps;
    float safezonedistanceinm;
    float rescuegroupdistanceinm;

    String safezonenamestring;
    String rescuegroupnamestring;

    String safezonecontactstring;
    String rescuegroupcontacstring;


    TextView rescugroupname;
    TextView rescuegroupdistance;
    Button rescuegroupcall;


    TextView safezonename;
    TextView safezonedistance;
    Button safezonecall;

    Bitmap CurrentVictimBitmap;
    Bitmap CurrentLandmarkBitamp;
    Bitmap CurrentLandmark1Bitamp;
    Bitmap CurrentLandmark2Bitamp;

    private static final int minimumtimeofrequest = 100;

    private static final int minimumdistanceofrequest = 1;


    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_SELECT_VICTIM_IMAGE_IN_ALBUM = 1;
    private Uri mUriPhotoTaken;
    ImageView victimimage;
    ImageView landmarkimage1;
    ImageView landmarkimage2;
    ImageView landmarkimage3;


    private String victimimagepathtag = "VICTIM IMAGE";
    private String landmarkimagepathtag1="LANDMARK 1";
    private String landmarkimagepathtag2="LANDMARK 2";
    private String landmarkimagepathtag3="LANDMARK 3";




    String VICTIM_PREF = "VICTIM-URL";
    String victimrestoredPath;
    String landmarkrestoredPath1;
    String landmarkrestoredPath2;
    String landmarkrestoredPath3;




    Button victimimageupload;
    Button landmarkimageupload;
    Button safe;
    Button unsafe;
    Button nearest_rescue_group_call;
    Button nearest_safe_zone_call;

    double mylatitude;
    double mylongitude;

    int disaster_id;

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



        disaster_id=getIntent().getIntExtra("disaster_id",-45);




        rescugroupname = findViewById(R.id.nearestrescuegroupname);
        rescuegroupdistance = findViewById(R.id.rescuegroupdistance);
        rescuegroupcall = findViewById(R.id.rescuegroupcallbutton);


        safezonename = findViewById(R.id.nearestsafezone);
        safezonedistance = findViewById(R.id.nearestsafezonedistance);
        safezonecall = findViewById(R.id.nearestsafezonecall);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        victimimage = (ImageView) findViewById(R.id.victimimage1);
        landmarkimage1=(ImageView)findViewById(R.id.landmarkimage1);
        landmarkimage2=(ImageView)findViewById(R.id.landmarkimage2);
        landmarkimage3=(ImageView)findViewById(R.id.landmarkimage3);

        LinearLayout safeunsafe=findViewById(R.id.safeunsafe);
        FloatingActionButton instahelp=findViewById(R.id.instahelp);


        if(disaster_id==-1)
        {
            safeunsafe.setVisibility(View.GONE);
            instahelp.setVisibility(View.VISIBLE);
        }









        safe=(Button)findViewById(R.id.safeid);
        unsafe=(Button)findViewById(R.id.unsafeid);
        victimimageupload = (Button) findViewById(R.id.victimuploadbutton);
        landmarkimageupload=(Button)findViewById(R.id.landmarkupload);




        sharedPreferences = this.getSharedPreferences(VICTIM_PREF, Context.MODE_PRIVATE);
        victimrestoredPath = sharedPreferences.getString(victimimagepathtag, null);
        landmarkrestoredPath1=sharedPreferences.getString(landmarkimagepathtag1,null);
        landmarkrestoredPath2=sharedPreferences.getString(landmarkimagepathtag2,null);
        landmarkrestoredPath3=sharedPreferences.getString(landmarkimagepathtag3,null);















        if (victimrestoredPath != null)
        {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.ARGB_8888;


            options.inBitmap=CurrentVictimBitmap;

            CurrentVictimBitmap=BitmapFactory.decodeFile(victimrestoredPath,options);

            victimimage.setImageBitmap(CurrentVictimBitmap);

            victimimage.setScaleType(ImageView.ScaleType.FIT_XY);

        }
        if(landmarkrestoredPath1!=null)
        {

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.RGB_565;
            options.inBitmap=CurrentLandmarkBitamp;
            CurrentLandmarkBitamp=BitmapFactory.decodeFile(landmarkrestoredPath1,options);
            landmarkimage1.setImageBitmap(CurrentLandmarkBitamp);
            landmarkimage1.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        if(landmarkrestoredPath2!=null)
        {

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.RGB_565;





            options.                                 inBitmap=CurrentLandmark1Bitamp;

            CurrentLandmark1Bitamp=BitmapFactory.decodeFile(landmarkrestoredPath2,options);
            landmarkimage2.setImageBitmap(CurrentLandmark1Bitamp);
            landmarkimage2.setScaleType(ImageView.ScaleType.FIT_XY);

        }
        if(landmarkrestoredPath3!=null)
        {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inPreferredConfig=Bitmap.Config.RGB_565;


            options.inBitmap=CurrentLandmark2Bitamp;

            CurrentLandmark2Bitamp=BitmapFactory.decodeFile(landmarkrestoredPath3,options);

            landmarkimage3.setImageBitmap(CurrentLandmark2Bitamp);
            landmarkimage3.setScaleType(ImageView.ScaleType.FIT_XY);

        }



        victimimage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent victimIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                startActivityForResult(victimIntent, REQUEST_SELECT_VICTIM_IMAGE_IN_ALBUM);


            }
        });





        landmarkimage1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                startActivityForResult(landmarkIntent, 2);


            }
        });


        landmarkimage2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                startActivityForResult(landmarkIntent, 3);


            }
        });


        landmarkimage3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                startActivityForResult(landmarkIntent, 4);


            }
        });

        instahelp.setImageBitmap(textAsBitmap("FIND NEAREST", 40, Color.BLACK));




        instahelp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RvAzure_StuckInThisDisaster.this);

                dialogBuilder.setTitle("MARK YOUR LOCATION");
                dialogBuilder.setPositiveButton("SAFE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {


                        String phno ="8618452952";

                        String message="RVSAFE-DISTRESS USID:xxxx LAT:"+mygps.getLatitude()+" LON:"+mygps.getLongitude();

                        SmsManager manager = SmsManager.getDefault();

                        manager.sendTextMessage(phno, null, message, null, null);



                        //do something with edt.getText().toString();
                    }
                });
                dialogBuilder.setNegativeButton("IN DANGER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        //pass

                        String phno =" 8618452952";

                        String message="RVSAFE-DISTRESS USID:xxxx LAT:"+mygps.getLatitude()+" LON:"+mygps.getLongitude();

                        SmsManager manager = SmsManager.getDefault();

                        manager.sendTextMessage(phno, null, message, null, null);


                    }
                });


                AlertDialog b = dialogBuilder.create();
                b.show();





            }
        });













        victimimageupload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (victimrestoredPath != null) {
                    try {

                     if(isInternetConnection())
                     {
                         System.out.print("PATH ---------------------------sdfbisdfu---sdbj" + victimrestoredPath);
                         new Uploadasynch(getApplicationContext()).execute(victimrestoredPath);
                         Toast.makeText(getApplicationContext(), "IMAGE UPLOADED", Toast.LENGTH_SHORT).show();
                     }
                     else
                     {
                         Toast.makeText(RvAzure_StuckInThisDisaster.this,"ACTION CANNOT BE PERFORMED",Toast.LENGTH_SHORT).show();
                     }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.v("myApp", "Some error came up");
                    }


                }


            }
        });

      landmarkimageupload.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view)
          {
              try
              {
                 boolean upload=false;

                 if(isInternetConnection()) {
                     if (landmarkrestoredPath1 != null)
                     {
                         upload = true;
                         new uploadImage().execute(new File(landmarkrestoredPath1));
                     }
                     if (landmarkrestoredPath2 != null) {
                         upload = true;
                         new uploadImage().execute(new File(landmarkrestoredPath2));
                     }
                     if (landmarkrestoredPath3 != null) {
                         upload = true;
                         new uploadImage().execute(new File(landmarkrestoredPath3));
                     }
                     if (upload) {
                         Toast.makeText(getApplicationContext(), "IMAGE UPLAODED", Toast.LENGTH_SHORT).show();
                     }

                 }
                 else
                 {
                     Toast.makeText(RvAzure_StuckInThisDisaster.this,"ACTION CANNOT BE PERFORMED",Toast.LENGTH_SHORT).show();
                 }



              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }
          }
      });










        safe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(isInternetConnection())
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RvAzure_StuckInThisDisaster.this);
                    alertDialogBuilder.setTitle("DO YOU WANT MARK YOUR CURRENT LOCATION SAFE");
                    alertDialogBuilder.setMessage("your contribution can help us save numerous lives")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    UserData userData = new UserData();
                                    userData.setIssafe("true");
                                    userData.setLat("" + mygps.getLatitude());
                                    userData.setLong("" + mygps.getLongitude());
                                    userData.setUser_id("0");
                                    Toast.makeText(RvAzure_StuckInThisDisaster.this, "YOU MARKED YOUR LOCATION SAFE", Toast.LENGTH_SHORT).show();
                                    new postSafetyAsync().execute(userData);

                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });


                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(RvAzure_StuckInThisDisaster.this,"THIS ACTION CANNOT BE PERFORMED",Toast.LENGTH_SHORT).show();
                }
            }
        });
        unsafe.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View view)
            {
               if(isInternetConnection()) {
                   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RvAzure_StuckInThisDisaster.this);
                   alertDialogBuilder.setTitle("DO YOU WANT MARK YOUR CURRENT LOCATION UNSAFE");
                   alertDialogBuilder.setMessage("your contribution can help us save numerous lives")
                           .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i)
                               {
                                   UserData userData = new UserData();
                                   userData.setIssafe("false");
                                   userData.setLat("" + mygps.getLatitude());
                                   userData.setLong("" + mygps.getLongitude());
                                   userData.setUser_id("0");
                                   Toast.makeText(RvAzure_StuckInThisDisaster.this, "YOU MARKED YOUR LOCATION UNSAFE", Toast.LENGTH_SHORT).show();
                                   new postSafetyAsync().execute(userData);

                               }
                           }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i)
                       {
                           dialogInterface.cancel();
                       }
                   });


                   AlertDialog dialog = alertDialogBuilder.create();
                   dialog.show();
               }
               else
               {
                   Toast.makeText(RvAzure_StuckInThisDisaster.this,"ACTION CANNOT BE PERFORMED",Toast.LENGTH_SHORT).show();
               }
            }
        });

     safezonecall.setOnClickListener(new View.OnClickListener()
     {
         @Override
         public void onClick(View view)
         {

             Intent phoneIntent=new Intent(Intent.ACTION_DIAL);


             if (ActivityCompat.checkSelfPermission(RvAzure_StuckInThisDisaster.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
             {
                 Toast.makeText(RvAzure_StuckInThisDisaster.this,"GRANT PHONE CALL PERMISSION",Toast.LENGTH_SHORT).show();
                 return;
             }

             phoneIntent.setData(Uri.parse("tel:"+DistressMessageNearestGroupContact));

             startActivity(phoneIntent);

         }

     });

     rescuegroupcall.setOnClickListener(new View.OnClickListener()
     {
         @Override
         public void onClick(View view)
         {


             Intent phoneIntent=new Intent(Intent.ACTION_DIAL);


             if (ActivityCompat.checkSelfPermission(RvAzure_StuckInThisDisaster.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
             {
                 Toast.makeText(RvAzure_StuckInThisDisaster.this,"GRANT PHONE CALL PERMISSION",Toast.LENGTH_SHORT).show();
                 return;
             }

             phoneIntent.setData(Uri.parse("tel:"+  DistressMessageNearestRescueGroupContact));

             startActivity(phoneIntent);
         }
     });




        if(isInternetConnection())
        {

            new RescueQueryAsync().execute();
            new GetClustersAsync().execute();
             //new RetrieveImagesAsync().execute();
        }


        SmsReceiver.bindListener(new SmsListener()
        {
            @Override
            public void messageReceived(String messageText) {

     try {
         if (!isInternetConnection() && messageText.startsWith("RVSAFE DISTRESS HELPLINE")) {
             //             Toast.makeText(getApplicationContext(), messageText, Toast.LENGTH_SHORT).show();


             String[] format = new String[]{"NGLA", "NGLO", "NGC", "NRGLA", "NRGLO", "NRGC"};


             DistressMessageNearestGroupLat = messageText.split(format[0])[1].split(" ")[0];
             DistressMessageNearestGroupLon = messageText.split(format[1])[1].split(" ")[0];
             DistressMessageNearestGroupContact = messageText.split(format[2])[1].split(" ")[0];


             DistressMessageNearestRescueGroupLat = messageText.split(format[3])[1].split(" ")[0];
             DistressMessageNearestRescueGroupLon = messageText.split(format[4])[1].split(" ")[0];
             DistressMessageNearestRescueGroupContact = messageText.split(format[5])[1];


             updatelocale();

            }
           }

           catch(Exception e)
          {

          }



            }
        });


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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    SharedPreferences.Editor editor = this.getSharedPreferences(VICTIM_PREF, Context.MODE_PRIVATE).edit();
                    editor.putString(victimimagepathtag, picturePath);
                    editor.apply();

                    victimrestoredPath = picturePath;

                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap;
                    CurrentVictimBitmap=BitmapFactory.decodeFile(picturePath,options);
                    victimimage.setImageBitmap(CurrentVictimBitmap);
                    victimimage.setScaleType(ImageView.ScaleType.FIT_XY);



                }
                break;
            case 2:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    SharedPreferences.Editor editor = this.getSharedPreferences(VICTIM_PREF, Context.MODE_PRIVATE).edit();
                    editor.putString(landmarkimagepathtag1, picturePath);
                    editor.apply();

                    landmarkrestoredPath1 = picturePath;

                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;

                    options.inBitmap=CurrentLandmarkBitamp;

                    CurrentLandmarkBitamp=BitmapFactory.decodeFile(picturePath,options);

                    landmarkimage1.setImageBitmap(CurrentLandmarkBitamp);
                    landmarkimage1.setScaleType(ImageView.ScaleType.FIT_XY);



                }
                break;
            case 3:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    SharedPreferences.Editor editor = this.getSharedPreferences(VICTIM_PREF, Context.MODE_PRIVATE).edit();
                    editor.putString(landmarkimagepathtag2, picturePath);
                    editor.apply();

                    landmarkrestoredPath2 = picturePath;

                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentLandmark1Bitamp;
                    CurrentLandmark1Bitamp=BitmapFactory.decodeFile(picturePath,options);
                    landmarkimage2.setImageBitmap(CurrentLandmark1Bitamp);
                    landmarkimage2.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;
            case 4:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    SharedPreferences.Editor editor = this.getSharedPreferences(VICTIM_PREF, Context.MODE_PRIVATE).edit();
                    editor.putString(landmarkimagepathtag3, picturePath);
                    editor.apply();

                    landmarkrestoredPath3 = picturePath;



                    BitmapFactory.Options options=new BitmapFactory.Options();

                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentLandmark2Bitamp;

                    CurrentLandmark2Bitamp=BitmapFactory.decodeFile(picturePath,options);

                    landmarkimage3.setImageBitmap(CurrentLandmark2Bitamp);
                    landmarkimage3.setScaleType(ImageView.ScaleType.FIT_XY);



                }
                break;

            default:
                break;
        }
    }


    public void updatelocale()
    {
        LatLng affectedlatlon = new LatLng(Float.parseFloat(DistressMessageNearestGroupLat), Float.parseFloat(DistressMessageNearestGroupLon));
        LatLng rescuelatlang = new LatLng(Float.parseFloat(DistressMessageNearestRescueGroupLat), Float.parseFloat(DistressMessageNearestRescueGroupLon));
        Log.i("TRIAL RESCUE", "" + Float.parseFloat(DistressMessageNearestRescueGroupLat) + "" + Float.parseFloat(DistressMessageNearestRescueGroupLon));


        LatLng mypos=new LatLng(mygps.getLatitude(),mygps.getLongitude());

        safezonename.setText("");
        safezonedistanceinm =distancebetweenpoints(affectedlatlon,mypos);
        if (safezonedistanceinm < 1000)
        {
            safezonedistance.setText("DISTANCE: " + Math.round(safezonedistanceinm * 100.0) / 100.0 + " m");
        }
        else
            {
            safezonedistance.setText("DISTANCE: " + Math.round(safezonedistanceinm / 10.0) / 100.0 + " km");
           }

         rescuegroupdistanceinm=distancebetweenpoints(rescuelatlang,mypos);

        rescugroupname.setText("");
        if (rescuegroupdistanceinm < 1000)
        {
            rescuegroupdistance.setText("DISTANCE: " + Math.round(rescuegroupdistanceinm * 100.0) / 100.0 + " m");
        }
        else
            {
            rescuegroupdistance.setText("DISTANCE: " + Math.round(rescuegroupdistanceinm / 10.0) / 100.0 + " km");
            }









        mMap.addMarker(new MarkerOptions().position(rescuelatlang).title("NEAREST RESCUE GROUP").snippet(DistressMessageNearestRescueGroupContact).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions().position(affectedlatlon).title("NEAREST SAFE ZONE").snippet(DistressMessageNearestRescueGroupContact).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(affectedlatlon, 10);
        mMap.animateCamera(cameraUpdate);








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
        mMap.setInfoWindowAdapter(new RvAzure_CustomMarkerWindow(getBaseContext()));


        mylatitude = mygps.getLatitude();
        mylongitude = mygps.getLongitude();


        LatLng mylocation = new LatLng(mylatitude, mylongitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mylocation, 10);
        mMap.animateCamera(cameraUpdate);

        //mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);





   mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
       @Override
       public void onInfoWindowLongClick(Marker marker) {





           Intent phoneIntent=new Intent(Intent.ACTION_DIAL);


           if (ActivityCompat.checkSelfPermission(RvAzure_StuckInThisDisaster.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
           {
               Toast.makeText(RvAzure_StuckInThisDisaster.this,"GRANT PHONE CALL PERMISSION",Toast.LENGTH_SHORT).show();



           }

           phoneIntent.setData(Uri.parse("tel:"+marker.getSnippet()));

           startActivity(phoneIntent);




       }
   });








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


    public boolean isInternetConnection()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class RescueQueryAsync extends AsyncTask<Void, Void, RescueDataList> {

        RescueDataList list;


        protected RescueDataList doInBackground(Void... params)
        {
            String url = "http://codefundoapp.azurewebsites.net/hackathonapi/v1/resources/rescueGroupData";
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                //con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();

                Gson gson = new Gson();


                //JSONObject myResponse = new JSONObject(response.toString());
                list = gson.fromJson(response.toString(), RescueDataList.class);
                //System.out.println(list.toString());
                for (RescueGroupData i : list.data) {
                    System.out.println("NEW STUFF" + i);
                }


                return list;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }

        public BitmapDescriptor getVictimMarkerIcon(String color)
        {
            float[] hsv = new float[3];
            Color.colorToHSV(Color.parseColor(color), hsv);
            return BitmapDescriptorFactory.defaultMarker(hsv[0]);

        }


        protected void onPostExecute(RescueDataList list) {
            //You can access the list here
            ArrayList<RescueGroupData> rescuegroupinfo = list.getData();


            LatLng mypos = new LatLng(mygps.getLatitude(), mygps.getLongitude());

            int initsafezonecounter = 0;
            int initrescuegroupcounter = 0;

            for (int i = 0; i < rescuegroupinfo.size(); i++) {


                switch (rescuegroupinfo.get(i).getGroup_type()) {


                    case "1": //safe zone
                        if (rescuegroupinfo.get(i).getSafety().matches("SAFE")) {
                            LatLng safezone = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
                            mMap.addMarker(new MarkerOptions().position(safezone).title("SAFE ZONE").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(getVictimMarkerIcon("#CC00ff99")));

                            if (initsafezonecounter == 0) {
                                safezonedistanceinm = distancebetweenpoints(mypos, safezone);
                                safezonenamestring = rescuegroupinfo.get(i).getGroup_name();
                                DistressMessageNearestGroupContact= rescuegroupinfo.get(i).getContact_no();

                                initsafezonecounter++;
                            } else {
                                if (safezonedistanceinm > distancebetweenpoints(mypos, safezone)) {
                                    safezonedistanceinm = distancebetweenpoints(mypos, safezone);
                                    safezonenamestring = rescuegroupinfo.get(i).getGroup_name();
                                    DistressMessageNearestGroupContact = rescuegroupinfo.get(i).getContact_no();

                                }
                            }

                        }
                        else
                            {
                            LatLng safezone = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
                            mMap.addMarker(new MarkerOptions().position(safezone).title("UNSAFE ZONE").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(getVictimMarkerIcon("#CCff0000")));
                            }

                        break;

                    case "2"://rescue group
                        LatLng rescuegroup = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(rescuegroup).title("RESCUE GROUP").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(BitmapDescriptorFactory.fromResource(R.drawable.rescue1)));
                                //getVictimMarkerIcon("#CC0099ff")));

                        if (initrescuegroupcounter == 0)
                        {
                            rescuegroupdistanceinm = distancebetweenpoints(mypos, rescuegroup);
                            rescuegroupnamestring = rescuegroupinfo.get(i).getGroup_name();
                            DistressMessageNearestRescueGroupContact = rescuegroupinfo.get(i).getContact_no();
                            initrescuegroupcounter++;
                        }
                        else
                            {
                            if (rescuegroupdistanceinm > distancebetweenpoints(mypos, rescuegroup))
                            {
                                rescuegroupdistanceinm = distancebetweenpoints(mypos, rescuegroup);
                                rescuegroupnamestring = rescuegroupinfo.get(i).getGroup_name();
                                DistressMessageNearestGroupContact= rescuegroupinfo.get(i).getContact_no();

                            }
                        }


                        break;


                    case "3"://relief camp
                        LatLng reliefgroup = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(reliefgroup).title("RELIEF CAMP").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(BitmapDescriptorFactory.fromResource(R.drawable.reliefcamp)));
                        break;


                }
            }

            safezonename.setText(safezonenamestring);

            if (safezonedistanceinm < 1000)
            {
                safezonedistance.setText("DISTANCE: " + Math.round(safezonedistanceinm * 100.0) / 100.0 + " m");
            }
            else
                {
                safezonedistance.setText("DISTANCE: " + Math.round(safezonedistanceinm / 10.0) / 100.0 + " km");


            }



            rescugroupname.setText(rescuegroupnamestring);
            if (rescuegroupdistanceinm < 1000)
            {
                rescuegroupdistance.setText("DISTANCE: " + Math.round(rescuegroupdistanceinm * 100.0) / 100.0 + " m");
            }
            else
                {
                rescuegroupdistance.setText("DISTANCE: " + Math.round(rescuegroupdistanceinm / 10.0) / 100.0 + " km");
               }


        }


    }


    public float distancebetweenpoints(LatLng mypos, LatLng grouppos)
    {
        float[] result = new float[1];

        Location.distanceBetween(mypos.latitude, mypos.longitude, grouppos.latitude, grouppos.longitude, result);

        return result[0];

    }

    private class postSafetyAsync extends AsyncTask<UserData, Void, Void>
    {

        protected Void doInBackground(UserData... data) {

            try {
                String postUrl = "https://aztests.azurewebsites.net/victims/update";
                Gson gson = new Gson();
                System.out.println(data[0]);

                //StringEntity postingString = new StringEntity(gson.toJson(data[0]));

                String postingString = gson.toJson(data[0]);

                System.out.println("Posting data ============== " + postingString);

                HttpURLConnection urlConnection;


                urlConnection = (HttpURLConnection) ((new URL(postUrl).openConnection()));
                //System.out.println("Response code: "+urlConnection.getResponseCode());
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();


                //Write
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postingString.toString());
                writer.close();
                outputStream.close();
                //System.out.println("Response code: "+urlConnection.getResponseCode());


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                String result = sb.toString();

                System.out.println("Result == " + result);
                }
                catch(Exception e) {
                }


                return null;
        }

        protected void onPostExecute(Void... params) {

        }
    }

    private class uploadImage extends AsyncTask<File, Void, Void>
    {

        private byte[] read(File file){
            //File file = new File(filepath);
            int size = (int) file.length();
            //System.out.println("======================================"+size);
            byte[] bytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();
                System.out.println(bytes.length);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return bytes;
        }


        protected Void doInBackground(File... data) {

            try {

                String name = data[0].getName();

                String[] splits = name.split("\\.");

                System.out.println("The format is " + splits[1]);


                URL urlObj = new URL("http://aztests.azurewebsites.net/victim/upload/images/0/0/"+splits[1]+"/blob");
                //URL urlObj = new URL("http://192.168.43.27:8080/facial");
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true);
                //conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestProperty("Content-Type", "image/jpeg");

                OutputStream outputStream = conn.getOutputStream();


                String path = data[0].getPath();

                System.out.println("Path of the image is"+path);

                byte[] bytes;
                bytes = read(data[0]);

                DataOutputStream dout = new DataOutputStream(outputStream);

                dout.write(bytes,0,bytes.length);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //Get response from server
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code : " + responseCode);
                // read in the response from the server
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println(inputLine);
                }
                // close the input stream
                in.close();


                dout.close();
                outputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;


        }


    }



    private class GetClustersAsync extends AsyncTask<Void, Void, ClusterData> {

        ClusterData clusterData;

        @Override
        protected ClusterData doInBackground(Void... params) {
            String url = "https://aztests.azurewebsites.net/victims/disasters/clusters/0";
            try
            {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                //con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());

                JSONObject safeClusters = jsonObject.getJSONObject("safe");
                JSONObject unSafeclusters = jsonObject.getJSONObject("unsafe");

                Gson gson = new Gson();


                //JSONObject myResponse = new JSONObject(response.toString());
                clusterData = gson.fromJson(response.toString(), ClusterData.class);

                HashMap<String,String> safe_clusterData = new HashMap<String,String>();
                HashMap<String,String> unsafe_clusterData = new HashMap<String,String>();

                Iterator iter1 = safeClusters.keys();
                Iterator iter2 = unSafeclusters.keys();

                while(iter1.hasNext())
                {
                    String key = (String) iter1.next();
                    String value = safeClusters.getString(key);
                    safe_clusterData.put(key,value);
                }
                while(iter2.hasNext())
                {
                    String key = (String) iter2.next();
                    String value = unSafeclusters.getString(key);
                    unsafe_clusterData.put(key,value);
                }

                System.out.println("manual output "+safe_clusterData);
                System.out.println("manual output "+unsafe_clusterData);

                clusterData.setSafe(new SingleCusterData(safe_clusterData));
                clusterData.setUnsafe(new SingleCusterData(unsafe_clusterData));

                //System.out.println(list.toString());



                return clusterData;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return clusterData;

        }
        @Override
        protected void onPostExecute(ClusterData data)
        {

            Gson gson = new Gson();

            HashMap<String,String> safe_clusterData = new HashMap<String,String>();
            HashMap<String,String> unsafe_clusterData = new HashMap<String,String>();


            SingleCusterData safeclusters = data.getSafe();
            SingleCusterData unsafeclusters = data.getUnsafe();

            int numsafe = data.getNumsafe();
            int numunsafe = data.getNumunsafe();

            safe_clusterData = safeclusters.getData();

            unsafe_clusterData = unsafeclusters.getData();


            for(String key : safe_clusterData.keySet())
            {
                String values = safe_clusterData.get(key);

                System.out.println("Value = "+values);

                ClusterDatamini doubleList = gson.fromJson("{\"doubles\":"+values+"}".toString(),ClusterDatamini.class);

                double[] list = doubleList.getDoubles();

                System.out.println(list[0]);
                int num_people = (int)list[2];

                double radius = num_people*100;

                LatLng point = new LatLng(list[0],list[1]);
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(point);

                // Radius of the circle
                circleOptions.radius(radius);

                // Border color of the circle
                circleOptions.strokeColor(Color.TRANSPARENT);

                // Fill color of the circle
                circleOptions.fillColor(Color.parseColor("#5500FF00"));

                // Border widtoh of the circle
                circleOptions.strokeWidth(2);

                // Adding the circle to the GoogleMap
                mMap.addCircle(circleOptions);



            }

            for(String key : unsafe_clusterData.keySet())
            {
                String values = unsafe_clusterData.get(key);

                System.out.println("Value = "+values);

                ClusterDatamini doubleList = gson.fromJson("{\"doubles\":"+values+"}".toString(),ClusterDatamini.class);



                double[] list = doubleList.getDoubles();

                System.out.println(list[0]);
                int num_people = (int)list[2];

                double radius = num_people*100;

                LatLng point = new LatLng(list[0],list[1]);
                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(point);

                // Radius of the circle
                circleOptions.radius(radius);

                // Border color of the circle
                circleOptions.strokeColor(Color.TRANSPARENT);

                // Fill color of the circle
                circleOptions.fillColor(Color.parseColor("#55FF0000"));

                // Border width of the circle
                circleOptions.strokeWidth(2);

                // Adding the circle to the GoogleMap
                mMap.addCircle(circleOptions);

            }





        }

    }














    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }






}
