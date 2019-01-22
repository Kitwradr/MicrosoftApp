package c.theinfiniteloop.rvsafe;

import android.Manifest;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RvAzure_Disaster_cards extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    static View.OnClickListener myOnClickListener;
    RvAzure_GPStracker locationListener;


    private String usercodepathtag = "USER DETAILS";
    String USER_PREF = "USER-URL";
    String usernamepathtag="USERNAME";

    SharedPreferences sharedPreferences;

    public String LOG_TAG = "AUDIO-LOG";

    private  static  final  int minimumtimeofrequest=100;

    private  static  final  int minimumdistanceofrequest=1;

    private static final int REQUEST_INTERNET_PERMISSION = 201;

    private static final int REQUEST_GPS_PERMISSION = 200;

    private boolean permissionToLocationAccepted = false;

    private boolean permissionToInternetAccepted = false;

    /*an array of permissions for to be requested*/
    private String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.SEND_SMS,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_SMS,Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE};



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*code snippet to request permission for audio */
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        switch (requestCode)
        {
            /*check whether permission to record audio is given*/
            case REQUEST_GPS_PERMISSION:
                permissionToLocationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                permissionToLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                permissionToLocationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                break;


        }
        if (!permissionToLocationAccepted)
            finish();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure__disaster_cards);
        sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        String username=sharedPreferences.getString(usernamepathtag,null);


        ActivityCompat.requestPermissions(this, permissions, REQUEST_GPS_PERMISSION);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hview =navigationView.getHeaderView(0);
        TextView txt=(TextView) hview.findViewById(R.id.header);
        txt.setText(username);



        navigationView.setNavigationItemSelectedListener(this);







        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new RvAzure_GPStracker(getApplicationContext(),locationManager);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }


        boolean gpsenabled=false;
        boolean networkenabled=false;

        gpsenabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkenabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(gpsenabled)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, locationListener);
        }
        else if (networkenabled)
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minimumtimeofrequest,minimumdistanceofrequest,locationListener);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"TURN ON GPS",Toast.LENGTH_SHORT).show();
        }




        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relativelayouthomepage, RvAzure_DisasterCardHome.newInstance());
        transaction.commit();



        Log.i("CURRENT LOCATION:",""+locationListener.getCityname()+" "+locationListener.getStatename());

        startService(new Intent(getApplicationContext(), NotificationService.class));

    }

    @Override
    public void onBackPressed()
    {



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
            {
            super.onBackPressed();
            }


     /*   Intent i=new Intent(RvAzure_Disaster_cards.this,RvAzure_Disaster_cards.class);
        startActivity(i);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relativelayouthomepage, RvAzure_DisasterCardHome.newInstance());
        transaction.commit(); */


    }





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        Fragment selectedFragment = null;
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    selectedFragment = RvAzure_DisasterCardHome.newInstance();
                    break;
                case R.id.sos:
                    selectedFragment = RvAzure_sos.newInstance();
                    break;

                case R.id.am_i_safe:
                    selectedFragment = RvAzure_am_i_safe.newInstance();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.relativelayouthomepage, selectedFragment);
            transaction.commit();
            return true;
        }
    };












    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rv_azure__disaster_cards, menu);
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


            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString(usercodepathtag,null);
            editor.putString(usernamepathtag,null);
            editor.commit();


            Intent i=new Intent(RvAzure_Disaster_cards.this,RvAzure_login.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share)
        {

            /*code to share after app is published */

        }
        else if(id==R.id.medical_id)
        {
            startActivity(new Intent(RvAzure_Disaster_cards.this,AzureMedicalID.class));
        }
        else if (id == R.id.tips)
        {

            startActivity(new Intent(RvAzure_Disaster_cards.this,RvAzure_tips_activity.class));
            /*code to share after app is published */
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





}