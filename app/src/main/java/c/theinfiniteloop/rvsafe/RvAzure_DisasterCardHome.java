package c.theinfiniteloop.rvsafe;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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


        data = new ArrayList<RvAzure_DataModelForCards>();
        adapter = new RvAzure_CustomAdapterForRecyclerView(data);
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.disastercard_home, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        new QueryAsync().execute();

        new GetClustersAsync().execute();





        recyclerView.setAdapter(adapter);


        return view;

    }

    private class QueryAsync extends AsyncTask<Void, Void,DisasterList> {

        DisasterList list;


        protected DisasterList doInBackground(Void... params) {
            String url = "http://codefundoapp.azurewebsites.net/hackathonapi/v1/resources/disasterdata";
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
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();

                Gson gson = new Gson();


                //JSONObject myResponse = new JSONObject(response.toString());
                list = gson.fromJson(response.toString(), DisasterList.class);
                //System.out.println(list.toString());
                for (DisasterData i : list.data) {
                    System.out.println("NEW STUFF" + i);
                }



                return list;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(DisasterList list) {
            //You can access the list here
            ArrayList<DisasterData> recylerviewdata = list.getData();


            data.clear();
            for (int i = 0; i < recylerviewdata.size(); i++) {

                data.add(new RvAzure_DataModelForCards(
                        recylerviewdata.get(i).getDisaster_name(),
                        recylerviewdata.get(i).getGetDisaster_type(),
                        recylerviewdata.get(i).getDisaster_id(),
                        recylerviewdata.get(i).getImage_url()
                ));
            }
            adapter.notifyDataSetChanged();


        }
    }

    private class GetClustersAsync extends AsyncTask<Void, Void, ClusterData> {

        ClusterData clusterData;

        @Override
        protected ClusterData doInBackground(Void... params) {
            String url = "https://aztests.azurewebsites.net/victims/diasasters/clusters/1";
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
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());

                JSONObject safeClusters = jsonObject.getJSONObject("safe");
                JSONObject unSafeclusters = jsonObject.getJSONObject("unsafe");

                Gson gson = new Gson();


                //JSONObject myResponse = new JSONObject(response.toString());
                clusterData = gson.fromJson(response.toString(), ClusterData.class);

                HashMap<String,String> safe_clusterData = new HashMap<String,String>();
                HashMap<String,String> unsafe_clusterData = new HashMap<String,String>();

                Iterator iter1 = safeClusters.keys();
                Iterator iter2 = unSafeclusters.keys();

                while(iter1.hasNext())
                {
                    String key = (String) iter1.next();
                    String value = safeClusters.getString(key);
                    safe_clusterData.put(key,value);
                }
                while(iter2.hasNext())
                {
                    String key = (String) iter2.next();
                    String value = unSafeclusters.getString(key);
                    unsafe_clusterData.put(key,value);
                }

                System.out.println("manual output "+safe_clusterData);
                System.out.println("manual output "+unsafe_clusterData);

                clusterData.setSafe(new SingleCusterData(safe_clusterData));
                clusterData.setUnsafe(new SingleCusterData(unsafe_clusterData));

                //System.out.println(list.toString());



                return clusterData;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return clusterData;

        }
        @Override
        protected void onPostExecute(ClusterData data){

            System.out.println(data);

            int numsafe = data.getNumsafe();
            int numunsafe = data.getNumunsafe();

            SingleCusterData safe = data.getSafe();
            




        }

    }




}

