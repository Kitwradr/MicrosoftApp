package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.util.JSON;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class AudioProcessor extends AppCompatActivity implements TextToSpeech.OnInitListener {


    RvAzure_GPStracker mygps;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private TextToSpeech tts;

    private String speech_input = "";

    float safezonedistanceinm;
    float rescuegroupdistanceinm;

    String safezonenamestring;
    String rescuegroupnamestring;

    private String earthquakes_text="if you are in an earthquake drop down and take cover under a desk or table, "+"Stay inside until the tremors " +
            "stop and it is safe to exit, "+"Stay away from windows and light fixtures, "+"If you are in bed hold on and stay there. " +
            "Protect your head with a pillow, "+" If you are outdoors drop down to ground and stay clear from buildings ";
   private String floods_text="If you are under a flood warning, find a safe shelter right away, "+"Move to higher ground, "+"Do not walk, " +
           "swim or drive through flood waters, "+"if you are told to evacuate immediately do so";

   private String tsunanmi_text="if you are in a tsunami area and there is an earthquake, then first protect yourself from " +
           "the earthquake, "+"Drop, cover, and hold on, "+"cover your head and neck with your arms, "+"if you are outside a tsunami hazardous " +
           "zone then stay where you are unless officials tell you otherwise, "+" If you are in water try to grab onto something that floats, "+"If you " +
           "are in a boat, then face the direction of the waves and head out to sea, "+"If you are in harbor then go inland";

   private String volcano_text="if you are in a volcanic eruption zone, "+"evacuate only as recommended by authorities to stay clear of lava, mud flows and " +
           "flying debris, "+"Cover your mouth with a damp handkerchief, "+" Shut all the doors and windows and stay indoors ";

    List<String> keyset;

    private String weather_string;

    private static final String intial_query = "hey there, i am RV safe, mini assistant, HOW CAN I HELP YOU";

    private static final int minimumtimeofrequest = 100;

    private static final int minimumdistanceofrequest = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_processor);

        CircleImageView auidoimage=findViewById(R.id.audio_image);
        auidoimage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pulse));


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mygps = new RvAzure_GPStracker(getApplicationContext(), locationManager);



        boolean gpsenabled = false;
        boolean networkenabled = false;

        gpsenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkenabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsenabled)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        }
        else if (networkenabled)
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        }
        else
            {
            Toast.makeText(getApplicationContext(), "TURN ON GPS", Toast.LENGTH_SHORT).show();
        }

        if (isInternetConnection())
        {

            new getWeatherDetails().execute();

        }





        tts = new TextToSpeech(this, this);
        speakOut(intial_query);



        new CountDownTimer(10000,1000)
        {
            @Override
            public void onFinish()
            {
                askSpeechInput();
            }

            @Override
            public void onTick(long l)
            {

            }
        }.start();


    }



    private void askSpeechInput()
    {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");

        try
        {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }
        catch(ActivityNotFoundException a)
        {

             Toast.makeText(getApplicationContext(),"THIS WAS NOT EXPECTED",Toast.LENGTH_SHORT).show();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQ_CODE_SPEECH_INPUT:
            {
                if (resultCode == RESULT_OK && null != data)
                {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //    voiceInput.setText(result.get(0));

                    speech_input=result.get(0);

                    //Trigger Action

                    if(isInternetConnection())
                    {
                        System.out.println("Entered here");
                        new getIntentAsync().execute(speech_input.toLowerCase());
                    }

                    int delay=6000;

                    Log.i("EXECUTABLE2","HEY HEY");

                    if(isInternetConnection())
                    {
                        if(!(speech_input.matches(""))&&speech_input!=null)
                        {
                            Log.i("EXECUTABLE", "HEY HEY");
                    //        new getIntentAsync().execute("send a distress message to my emergency contact");
                        }
                    }

                    new CountDownTimer(delay,1000)
                    {
                        @Override
                        public void onFinish()
                        {
                            askSpeechInput();
                        }

                        @Override
                        public void onTick(long l) {

                        }
                    }.start();

                }
                else
                {
                    speakOut("Sorry i couldn't understand");
                    new CountDownTimer(10000,1000)
                    {
                        @Override
                        public void onFinish()
                        {
                            askSpeechInput();
                        }

                        @Override
                        public void onTick(long l)
                        {

                        }
                    }.start();

                }


                break;
            }

            default:
                {
                speakOut("Sorry i didn't understand");
                    Log.i("WRONG 2","DELAY FOR SPEECH INPUT TO FINISH");
                break;
                }
        }
    }


    public void onInit(int status)
    {

        if (status == TextToSpeech.SUCCESS)
        {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "This Language is not supported");
            } else
            {
                //   btnSpeak.setEnabled(true);
                speakOut(intial_query);
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }


    private void speakOut(String text)
    {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
        while(tts.isSpeaking())
        {
           //Log.i("TTS","TTS IS SPEAKING");
        }
    }


    public void onDestroy()
    {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }





    private class getWeatherDetails extends AsyncTask<Void, Void, LinkedHashMap<String,String>>
    {
        public LinkedHashMap<String,String> doInBackground(Void... data)
        {
            LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
            try {
                String getUrl = "https://aztests.azurewebsites.net/weather";

                URL urlObj = new URL(getUrl);
                //URL urlObj = new URL("http://192.168.43.27:8080/facial");
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("GET");

                conn.setRequestProperty("Content-Type", "image/jpeg");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //Get response from server
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code : " + responseCode);
                // read in the response from the server
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                    System.out.println(inputLine);
                }
                // close the input stream
                in.close();


                JSONObject jObject  = new JSONObject(response.toString());
                //JSONObject menu = jObject.getJSONObject("menu");


                Iterator iter = jObject.keys();
                while(iter.hasNext()) {
                    String key = (String) iter.next();
                    String value = jObject.getString(key);
                    map.put(key, value);


                    System.out.println("key = "+key +"value = "+value);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return map;

        }

        @Override
        protected void onPostExecute(LinkedHashMap<String,String> weathermaps)
        {


            Log.i("CALLED","TEST CALLED"+weathermaps.size());


//           LinkedHashSet<String> keyset = weathermaps.keySet();


            keyset=new ArrayList<String>(weathermaps.keySet());

            Log.i("CALLED","TEST CALLED"+keyset.size());

            keyset.remove(0);



            weather_string ="";


            for(int i=0;i<keyset.size();i++)
            {
                weather_string+=keyset.get(i)+" , "+weathermaps.get(keyset.get(i))+",";
            }


        }
    }


    public boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }





    private void sendSms(String phonenumber)
    {
        String message="Iam in need of help!! http://maps.google.com/?q=<"+mygps.getLatitude()+">,<"+mygps.getLongitude()+">";
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(phonenumber, null, message, null, null);
    }





    private class getIntentAsync extends AsyncTask<String, Void, ArrayList<String>>
    {

        @Override
        protected ArrayList<String> doInBackground(String... speechinput) {
            try {
                System.out.println("--------------------------------------");
                ArrayList<String> strList = new ArrayList<>();

                String LUISurl = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/fe722394-907f-4eff-9fdb-1addf6eaaa30?verbose=true&timezoneOffset=-360&subscription-key=ca3da5d2e8b64c8a80ce5e859ce43c01&q=" + speechinput[0];

                URL obj = new URL(LUISurl);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + LUISurl);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject intents = jsonResponse.getJSONObject("topScoringIntent");
                String intentName = intents.getString("intent");
                System.out.println(intentName.toString()+"INTENT NAME");

                String entityname;

                JSONArray entitities = jsonResponse.getJSONArray("entities");

                if(entitities.length() != 0) {

                    JSONObject entityjson = entitities.getJSONObject(0);

                    entityname = entityjson.getString("entity");

                    if (entityname != null)
                        strList.add(entityname);

                }




                in.close();

                strList.add(intentName);


                return strList;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<String> data)
        {
            System.out.println("---------"+data.toString());
            if(data!=null)
            {
                System.out.println(data);

                if(data.size()==2)
                {
                    //speakOut(earthquakes_text);
                    if(data.get(0).matches("floods"))
                    {
                        speakOut(floods_text);
                    }
                    else if(data.get(0).matches("tsunamis"))
                    {
                        speakOut(tsunanmi_text);
                    }
                    else if(data.get(0).matches("earthquakes"))
                    {
                        speakOut(earthquakes_text);
                    }
                    else if(data.get(0).matches("volcanoes"))
                    {
                        speakOut(volcano_text);
                    }

                }
                else if(data.get(0).toLowerCase().matches("nearestrescuegroup"))
                {

                    if(isInternetConnection())
                    {
                        new RescueQueryAsync().execute();
                    }
                    new CountDownTimer(2000,1000)
                    {
                        @Override
                        public void onFinish()
                        {
                            //do nothing
                        }

                        @Override
                        public void onTick(long l) {

                        }
                    }.start();



                }
                else if(data.get(0).toLowerCase().matches("sosalert"))
                {
                    System.out.println("---------"+"distress message");
                    sendSms("9421394616");
                    speakOut("WE HAVE NOTIFIED EMERGENCY SERVICES OF YOUR LOCATION, DO NOT PANIC HELP WILL ARRIVE SHORTLY");


                    UserData userData = new UserData();
                    userData.setIssafe("false");
                    userData.setLat("" + mygps.getLatitude());
                    userData.setLong("" + mygps.getLongitude());
                    userData.setUser_id("0");
                    Toast.makeText(getApplicationContext(), "YOU MARKED YOUR LOCATION UNSAFE", Toast.LENGTH_SHORT).show();
                   if(isInternetConnection())
                    {
                    new postSafetyAsync().execute(userData);
                    }

                    new CountDownTimer(2000,1000)
                    {
                        @Override
                        public void onFinish()
                        {
                            //do nothing
                        }

                        @Override
                        public void onTick(long l) {

                        }
                    }.start();





                }
                else if(data.get(0).toLowerCase().matches("getweatherreport"))
                {
                    speakOut("WEATHER REPORT FOR FIVE DAYS");
                    if(!weather_string.matches(""))
                        speakOut(weather_string);

                }
                

            }
            else
            {
                Log.i("ID", "NO DATA");
            }

        }


    }

    private class RescueQueryAsync extends AsyncTask<Void, Void, RescueDataList>
    {
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

        protected void onPostExecute(RescueDataList list)
        {
            //You can access the list here
            ArrayList<RescueGroupData> rescuegroupinfo = list.getData();
            LatLng mypos = new LatLng(mygps.getLatitude(), mygps.getLongitude());

            int initsafezonecounter = 0;
            int initrescuegroupcounter = 0;
            for (int i = 0; i < rescuegroupinfo.size(); i++)
            {
                switch (rescuegroupinfo.get(i).getGroup_type()) {


                    case "1": //safe zone
                        if (rescuegroupinfo.get(i).getSafety().matches("SAFE")) {
                            LatLng safezone = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
                        //    mMap.addMarker(new MarkerOptions().position(safezone).title("SAFE ZONE").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(getVictimMarkerIcon("#CC00ff99")));

                            if (initsafezonecounter == 0)
                            {
                                safezonedistanceinm = distancebetweenpoints(mypos, safezone);
                                safezonenamestring = rescuegroupinfo.get(i).getGroup_name();
                            //    DistressMessageNearestGroupContact= rescuegroupinfo.get(i).getContact_no();
                                initsafezonecounter++;
                            }
                            else
                                {
                                if (safezonedistanceinm > distancebetweenpoints(mypos, safezone))
                                {   safezonedistanceinm = distancebetweenpoints(mypos, safezone);
                                    safezonenamestring = rescuegroupinfo.get(i).getGroup_name();
                             //       DistressMessageNearestGroupContact = rescuegroupinfo.get(i).getContact_no();
                                }
                            }

                        }
                        else
                        {
                            LatLng safezone = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
                      //      mMap.addMarker(new MarkerOptions().position(safezone).title("UNSAFE ZONE").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(getVictimMarkerIcon("#CCff0000")));
                        }

                        break;

                    case "2"://rescue group
                        LatLng rescuegroup = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(rescuegroup).title("RESCUE GROUP").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(BitmapDescriptorFactory.fromResource(R.drawable.rescue1)));
                        //getVictimMarkerIcon("#CC0099ff")));

                        if (initrescuegroupcounter == 0)
                        {
                            rescuegroupdistanceinm = distancebetweenpoints(mypos, rescuegroup);
                            rescuegroupnamestring = rescuegroupinfo.get(i).getGroup_name();
                         //   DistressMessageNearestRescueGroupContact = rescuegroupinfo.get(i).getContact_no();
                            initrescuegroupcounter++;
                        }
                        else
                        {
                            if (rescuegroupdistanceinm > distancebetweenpoints(mypos, rescuegroup))
                            {
                                rescuegroupdistanceinm = distancebetweenpoints(mypos, rescuegroup);
                                rescuegroupnamestring = rescuegroupinfo.get(i).getGroup_name();
                           //     DistressMessageNearestGroupContact= rescuegroupinfo.get(i).getContact_no();

                            }
                        }


                        break;


                    case "3"://relief camp
                        LatLng reliefgroup = new LatLng(rescuegroupinfo.get(i).getLatitude(), rescuegroupinfo.get(i).getLongitude());
  //                      mMap.addMarker(new MarkerOptions().position(reliefgroup).title("RELIEF CAMP").snippet(rescuegroupinfo.get(i).getGroup_name()).icon(BitmapDescriptorFactory.fromResource(R.drawable.reliefcamp)));
                        break;
                }
            }


            speakOut("The nearest safe zone, "+safezonenamestring+", is at a distance of "+safezonedistanceinm+"  metres");

            speakOut("The nearest rescue group, "+rescuegroupnamestring+", is at a distance of "+rescuegroupdistanceinm+" metres");



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
















}


