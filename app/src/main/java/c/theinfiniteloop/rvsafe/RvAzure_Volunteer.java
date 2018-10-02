package c.theinfiniteloop.rvsafe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class RvAzure_Volunteer extends Fragment
{

    public static RvAzure_Volunteer newInstance()
    {
        RvAzure_Volunteer fragment = new RvAzure_Volunteer();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.volunteer, container, false);
        return view;

    }




}
