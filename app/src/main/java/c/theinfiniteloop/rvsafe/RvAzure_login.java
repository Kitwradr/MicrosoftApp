package c.theinfiniteloop.rvsafe;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Locale;

public class RvAzure_login extends AppCompatActivity implements TextToSpeech.OnInitListener
{
    private static final String TAG = "MyFirebaseMsgService";

    private  static final String intial_query ="DO YOU PREFER ONLY A VOICE BASED INTERFACE";

 private   String usernameentered;
 private   String passwordentered;
 private  String userID;
 private String speech_input="";
 private TextToSpeech tts;




    SharedPreferences sharedPreferences;

    private String usercodepathtag = "USER DETAILS";
    String USER_PREF = "USER-URL";
    String usernamepathtag="USERNAME";
    String dumb_user_name_path="DUMB USER";
    String userrestoredPath;


    private final int REQ_CODE_SPEECH_INPUT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure_login);


        Button signin=findViewById(R.id.signin);
        TextView textView =findViewById(R.id.forgot);
        EditText username=findViewById(R.id.username);
        EditText password =findViewById(R.id.password);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        new getPersonId().execute("suhas");


        if(sharedPreferences.getString(usernamepathtag,null)!=null)
        {
            startActivity(new Intent(RvAzure_login.this,RvAzure_Disaster_cards.class));
        }
     else
        {
            tts = new TextToSpeech(this, this);

            new CountDownTimer(3000,1000)
            {
                @Override
                public void onFinish()
                {
                    speakOut(intial_query);

                }

                @Override
                public void onTick(long l) {

                }
            }.start();



            new CountDownTimer(3000,1000)
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



       FirebaseMessaging.getInstance().subscribeToTopic("hackathon")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                     //   Toast.makeText(RvAzure_login.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        Log.d("Firebase", "token "+ FirebaseInstanceId.getInstance().getToken());


        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                usernameentered=username.getText().toString();

                passwordentered=password.getText().toString();

                 if(usernameentered.matches(""))
                 {
                     username.setError("ENTER VALID USERNAME");
                     username.setText("");


                     password.setText("");


                 }
                 if(passwordentered.matches(""))
                 {
                     password.setError("YOUR PASSWORD IS INVALID");
                     password.setText("");
                 }


                //store username and user ID


           if(!(usernameentered.matches("")&&passwordentered.matches("")))

           {  //call user id code function here
               userID = "1";
               //store

               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString(usercodepathtag, userID);
               editor.putString(usernamepathtag, usernameentered);
               editor.commit();


               if(usernameentered.matches("rescue"))
               {
                   startActivity(new Intent(RvAzure_login.this,rescue_view2.class));
               }
         else {
                   startActivity(new Intent(RvAzure_login.this, RvAzure_Disaster_cards.class));

               }
           }


            }
        });


        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


               Intent browserintent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/3Tyikow5HqZhQoMx1"));
               startActivity(browserintent);

//

            }
        });










    }


    private void askSpeechInput()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }
        catch (ActivityNotFoundException a)
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

                //Trigger Acti


//                    Toast.makeText(this,""+speech_input,Toast.LENGTH_LONG).show();


                    if(speech_input.toLowerCase().contains("yes"))
                    {

               //         Toast.makeText(this,"TEST FOR YES",Toast.LENGTH_LONG).show();

                        startActivity(new Intent(RvAzure_login.this, AudioProcessor.class));


                    }
                    else
                    {

                    }


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
                    || result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS", "This Language is not supported");
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
            Log.i("DO NOT DISTURB","RUN");
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

    private class getPersonId extends AsyncTask<String, Void,String>
    {


        protected String doInBackground(String... params)
        {
            String url = "https://aztests.azurewebsites.net/victims/group/create/faceid";
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoInput(true);
                con.setDoInput(true);
                con.connect();

                JSONObject json = new JSONObject();
                json.put("name",params[0].toString());

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


                JSONObject myResponse = new JSONObject(response.toString());

                String personid = myResponse.getString("personId");



                return personid;

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(String personid)
        {
            System.out.println("person ID is -------"+personid);

            //send this personID as 2nd element of arraylist to async of next uploadimageasync

        }
    }
    // Arraylist index 0 contains the filepath of image to be uplaoded and index 1 contains the facematch ID
    private class uploadImageFaceMatch extends AsyncTask<ArrayList<String>, Void, Void>
    {

        private byte[] read(String pathname){
            File file = new File(pathname);
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


        protected Void doInBackground(ArrayList<String>... data) {

            try {
                ArrayList<String> arr_list = data[0];

                String name = arr_list.get(0);
                String faceid = arr_list.get(1);




                URL urlObj = new URL("http://aztests.azurewebsites.net/victims/group/"+faceid+"/addface");
                //URL urlObj = new URL("http://192.168.43.27:8080/facial");
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true);
                //conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestProperty("Content-Type", "application/octet-stream");

                OutputStream outputStream = conn.getOutputStream();




                System.out.println("Path of the image is"+name);

                byte[] bytes;
                bytes = read(name);

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






}
