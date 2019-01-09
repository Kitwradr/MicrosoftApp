package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class donate extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    RvAzure_GPStracker mygps;
    private static final int minimumtimeofrequest = 100;

    private static final int minimumdistanceofrequest = 1;


    public static boolean button1pressed = false;
    public static boolean button2pressed = false;
    public static boolean button3pressed = false;
    public static boolean button4pressed = false;
    public ArrayList<LatLng> ngolocations;

    PMedicalDetails medicalDetails = new PMedicalDetails();





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



        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        mygps = new RvAzure_GPStracker(getContext(), locationManager);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }


        boolean gpsenabled = false;
        boolean networkenabled = false;

        gpsenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkenabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsenabled)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        }
        else if (networkenabled)
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        }
        else
            {
            Toast.makeText(getContext(), "TURN ON GPS", Toast.LENGTH_SHORT).show();
        }

//        POSTING MEDICL DATA TESTING
//        medicalDetails.setAge("10");
//        medicalDetails.setAllergy("Allergies entered");
//        medicalDetails.setBlood("0+");
//        medicalDetails.setMedical_condition("Medical conditions entered");
//        medicalDetails.setHeight("9");
//        medicalDetails.setPitureLink("picture link to be updated");
//        medicalDetails.setUser_id("0");
//        medicalDetails.setNotes("Notes to be entered");
//        medicalDetails.setWeight("60kg");
//        medicalDetails.setName("Suhas");
//
//        new postMedicalAsync().execute(medicalDetails);


            new getMedicalAsync().execute(0);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_donate, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final Button button1 = view.findViewById(R.id.button1);
        final Button button2 = view.findViewById(R.id.button2);
        final Button button3 = view.findViewById(R.id.button3);
        final Button button4 = view.findViewById(R.id.button4);
        Button button5 = view.findViewById(R.id.button5);


        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

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

                if (button3pressed)
                {
                    button3.setBackgroundResource(R.drawable.oval_button_white);
                    button3pressed = false;
                }
                else
                    {
                    button3.setBackgroundResource(R.drawable.oval_button_light_green);
                    button3pressed = true;
                   }


            }
        });


        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (button4pressed)
                {
                    button4.setBackgroundResource(R.drawable.oval_button_white);
                    button4pressed = false;
                }
                else
                    {
                    button4.setBackgroundResource(R.drawable.oval_button_light_green);
                    button4pressed = true;
                }


            }
        });

        button5.setOnClickListener(new View.OnClickListener()
        {
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

                donateDetails.setId("0");

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


               LatLng pos= new LatLng(mygps.getLatitude(),mygps.getLongitude());
                float mindistance=distancebetweenpoints(pos,ngolocations.get(0));

               int position =0;


               for(int i=0;i<ngolocations.size();i++)
                {
                    if(mindistance>distancebetweenpoints(pos,ngolocations.get(i)))
                    {
                        position=i;
                    }
                }

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_donate_dialog, null);
                dialogBuilder.setView(dialogView);



                final  TextView message=(TextView)dialogView.findViewById(R.id.message);

                message.setText("YOUR DETAILS WILL BE SHARED WITH NGO "+position);



                final EditText phno = (EditText) dialogView.findViewById(R.id.phonenumber);

                phno.setText("PHONE NO: "+phonenumber);


                final EditText address=(EditText)dialogView.findViewById(R.id.address);


                if(mygps.getThoroughfare()!=null)
                {
                    address.setText("ADDRESS: " + mygps.getThoroughfare());
                }
                else
                    {
                    address.setText("ADDRESS: ");
                    }


                    final EditText city=(EditText)dialogView.findViewById(R.id.city);

              if(mygps.getCityname()!=null)
              {
                  city.setText("CITY: " + mygps.getCityname());
              }
              else
              {
                  city.setText("CITY: ");
              }

                dialogBuilder.setTitle("WE ARE GLAD YOU WANT TO HELP");
                dialogBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {


                        donateDetails.setPhone_number((""+phno.getText()).split("PHONE NO: ")[1]);
                        donateDetails.setAddress((""+address.getText()).split("ADDRESS: ")[1]);
                        donateDetails.setCity((""+city.getText()).split("CITY: ")[1]);
                        

                        donateDetails.setItems(items);


                        if(isInternetConnection())
                        {
                            new postDonateAsync().execute(donateDetails);
                        }

                        //do something with edt.getText().toString();
                    }
                });
                dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();











            //

        }
        });










        return view;

    }



    public boolean isInternetConnection()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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


        ngolocations=new ArrayList<>();
        ngolocations.add(new LatLng(12.934,77.593));
        ngolocations.add(new LatLng(13.01,77.64));
        ngolocations.add(new LatLng(13.05,77.68));
        ngolocations.add(new LatLng(12.988,77.565));
        ngolocations.add(new LatLng(13.0677,77.564));
        ngolocations.add(new LatLng(12.938,77.61));




       for(int i=0;i<ngolocations.size();i++)
       {
           mMap.addMarker(new MarkerOptions().position(ngolocations.get(i)).title("NGO "+i).snippet("CONTACT NUMBER  984543482"+i).icon(BitmapDescriptorFactory.fromResource(R.drawable.ngo)));

       }



    }



    public float distancebetweenpoints(LatLng mypos, LatLng grouppos)
    {
        float[] result = new float[1];

        Location.distanceBetween(mypos.latitude, mypos.longitude, grouppos.latitude, grouppos.longitude, result);

        return result[0];

    }









    private class postDonateAsync extends AsyncTask<DonateDetails, Void, Void>
    {

        @Override
        protected Void doInBackground(DonateDetails... data)
        {
            Log.i("new stuffs",data.toString());


            try {
                String postUrl = "https://aztests.azurewebsites.net/ngo/resources/add";
                Gson gson = new Gson();
                System.out.println(data[0]);

                //StringEntity postingString = new StringEntity(gson.toJson(data[0]));

                String postingString = gson.toJson(data[0]);

                System.out.println("Posting data = "+ postingString);

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
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"))) {
                    writer.write(postingString.toString());
                    writer.close();
                }
                outputStream.close();
                //System.out.println("Response code: "+urlConnection.getResponseCode());


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null)
                {
                    sb.append(line);
                }

                bufferedReader.close();
                String result = sb.toString();

                System.out.println("Result == "+result);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }

    }

    private class postMedicalAsync extends AsyncTask<PMedicalDetails, Void, Void>
    {

        @Override
        protected Void doInBackground(PMedicalDetails... data)
        {
            Log.i("new stuffs",data.toString());


            try {
                String postUrl = "https://aztests.azurewebsites.net/victims/update/medical";
                Gson gson = new Gson();
                System.out.println(data[0]);

                //StringEntity postingString = new StringEntity(gson.toJson(data[0]));

                String postingString = gson.toJson(data[0]);

                System.out.println("Posting data = "+ postingString);

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
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"))) {
                    writer.write(postingString.toString());
                    writer.close();
                }
                outputStream.close();
                //System.out.println("Response code: "+urlConnection.getResponseCode());


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null)
                {
                    sb.append(line);
                }

                bufferedReader.close();
                String result = sb.toString();

                System.out.println("Result == "+result);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }

    }

    private class getMedicalAsync extends AsyncTask<Integer, Void,PMedicalDetails> {

        PMedicalDetails list;


        protected PMedicalDetails doInBackground(Integer... params)
        {
            String url = "https://aztests.azurewebsites.net/victims/get/medical";
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoInput(true);
                con.setDoInput(true);
                con.connect();

                JSONObject json = new JSONObject();
                json.put("user_id",params[0].toString());

                OutputStream outputStream = con.getOutputStream();
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"))) {
                    writer.write(json.toString());
                    writer.close();
                }
                outputStream.close();



                System.out.println("\nSending 'POST' request to URL : " + url);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                int responseCode = con.getResponseCode();

                System.out.println("Response Code : " + responseCode);
                //add request header

                in.close();

                Gson gson = new Gson();


                JSONObject myResponse = new JSONObject(response.toString());
                JSONObject data = myResponse.getJSONObject("data");
                list = gson.fromJson(data.toString(), PMedicalDetails.class);
                System.out.println(list.toString());




                return list;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(DisasterList list)
        {



        }
    }







}
