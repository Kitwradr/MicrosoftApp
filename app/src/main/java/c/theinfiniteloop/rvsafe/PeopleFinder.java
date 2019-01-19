package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class PeopleFinder extends Fragment
{

    ImageView face1;
    ImageView face2;
    ImageView face3;

    ImageView face4;
    ImageView face5;
    ImageView face6;

    ImageView face7;
    ImageView face8;
    ImageView face9;


    Bitmap CurrentVictimBitmap;
    Bitmap CurrentVictimBitmap2;
    Bitmap CurrentVictimBitmap3;

    Bitmap CurrentVictimBitmap4;
    Bitmap CurrentVictimBitmap5;
    Bitmap CurrentVictimBitmap6;

    Bitmap CurrentVictimBitmap7;
    Bitmap CurrentVictimBitmap8;
    Bitmap CurrentVictimBitmap9;
    Button upload;
    String name;
    EditText name_of_person;

    String picturepath1="";
    String picturepath2="";
    String picturepath3="";

    String picturepath4="";
    String picturepath5="";
    String picturepath6="";
    String picturepath7="";
    String picturepath8="";
    String picturepath9="";


    public static PeopleFinder newInstance()
    {
        PeopleFinder fragment = new PeopleFinder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(isInternetConnection())
        {

            new getPersonId().execute();
        }


    }




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.people_finder, container, false);

        try{

            name_of_person=view.findViewById(R.id.name);

            face1 = view.findViewById(R.id.image1);
            face2 = view.findViewById(R.id.image2);
            face3 = view.findViewById(R.id.image3);


            face4 = view.findViewById(R.id.image4);
            face5 = view.findViewById(R.id.image5);
            face6 = view.findViewById(R.id.image6);


            face7 = view.findViewById(R.id.image7);
            face8 = view.findViewById(R.id.image8);
            face9 = view.findViewById(R.id.image9);



            face1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 1);
                }
            });

            face2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 2);
                }
            });

            face3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 3);
                }
            });

            face4.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 4);
                }
            });
            face5.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 5);
                }
            });
            face6.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 6);
                }
            });


            face7.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 7);
                }
            });

            face8.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 8);
                }
            });

            face9.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                    startActivityForResult(landmarkIntent, 9);
                }
            });




            upload.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ArrayList<String> temp=new ArrayList<>();
                    if(name_of_person.getText().toString().matches(""))
                    {
                        name_of_person.setError("enter the name of the person");
                    }
                    else
                    {
                        name=name_of_person.getText().toString();


                        if(!picturepath1.matches(""))
                        {
                         temp.add(name);
                         temp.add(picturepath1);
                         new getPersonId().execute(temp);
                        }
                        temp.clear();


                        if(!picturepath2.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath2);
                            new getPersonId().execute(temp);
                        }
                        temp.clear();


                        if(!picturepath3.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath3);
                            new getPersonId().execute(temp);
                        }

                        temp.clear();

                        if(!picturepath4.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath4);
                            new getPersonId().execute(temp);
                        }

                        temp.clear();

                        if(!picturepath5.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath5);
                            new getPersonId().execute(temp);
                        }

                        temp.clear();

                        if(!picturepath6.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath6);
                            new getPersonId().execute(temp);
                        }

                        temp.clear();

                        if(!picturepath7.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath7);
                            new getPersonId().execute(temp);
                        }

                        temp.clear();

                        if(!picturepath8.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath8);
                            new getPersonId().execute(temp);
                        }

                        temp.clear();

                        if(!picturepath9.matches(""))
                        {
                            temp.add(name);
                            temp.add(picturepath9);
                            new getPersonId().execute(temp);
                        }


                    }//you can use picture path 1,2 ,3  for images//call the async class function here
                }
            });

        }
    catch (Exception e)
    {
     e.printStackTrace();
    }




        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath1=picturepath1;

                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap;
                    CurrentVictimBitmap=BitmapFactory.decodeFile(picturePath,options);
                    face1.setImageBitmap(CurrentVictimBitmap);
                    face1.setScaleType(ImageView.ScaleType.FIT_XY);



                }
                break;
            case 2:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                   picturepath2=picturePath;

                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap2;

                    CurrentVictimBitmap2=BitmapFactory.decodeFile(picturePath,options);

                    face2.setImageBitmap(CurrentVictimBitmap2);
                    face2.setScaleType(ImageView.ScaleType.FIT_XY);



                }
                break;
            case 3:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath3=picturePath;


                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap3;
                    CurrentVictimBitmap3=BitmapFactory.decodeFile(picturePath,options);
                    face3.setImageBitmap(CurrentVictimBitmap3);
                    face3.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;
            case 4:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath4=picturePath;


                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap4;
                    CurrentVictimBitmap4=BitmapFactory.decodeFile(picturePath,options);
                    face4.setImageBitmap(CurrentVictimBitmap4);
                    face4.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;
            case 5:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath5=picturePath;


                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap5;
                    CurrentVictimBitmap5=BitmapFactory.decodeFile(picturePath,options);
                    face5.setImageBitmap(CurrentVictimBitmap5);
                    face5.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;

            case 6:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath6=picturePath;


                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap6;
                    CurrentVictimBitmap6=BitmapFactory.decodeFile(picturePath,options);
                    face6.setImageBitmap(CurrentVictimBitmap6);
                    face6.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;
            case 7:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath7=picturePath;


                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap7;
                    CurrentVictimBitmap7=BitmapFactory.decodeFile(picturePath,options);
                    face7.setImageBitmap(CurrentVictimBitmap7);
                    face7.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;

            case 8:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath8=picturePath;


                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap8;
                    CurrentVictimBitmap8=BitmapFactory.decodeFile(picturePath,options);
                    face8.setImageBitmap(CurrentVictimBitmap8);
                    face8.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;

            case 9:
                if (resultCode == RESULT_OK && data != null)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    picturepath9=picturePath;


                    BitmapFactory.Options options=new BitmapFactory.Options();
                    options.inPreferredConfig=Bitmap.Config.RGB_565;
                    options.inBitmap=CurrentVictimBitmap9;
                    CurrentVictimBitmap9=BitmapFactory.decodeFile(picturePath,options);
                    face9.setImageBitmap(CurrentVictimBitmap9);
                    face9.setScaleType(ImageView.ScaleType.FIT_XY);


                }
                break;



            default:
                break;
        }
    }






    private class getPersonId extends AsyncTask<ArrayList<String>, Void,ArrayList<String>>
    {


        protected ArrayList<String> doInBackground(ArrayList<String>... params)
        {
            ArrayList<String> inputList = params[0];
            ArrayList<String> returnList = new ArrayList<String>();
            String url = "https://aztests.azurewebsites.net/victims/group/create/faceid";
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoInput(true);
                con.setDoInput(true);
                con.connect();

                JSONObject json = new JSONObject();
                json.put("name",inputList.get(0));

                OutputStream outputStream = con.getOutputStream();
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"))) {
                    writer.write(json.toString());
                    writer.close();
                }
                outputStream.close();



                System.out.println("\nSending 'POST' request to URL : " + url);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println(response);
                int responseCode = con.getResponseCode();

                System.out.println("Response Code : " + responseCode);
                //add request header

                in.close();


                JSONObject myResponse = new JSONObject(response.toString());

                String personid = myResponse.getString("personId");

                returnList.add(personid);
                returnList.add(inputList.get(1));

                return returnList;

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(ArrayList<String> arr_list)
        {
            System.out.println("person ID is -------"+arr_list.get(0));

            //send this personID as 2nd element of arraylist to async of next uploadimageasync
            //1st element is the filepath which is arr_list.get(1)
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(arr_list.get(1));
            arrayList.add(arr_list.get(0));

            new uploadImageFaceMatch().execute(arrayList);

        }
    }
    // Arraylist index 0 contains the filepath of image to be uplaoded and index 1 contains the facematch ID
    private class uploadImageFaceMatch extends AsyncTask<ArrayList<String>, Void, Void>
    {

        private byte[] read(String pathname){
            File file = new File(pathname);
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


        protected Void doInBackground(ArrayList<String>... data) {

            try {
                ArrayList<String> arr_list = data[0];

                String name = arr_list.get(0);
                String faceid = arr_list.get(1);




                URL urlObj = new URL("http://aztests.azurewebsites.net/victims/group/"+faceid+"/addface");
                //URL urlObj = new URL("http://192.168.43.27:8080/facial");
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true);
                //conn.setUseCaches(false); // Don't use a Cached Copy

                conn.setRequestProperty("Content-Type", "application/octet-stream");

                OutputStream outputStream = conn.getOutputStream();




                System.out.println("Path of the image is"+name);

                byte[] bytes;
                bytes = read(name);

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
                // close the input stream
                in.close();


                dout.close();
                outputStream.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;


        }


    }


    public boolean isInternetConnection()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }










}
