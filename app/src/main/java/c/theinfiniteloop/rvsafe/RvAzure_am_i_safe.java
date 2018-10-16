package c.theinfiniteloop.rvsafe;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RvAzure_am_i_safe extends Fragment
{


    private static WebView webView;

    TextView Date1;
    TextView Desc1;

    TextView Date2;
    TextView Desc2;

    TextView Date3;
    TextView Desc3;

    TextView Date4;
    TextView Desc4;

    TextView Date5;
    TextView Desc5;


    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;
    CardView card5;





    public static RvAzure_am_i_safe newInstance()
    {
        RvAzure_am_i_safe fragment = new RvAzure_am_i_safe();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);



    }





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.am_i_safe_webview, container, false);

        webView =(WebView)view.findViewById(R.id.website);

        Date1=(TextView)view.findViewById(R.id.date1);
        Desc1=(TextView)view.findViewById(R.id.desc1);



        Date2=(TextView)view.findViewById(R.id.date2);
        Desc2=(TextView)view.findViewById(R.id.desc2);


        Date3=(TextView)view.findViewById(R.id.date3);
        Desc3=(TextView)view.findViewById(R.id.desc3);


        Date4=(TextView)view.findViewById(R.id.date4);
        Desc4=(TextView)view.findViewById(R.id.desc4);


        Date5=(TextView)view.findViewById(R.id.date5);
        Desc5=(TextView)view.findViewById(R.id.desc5);



        card1=(CardView)view.findViewById(R.id.card1);
        card2=(CardView)view.findViewById(R.id.card2);
        card3=(CardView)view.findViewById(R.id.card3);
        card4=(CardView)view.findViewById(R.id.card4);
        card5=(CardView)view.findViewById(R.id.card5);


        ProgressBar progressBar=view.findViewById(R.id.progressbar);


        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                // hide element by class name
//                webView.loadUrl("javascript:(function() { " +
//                        "document.getElementsByClassName('your_class_name')[0].style.display='none'; })()");
//                // hide element by id
                //webView.loadUrl("javascript:(function() { " +
                //"document.getElementById('smoothmenu1').style.display='none';})()");
                //webView.loadUrl("javascript:(function() { "+ "document.getElementsByClassName('arrowsidemenu')[0].style.display='none';})()");



                //webView.loadUrl("javascript:(function(){ document.getElementsByTagName('table')[0].style.display='none';})()");
                //webView.loadUrl("javascript:(function(){ document.getElementsByTagName('table')[1].style.display='none';})()");

                webView.loadUrl("javascript:(function(){ document.getElementsByTagName('center')[0].getElementsByTagName('font')[0].getElementsByTagName('table')[0].width='100%';})()");

                webView.loadUrl("javascript:(function(){ document.getElementsByTagName('body')[0].background=\"\";})()");



//                webView.loadUrl("javascript:(function(){ var x = document.getElementsByTagName('table')[2]; var y = x.getElementsByTagName('tbody')[0]; var z = y.getElementsByTagName('tr')[0];" +
//                        "var a = z.getElementsByTagName('td')[0]; a.style.display='none'; })()");
//
//                webView.loadUrl("javascript:(function(){ var x = document.getElementsByTagName('table')[2]; var y = x.getElementsByTagName('tbody')[0]; var z = y.getElementsByTagName('tr')[0];" +
//                        "var a = z.getElementsByTagName('td')[3]; a.style.display='none'; })()");

                //webView.loadUrl("javascript:(function(){ document.querySelectorAll('td[width=\"204\"]').style.display='none'; })()");
//                webView.loadUrl("javascript:(function(){document.getElementById('marquee1').style.display='none';})()");
//                webView.loadUrl("javascript:(function(){ document.getElementsByTagName('table')[15].style.display='none';})()");
            }
        });

//        MyWebClient webViewClient = new MyWebClient();
//        webView.setWebViewClient(webViewClient);


        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);


        webView.loadUrl("http://city.imd.gov.in/citywx/city_weather.php?id=42299");


        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);


                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);

            }
        });


     //   new getWeatherDetails().execute();



        return view;

    }
    @Override
    public void onResume() {

        super.onResume();

        getView().setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

               if(keyEvent.getAction()==KeyEvent.ACTION_UP&&i==KeyEvent.KEYCODE_BACK)
               {

                   if(webView.canGoBack())
                   {
                       webView.goBack();
                   }
                   return true;
               }


                return false;
            }
        });
    }


    private class getWeatherDetails extends AsyncTask<Void, Void, LinkedHashMap<String,String>>
    {

        public LinkedHashMap<String,String> doInBackground(Void... data)
        {
            LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();

            try {
                String getUrl = "https://aztests.azurewebsites.net/weather";

                URL urlObj = new URL(getUrl);
                //URL urlObj = new URL("http://192.168.43.27:8080/facial");
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("GET");

                conn.setRequestProperty("Content-Type", "image/jpeg");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                //Get response from server
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code : " + responseCode);
                // read in the response from the server
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println(inputLine);
                }
                // close the input stream
                in.close();


                JSONObject jObject  = new JSONObject(response.toString());
                //JSONObject menu = jObject.getJSONObject("menu");


                Iterator iter = jObject.keys();
                while(iter.hasNext()) {
                    String key = (String) iter.next();
                    String value = jObject.getString(key);
                    map.put(key, value);


                    System.out.println("key = "+key +"value = "+value);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return map;

        }

        @Override
        protected void onPostExecute(LinkedHashMap<String,String> weathermaps)
        {


            Log.i("CALLED","TEST CALLED"+weathermaps.size());


//            LinkedHashSet<String> keyset = weathermaps.keySet();
//

            List<String> keyset=new ArrayList<String>(weathermaps.keySet());

            Log.i("CALLED","TEST CALLED"+keyset.get(1));

            keyset.remove(0);





            switch (5)
            {

                case 1:card1.setVisibility(View.VISIBLE);
                       Date1.setText(keyset.get(0));
                       System.out.println("Card data"+ keyset.get(0));
                       Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                      break;

                case 2:
                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                    Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));



                    break;
                case 3:
                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));


                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                    Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));

                    card3.setVisibility(View.VISIBLE);
                    Date3.setText(keyset.get(2));
                    Desc3.setText("\u2022"+weathermaps.get(keyset.get(2)));



                    break;

                case 4:


                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                    Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));

                    card3.setVisibility(View.VISIBLE);
                    Date3.setText(keyset.get(2));
                    Desc3.setText("\u2022"+weathermaps.get(keyset.get(2)));

                    card4.setVisibility(View.VISIBLE);
                    Date4.setText(keyset.get(3));
                    Desc4.setText("\u2022"+weathermaps.get(keyset.get(3)));





                    break;
                case 5:

                    card1.setVisibility(View.VISIBLE);
                    Date1.setText(keyset.get(0));
                    Desc1.setText("\u2022"+weathermaps.get(keyset.get(0)));

                    card2.setVisibility(View.VISIBLE);
                    Date2.setText(keyset.get(1));
                    Desc2.setText("\u2022"+weathermaps.get(keyset.get(1)));

                    card3.setVisibility(View.VISIBLE);
                    Date3.setText(keyset.get(2));
                    Desc3.setText("\u2022"+weathermaps.get(keyset.get(2)));

                    card4.setVisibility(View.VISIBLE);
                    Date4.setText(keyset.get(3));
                    Desc4.setText("\u2022"+weathermaps.get(keyset.get(3)));

                    card5.setVisibility(View.VISIBLE);
                    Date5.setText(keyset.get(4));
                    Desc5.setText("\u2022"+weathermaps.get(keyset.get(4)));



                    break;


            }






        }
    }



















}
