package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
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
public class RvAzure_login extends AppCompatActivity
{
    private static final String TAG = "MyFirebaseMsgService";

 private   String usernameentered;
 private   String passwordentered;
 private  String userID;
SharedPreferences sharedPreferences;

    private String usercodepathtag = "USER DETAILS";
    String USER_PREF = "USER-URL";
    String usernamepathtag="USERNAME";
    String userrestoredPath;


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


       FirebaseMessaging.getInstance().subscribeToTopic("hackathon")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(RvAzure_login.this, msg, Toast.LENGTH_SHORT).show();
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
            public void onClick(View view) {


               Intent browserintent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/3Tyikow5HqZhQoMx1"));
               startActivity(browserintent);

//

            }
        });










    }

}
