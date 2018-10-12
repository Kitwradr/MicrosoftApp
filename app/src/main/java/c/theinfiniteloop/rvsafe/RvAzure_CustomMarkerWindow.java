package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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






        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(context,"TEST",Toast.LENGTH_SHORT);
            }
        });




        TextView markertype=v.findViewById(R.id.marker_type);
        TextView markername=v.findViewById(R.id.marker_name);


        markertype.setText(marker.getTitle());
        markername.setText(marker.getSnippet());













        return v;



    }

    @Override
    public View getInfoContents(Marker marker)
    {


        return null;
    }







}
