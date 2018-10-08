package c.theinfiniteloop.rvsafe;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RvAzure_am_i_safe extends Fragment
{


    private static WebView webView;




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

        return view;

    }
}
