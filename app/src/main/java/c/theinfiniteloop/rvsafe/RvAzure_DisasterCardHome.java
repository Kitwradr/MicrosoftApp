package c.theinfiniteloop.rvsafe;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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


        new QueryAsync().execute();

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

    private class QueryAsync extends AsyncTask<Void, Void,Void> {

        DisasterList list;


        protected Void doInBackground(Void... params) {
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
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                in.close();

                Gson gson = new Gson();



                //JSONObject myResponse = new JSONObject(response.toString());
                list = gson.fromJson(response.toString(),DisasterList.class);
                //System.out.println(list.toString());
                for (DisasterData i :list.data
                        ) {
                    System.out.println(i);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(Void... params)
        {
            //You can access the list here
            ArrayList<DisasterData> disasterlist = list.getData();

        }
    }


}

