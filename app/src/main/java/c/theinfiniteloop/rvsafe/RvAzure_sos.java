package c.theinfiniteloop.rvsafe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RvAzure_sos extends Fragment
{

    public static RvAzure_sos newInstance()
    {
        RvAzure_sos fragment = new RvAzure_sos();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.sos_fragment, container, false);
        return view;

    }







}
