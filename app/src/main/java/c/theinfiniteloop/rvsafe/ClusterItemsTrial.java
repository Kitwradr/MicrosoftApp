package c.theinfiniteloop.rvsafe;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

public class ClusterItemsTrial implements ClusterItem
{
    private final LatLng location;

    public ClusterItemsTrial(LatLng location) {
        this.location = location;
    }


    @Override
    public double getLatitude()
    {
        return location.latitude;
    }

    @Override
    public double getLongitude()
    {
        return location.longitude;
    }

    @Nullable
    @Override
    public String getTitle() {
        return "user id xx";
    }

    @Nullable
    @Override
    public String getSnippet()
    {
        return "contact yyy";
    }




}
