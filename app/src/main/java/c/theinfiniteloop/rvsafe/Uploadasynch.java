package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public  class Uploadasynch extends AsyncTask<String, Void, Void> {

    private Exception exception;
    private Context contexts;
    private boolean flag;
    private  String err="";
    public  Uploadasynch(Context context){
        contexts = context;
    }

    private byte[] read(String filepath){
        File file = new File(filepath);
        int size = (int) file.length();
        //System.out.println("======================================"+size);
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            System.out.println(bytes.length);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    protected Void doInBackground(String... urls) {
        try {


            URL urlObj = new URL("http://mlandai.azurewebsites.net/facial");
            //URL urlObj = new URL("http://192.168.43.27:8080/facial");
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true);
            //conn.setUseCaches(false); // Don't use a Cached Copy

            conn.setRequestProperty("Content-Type", "image/jpeg");

            OutputStream outputStream = conn.getOutputStream();


            String path = urls[0];
            //System.out.println("PATH--------------------------------------------"+path);
            byte[] bytes;
            bytes = read(path);
            //System.out.println("-===============================theadefeqfqfwqwf "+ bytes.length);
            DataOutputStream dout = new DataOutputStream(outputStream);

            dout.write(bytes,0,bytes.length);

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
            flag=true;
            // close the input stream
            in.close();


            dout.close();
            outputStream.close();



        } catch (Exception e) {
            this.exception = e;
            e.printStackTrace();
            this.err="error";


        } finally {

        }

return null;

    }


    @Override
    protected void onPostExecute(Void v) {
        // TODO: check this.exception
        // TODO: do something with the feed
        if(flag){
            Toast.makeText(contexts, "upload success ", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(contexts, "HttpHostConnectException Occured ", Toast.LENGTH_SHORT).show();
        }

    }
}
