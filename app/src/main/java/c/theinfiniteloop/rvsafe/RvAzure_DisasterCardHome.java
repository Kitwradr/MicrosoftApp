package c.theinfiniteloop.rvsafe;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RvAzure_DisasterCardHome extends Fragment
{


    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static ArrayList<RvAzure_DataModelForCards> data;
    private RecyclerView.LayoutManager layoutManager;


    public static RvAzure_DisasterCardHome newInstance()
    {
        RvAzure_DisasterCardHome fragment = new RvAzure_DisasterCardHome();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.disastercard_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());




            /*    QueryData queryData=new QueryData();
                List<Integer> disids=QueryData.queryDisasterids();

                Log.i("NEW DATA",""+disids.get(1));*/






        data = new ArrayList<RvAzure_DataModelForCards>();
        for (int i = 0; i < RvAzure_MyDataForCards.nameArray.length; i++)
        {
            data.add(new RvAzure_DataModelForCards(
                    RvAzure_MyDataForCards.nameArray[i],
                    RvAzure_MyDataForCards.typeArray[i],
                    RvAzure_MyDataForCards.id_[i],
                    RvAzure_MyDataForCards.drawableArray[i]
            ));
        }

        adapter = new RvAzure_CustomAdapterForRecyclerView(data);
        recyclerView.setAdapter(adapter);






        return view;

    }
}
