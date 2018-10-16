package c.theinfiniteloop.rvsafe;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

public class ClusterItemsTrial implements ClusterItem
{
    private final LatLng location;

    private  String Title;
    private  String Snippet;




    public ClusterItemsTrial(LatLng location,String snippet,String title)
    {
        Title=title;
        Snippet=snippet;
        this.location = location;
    }


    public void setTitle(String title)
    {
        Title = title;
    }

    public void setSnippet(String snippet)
    {
        Snippet=snippet;
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
        return Title;
    }

    @Nullable
    @Override
    public String getSnippet()
    {
        return Snippet;
    }




}
