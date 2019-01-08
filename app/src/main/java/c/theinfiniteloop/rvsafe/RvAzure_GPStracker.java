package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RvAzure_GPStracker implements LocationListener {

    private Context context;
    private LocationManager locationManager;

    public  String thoroughfare;
    public String cityname;
    public String statename;
    public double latitude;
    public double longitude;


    public String getStatename()
    {
        return statename;
    }
   // public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }



    public String getThoroughfare()
    {
        return thoroughfare;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public String getCityname()
    {
        return cityname;
    }

    public RvAzure_GPStracker(Context context,LocationManager locationManager)
    {
        this.context = context;
        this.locationManager=locationManager;
      Location loc=getLastBestLocation();

      if(loc!=null)
      {
          latitude = loc.getLatitude();
          longitude = loc.getLongitude();
      }
    }

    @Override
    public void onLocationChanged(Location loc)
    {


        if(loc==null)
        {
            loc=getLastBestLocation();
        }
        if(loc!=null)
        {
            longitude =  loc.getLongitude();
            latitude =   loc.getLatitude();


            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses;


            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                if (addresses.size() > 0)
                {

                    thoroughfare=addresses.get(0).getSubThoroughfare()+" "+addresses.get(0).getThoroughfare()+" "+addresses.get(0).getSubLocality();
                    cityname = addresses.get(0).getLocality();
                    statename = addresses.get(0).getAdminArea();


                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        Log.i("Location",""+cityname+statename);


    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    private Location getLastBestLocation()
        {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return null;
            }
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            long GPSLocationTime = 0;
            if (null != locationGPS)
            {
                GPSLocationTime = locationGPS.getTime();
            }

            long NetLocationTime = 0;

            if (null != locationNet)
            {
                NetLocationTime = locationNet.getTime();
            }

            if (0 < GPSLocationTime - NetLocationTime)
            {
                return locationGPS;
            } else

                {
                return locationNet;

                }




    }


}
