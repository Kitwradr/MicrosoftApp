package c.theinfiniteloop.rvsafe;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RvAzure_Volunteer extends Fragment
{

    static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static ArrayList<RvAzure_DataModelForVolunteerService> data;
    private RecyclerView.LayoutManager layoutManager;
    private  int disaster_id;

    public static RvAzure_Volunteer newInstance(int disaster_id)
    {
        RvAzure_Volunteer fragment = new RvAzure_Volunteer();
        fragment.disaster_id=disaster_id;
        return fragment;

    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        data = new ArrayList<RvAzure_DataModelForVolunteerService>();
        adapter = new RvAzure_CustomAdapaterForRecylerViewVolunteer(data);


    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        new QueryAsyncVolunteerData().execute();

        View view=inflater.inflate(R.layout.volunteer, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.volunteerrecyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.setAdapter(adapter);

        Toast.makeText(getContext(),""+disaster_id,Toast.LENGTH_SHORT).show();






        return view;

    }

    private class QueryAsyncVolunteerData extends AsyncTask<Void, Void,VolunteerDataList>
    {

        VolunteerDataList volunteerDataList;


        protected VolunteerDataList doInBackground(Void... params)
        {
            String url = "http://codefundoapp.azurewebsites.net/hackathonapi/v1/resources/volunteerData";
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                // optional default is GET
                con.setRequestMethod("GET");
                //add request header
                //con.setRequestProperty("User-Agent", "Mozilla/5.0");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();

                Gson gson = new Gson();



                //JSONObject myResponse = new JSONObject(response.toString());
                volunteerDataList = gson.fromJson(response.toString(),VolunteerDataList.class);
                //System.out.println(list.toString());

                for (VolunteerGroupData i :volunteerDataList.getData())
                {
                    System.out.println("NEW STUFF"+i);
                }



                return volunteerDataList;

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(VolunteerDataList list)
        {
            //You can access the list here
            ArrayList<VolunteerGroupData> recylerviewdata=list.getData();


         for(int i=0;i<recylerviewdata.size();i++)
         {
             if(recylerviewdata.get(i).getDiasaster_id()==disaster_id)
             {
                 data.add(new RvAzure_DataModelForVolunteerService(

                         recylerviewdata.get(i).getGroup_name(),
                         recylerviewdata.get(i).getGroup_description(),
                         recylerviewdata.get(i).getStarted_by(),
                         recylerviewdata.get(i).getStart_location(),
                         recylerviewdata.get(i).getDestination(),
                         recylerviewdata.get(i).getDate(),
                         recylerviewdata.get(i).getExpected_duration(),
                         recylerviewdata.get(i).getMale_members(),
                         recylerviewdata.get(i).getFemale_members(),
                         recylerviewdata.get(i).getNumber_of_members(),
                         recylerviewdata.get(i).get_id(),
                         recylerviewdata.get(i).getGroup_image_url()

                 ));
             }

         }

         adapter.notifyDataSetChanged();




        }
    }




}
