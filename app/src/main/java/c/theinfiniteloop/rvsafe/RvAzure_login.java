package c.theinfiniteloop.rvsafe;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
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
                   startActivity(new Intent(RvAzure_login.this,Rescuer_View.class));
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
            public void onClick(View view)
            {


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







}
