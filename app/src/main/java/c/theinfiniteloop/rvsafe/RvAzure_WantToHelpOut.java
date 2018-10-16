package c.theinfiniteloop.rvsafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rv_azure__want_to_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {

            Intent browserintent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/forms/3Tyikow5HqZhQoMx1"));
            startActivity(browserintent);

            return true;
        }

        return super.onOptionsItemSelected(item);


    }





















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


        public LocationData(double latitude, double longitude, String clientType)
        {
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




}
