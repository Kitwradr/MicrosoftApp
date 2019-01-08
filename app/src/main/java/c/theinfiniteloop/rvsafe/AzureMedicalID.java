package c.theinfiniteloop.rvsafe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AzureMedicalID extends AppCompatActivity
{

    Button add;
    ImageView edit;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azure_medical_id);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#FF0000"));
        actionBar.setBackgroundDrawable(colorDrawable);

   EditText  bloodgroup=(EditText)findViewById(R.id.bloodgroup);
   EditText  height=(EditText)findViewById(R.id.height);
   EditText  weight=(EditText)findViewById(R.id.weight);
   EditText  medicalconditions=(EditText)findViewById(R.id.medicalconditions);
   EditText  allergies=(EditText)findViewById(R.id.allergies);
   EditText  medicalnotes=(EditText)findViewById(R.id.medicalnotes);
        add=(Button)findViewById(R.id.addbutton);
        edit=(ImageView)findViewById(R.id.edit);


        disableEditText(bloodgroup);
        disableEditText(height);
        disableEditText(weight);
        disableEditText(medicalconditions);
        disableEditText(allergies);
        disableEditText(medicalnotes);

        edit.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view)
             {
                      enableEditText(bloodgroup);
                      enableEditText(height);
                      enableEditText(weight);
                      enableEditText(medicalconditions);
                      enableEditText(allergies);
                      enableEditText(medicalnotes);
             }
         });

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String bloodgrouptext="";
                String heighttext="";
                String weighttext="";
                String medicalconditionstext="";
                String allergiestext="";
                String medicalnotestext="";


                bloodgrouptext=bloodgroup.getText().toString();
                heighttext=height.getText().toString();
                weighttext=weight.getText().toString();
                medicalconditionstext=medicalconditions.getText().toString();
                allergiestext=allergies.getText().toString();
                medicalnotestext=medicalnotes.getText().toString();

                disableEditText(bloodgroup);
                disableEditText(height);
                disableEditText(weight);
                disableEditText(medicalconditions);
                disableEditText(allergies);
                disableEditText(medicalnotes);

                 PMedicalDetails medicalid=new PMedicalDetails();

                 medicalid.setBlood(bloodgrouptext);
                 medicalid.setAge("20");
                 medicalid.setName("Shubham");
                 medicalid.setHeight(heighttext);
                 medicalid.setWeight(weighttext);
                 medicalid.setMedical_condition(medicalconditionstext);
                 medicalid.setAllergy(allergiestext);
                 medicalid.setNotes(medicalnotestext);
                 medicalid.setUser_id("0");
                 medicalid.setPitureLink("");

                if (isInternetConnection())
                {
                    new postMedicalAsync().execute(medicalid);
                }
            }



        });



    }

    public boolean isInternetConnection()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void disableEditText(EditText editText)
    {
      //  editText.setFocusable(false);
        editText.setEnabled(false);
       // editText.setCursorVisible(false);
    }

    private  void enableEditText(EditText editText)
    {
      //  editText.setFocusable(true);
        editText.setEnabled(true);
       // editText.setInputType(InputType.TYPE_CLASS_TEXT);
       // editText.setCursorVisible(true);
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


}
