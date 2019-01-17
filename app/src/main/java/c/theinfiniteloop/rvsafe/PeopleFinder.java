package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Toast;

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

    String picturepath1;
    String picturepath2;
    String picturepath3;

    String picturepath4;
    String picturepath5;
    String picturepath6;
    String picturepath7;
    String picturepath8;
    String picturepath9;


    public static PeopleFinder newInstance()
    {
        PeopleFinder fragment = new PeopleFinder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            View view = inflater.inflate(R.layout.people_finder, container, false);

        try{

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




            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //you can use picture path 1,2 ,3  for images
                    //call the async class function here

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







}
