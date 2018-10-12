package c.theinfiniteloop.rvsafe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class RvAzure_MyGroup extends Fragment
{

     private int disater_id;


    public static RvAzure_MyGroup newInstance(int disaster_id)
    {
        RvAzure_MyGroup fragment = new RvAzure_MyGroup();
        fragment.disater_id=disaster_id;
        return fragment;

    }








    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.mygroup_fragment, container, false);


        return view;


    }


}
