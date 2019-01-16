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
    Bitmap CurrentVictimBitmap;
    Bitmap CurrentVictimBitamp2;
    Bitmap CurrentVictimBitmap3;
    Button upload;
    String name;

    String picturepath1;
    String picturepath2;
    String picturepath3;



    public static PeopleFinder newInstance(int disaster_id)
    {
        PeopleFinder fragment = new PeopleFinder();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        
        View view=inflater.inflate(R.layout.people_finder, container, false);

        face1=view.findViewById(R.id.image1);
        face2=view.findViewById(R.id.image2);
        face3=view.findViewById(R.id.image3);




        face1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                startActivityForResult(landmarkIntent, 1);
            }
        });

        face2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                startActivityForResult(landmarkIntent, 2);
            }
        });

        face3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent landmarkIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// Start the Intent
                startActivityForResult(landmarkIntent, 3);
            }
        });

        upload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //you can use picture path 1,2 ,3  for images
            //call the async class function here

            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null) {
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
                    options.inBitmap=CurrentVictimBitamp2;

                    CurrentVictimBitamp2=BitmapFactory.decodeFile(picturePath,options);

                    face2.setImageBitmap(CurrentVictimBitamp2);
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

            default:
                break;
        }
    }







}
