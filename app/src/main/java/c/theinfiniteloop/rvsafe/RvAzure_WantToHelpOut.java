package c.theinfiniteloop.rvsafe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class RvAzure_WantToHelpOut extends AppCompatActivity
{


    private int disaster_id;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        android.support.v4.app.Fragment selectedFragment = null;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {

                case R.id.navigation_home:
                   selectedFragment = RvAzure_Volunteer.newInstance(disaster_id);
                    break;

                case R.id.navigation_mygroup:
                    selectedFragment=RvAzure_MyGroup.newInstance(disaster_id);
                    break;

                case R.id.navigation_dashboard:
                    selectedFragment = donate.newInstance();
                    break;

            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.wanttohelpout_back, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure__want_to_help_out);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        disaster_id=getIntent().getIntExtra("disaster_id",-1);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wanttohelpout_back, RvAzure_Volunteer.newInstance(disaster_id));
        transaction.commit();



    }

    public static class LocationData
    {

        private double latitude;

        private double longitude;

        private  String clientType;


        public LocationData(double latitude, double longitude, String clientType) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.clientType = clientType;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getClientType() {
            return clientType;
        }

        public void setClientType(String clientType) {
            this.clientType = clientType;
        }


    }


    private class postDonateAsync extends AsyncTask<DonateDetails, Void, Void> {

        @Override
        protected Void doInBackground(DonateDetails... data) {

            try {
                String postUrl = "https://aztests.azurewebsites.net/ngo/resources/add";
                Gson gson = new Gson();
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(postUrl);
                StringEntity postingString = new StringEntity(gson.toJson(data));

                post.setEntity(postingString);
                post.setHeader("Content-type", "application/json");

                HttpResponse response = httpClient.execute(post);


                System.out.println("\nSending 'POST' request to URL : " + postUrl);
                int code = response.getStatusLine().getStatusCode();
                System.out.println("Exited with status code of " + code);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }

    }

}
