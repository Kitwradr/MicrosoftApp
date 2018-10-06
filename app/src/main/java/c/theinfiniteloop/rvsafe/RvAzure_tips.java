package c.theinfiniteloop.rvsafe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RvAzure_tips extends Fragment
{
    public static RvAzure_tips newInstance()
    {
        RvAzure_tips fragment = new RvAzure_tips();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.tips_fragment, container, false);
        return view;

    }
}
