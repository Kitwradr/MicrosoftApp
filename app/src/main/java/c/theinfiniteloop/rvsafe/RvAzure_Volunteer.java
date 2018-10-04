package c.theinfiniteloop.rvsafe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

public class RvAzure_Volunteer extends Fragment
{

    static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static ArrayList<RvAzure_DataModelForVolunteerService> data;
    private RecyclerView.LayoutManager layoutManager;

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


        recyclerView = (RecyclerView) view.findViewById(R.id.volunteerrecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        data = new ArrayList<RvAzure_DataModelForVolunteerService>();
        for (int i = 0; i < RvAzure_MyDataVolunteer.volunteerteamname.length; i++)
        {
            data.add(new RvAzure_DataModelForVolunteerService(

                    RvAzure_MyDataVolunteer.volunteerteamname[i],
                    RvAzure_MyDataVolunteer.startlocation[i],
                    RvAzure_MyDataVolunteer.startdate[i],
                    RvAzure_MyDataVolunteer.numberofmembers[i],
                    RvAzure_MyDataVolunteer.id_[i],
                    RvAzure_MyDataVolunteer.drawableArray[i]

                    ));
        }

        adapter = new RvAzure_CustomAdapaterForRecylerViewVolunteer(data);
        recyclerView.setAdapter(adapter);













        return view;

    }




}
