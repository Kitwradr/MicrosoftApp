package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class RvAzure_CustomMarkerWindow implements GoogleMap.InfoWindowAdapter {
    Context context;
    LayoutInflater layoutInflater;


    public  RvAzure_CustomMarkerWindow(Context context)
    {
       this.context=context;
    }


    @Override
    public View getInfoWindow(Marker marker)
    {




        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.groupmarkerlayout,null);

        Button b=v.findViewById(R.id.markercall);





        TextView markertype=v.findViewById(R.id.marker_type);
        markertype.setText(marker.getTitle());











        return v;



    }

    @Override
    public View getInfoContents(Marker marker)
    {


        return null;
    }







}
