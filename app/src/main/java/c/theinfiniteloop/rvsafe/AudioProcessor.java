package c.theinfiniteloop.rvsafe;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class AudioProcessor extends AppCompatActivity implements TextToSpeech.OnInitListener
{



    private final int REQ_CODE_SPEECH_INPUT = 100;

    private TextToSpeech tts;

    private String speech_input="";

    List<String> keyset;

    private String weather_string;

    private  static final String intial_query ="DO YOU WANT TO SEND A DISTRESS MESSAGE OR DO YOU WANT TO GET WEATHER REPORT";





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_processor);


     if(isInternetConnection())
     {
         new getWeatherDetails().execute();
     }





        tts = new TextToSpeech(this, this);
        speakOut(intial_query);



        new CountDownTimer(6000,1000)
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

                    int delay=3000;


                    if(speech_input.toLowerCase().contains("weather report"))
                    {




                     speakOut("WEATHER REPORT FOR FIVE DAYS");



                     if(!weather_string.matches(""))
                         speakOut(weather_string);


                       delay=18000;

                    }
                    else if(speech_input.toLowerCase().contains("distress"))
                    {


                         speakOut("WE HAVE NOTIFIED EMERGENCY SERVICES OF YOUR LOCATION, DO NOT PANIC HELP WILL ARRIVE SHORTLY");


                         delay=6000;

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
                break;
            }

        }
    }










    public void onInit(int status)
    {

        if (status == TextToSpeech.SUCCESS)
        {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
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
//

            keyset=new ArrayList<String>(weathermaps.keySet());

            Log.i("CALLED","TEST CALLED"+keyset.size());

            keyset.remove(0);



            weather_string ="";


            for(int i=0;i<keyset.size();i++)
            {
                weather_string+=keyset.get(i)+" , "+weathermaps.get(keyset.get(i))+",";
            }









/*            switch (5)
            {

                case 1:card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    System.out.println("Card data"+ keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                    break;

                case 2:
                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                    Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));



                    break;
                case 3:
                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));


                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                    Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));

                    card3.setVisibility(View.VISIBLE);
                    Date3.setText(keyset.get(2));
                    Desc3.setText("\u2022"+weathermaps.get(keyset.get(2)));



                    break;

                case 4:


                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                    Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));

                    card3.setVisibility(View.VISIBLE);
                    Date3.setText(keyset.get(2));
                    Desc3.setText("\u2022"+weathermaps.get(keyset.get(2)));

                    card4.setVisibility(View.VISIBLE);
                    Date4.setText(keyset.get(3));
                    Desc4.setText("\u2022"+weathermaps.get(keyset.get(3)));





                    break;
                case 5:

                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                      Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));

                    card3.setVisibility(View.VISIBLE);
                    Date3.setText(keyset.get(2));
                    Desc3.setText("\u2022"+weathermaps.get(keyset.get(2)));

                    card4.setVisibility(View.VISIBLE);
                    Date4.setText(keyset.get(3));
                    Desc4.setText("\u2022"+weathermaps.get(keyset.get(3)));

                    card5.setVisibility(View.VISIBLE);
                    Date5.setText(keyset.get(4));
                    Desc5.setText("\u2022"+weathermaps.get(keyset.get(4)));



                    break;


            } */






        }
    }








    public boolean isInternetConnection()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }














}
