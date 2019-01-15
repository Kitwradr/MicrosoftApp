package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class RvAzure_sos extends Fragment
{


    SharedPreferences sharedPreferences;

    RvAzure_GPStracker mygps;
    private static final int minimumtimeofrequest = 100;

    private static final int minimumdistanceofrequest = 1;


    private String contact1phone = "CONTACT-1-PH";
    private String contact2phone = "CONTACT-2-PH";
    private String contact3phone = "CONTACT-3-PH";

    private String contact1name = "CONTACT-1-NM";
    private String contact2name = "CONTACT-2-NM";
    private String contact3name = "CONTACT-3-NM";






    String contact1phonerestored;
    String contact2phonerestored;
    String contact3phonerestored;

    String contact1namerestored;
    String contact2namerestored;
    String contact3namerestored;




    String CONTACT_PREF = "CONTACT-PREF";


    FloatingActionButton fab;

    ImageView contactimage1;
    ImageView contactimage2;
    ImageView contactimage3;




    TextView contact1;
    TextView contact2;
    TextView contact3;







    public static RvAzure_sos newInstance()
    {
        RvAzure_sos fragment = new RvAzure_sos();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);


    }






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.sos_fragment, container, false);



        ImageView police=view.findViewById(R.id.police);
        ImageView ambulance=view.findViewById(R.id.ambulance);
        ImageView firebrigade=view.findViewById(R.id.firebrigade);

         contactimage1=view.findViewById(R.id.contactimage1);
         contactimage2=view.findViewById(R.id.contactimage2);
         contactimage3=view.findViewById(R.id.contactimage3);




        contact1=view.findViewById(R.id.contact1);
        contact2=view.findViewById(R.id.contact2);
        contact3=view.findViewById(R.id.contact3);



         fab =view.findViewById(R.id.floatb);


        sharedPreferences = getContext().getSharedPreferences(CONTACT_PREF, Context.MODE_PRIVATE);

        contact1phonerestored = sharedPreferences.getString(contact1phone, null);
        contact1namerestored = sharedPreferences.getString(contact1name, null);

        if(contact1namerestored!=null)
        {
            contact1.setText(contact1namerestored);
            contactimage1.setImageResource(R.drawable.ic_account_circle_black_24dp);

//            Uri   uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+getContext().getPackageName()+"/drawable/" + );
//            Picasso.get().load(R.drawable.ic_account_circle_black_24dp).error(R.drawable.ic_account_circle_black_24dp).into(contactimage1);



        }




        contact2phonerestored = sharedPreferences.getString(contact2phone, null);
        contact2namerestored = sharedPreferences.getString(contact2name, null);
        if(contact2namerestored!=null)
        {
            contact2.setText(contact2namerestored);
            contactimage2.setImageResource(R.drawable.ic_account_circle_black_24dp);

//            Picasso.get().load(R.drawable.ic_account_circle_black_24dp).error(R.drawable.ic_account_circle_black_24dp).into(contactimage2);


        }




        contact3phonerestored = sharedPreferences.getString(contact3phone, null);
        contact3namerestored= sharedPreferences.getString(contact3name, null);

        if(contact3namerestored!=null)
        {
            contact3.setText(contact3namerestored);
          contactimage3.setImageResource(R.drawable.ic_account_circle_black_24dp);

  //          Picasso.get().load(R.drawable.ic_account_circle_black_24dp).error(R.drawable.ic_account_circle_black_24dp).into(contactimage3);


        }











        police.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent phoneIntent=new Intent(Intent.ACTION_DIAL);


                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getContext(),"GRANT PHONE CALL PERMISSION",Toast.LENGTH_SHORT).show();
                    return;
                }

                phoneIntent.setData(Uri.parse("tel:100"));

                startActivity(phoneIntent);
            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {



                Intent phoneIntent=new Intent(Intent.ACTION_DIAL);


                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getContext(),"GRANT PHONE CALL PERMISSION",Toast.LENGTH_SHORT).show();
                    return;
                }

                phoneIntent.setData(Uri.parse("tel:102"));

                startActivity(phoneIntent);

            }
        });

        firebrigade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent phoneIntent=new Intent(Intent.ACTION_DIAL);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getContext(),"GRANT PHONE CALL PERMISSION",Toast.LENGTH_SHORT).show();
                    return;
                }

                phoneIntent.setData(Uri.parse("tel:101"));

                startActivity(phoneIntent);


            }
        });


        contactimage1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
             Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
             intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
             startActivityForResult(intent,1);
            }




        });

        contactimage2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent,2);
            }




        });

        contactimage3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent,3);
            }




        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


             AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(getContext()) ;

             alertDialogBuilder.setTitle("DO YOU WANT TO SEND A DISTRESS MESSAGE");
             alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i)
                 {
                     if(contact1phonerestored!=null)
                     {
                         sendSms(contact1phonerestored);
                     }

                     if(contact2phonerestored!=null)
                     {
                         sendSms(contact2phonerestored);
                     }


                     if(contact3phonerestored!=null)
                     {
                         sendSms(contact3phonerestored);
                     }


                 }
             })
                     .setNegativeButton(" BACK", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i)
                         {
                             dialogInterface.cancel();
                         }
                     });


               AlertDialog dialog=alertDialogBuilder.create();
               dialog.show();

            }
        });



        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        mygps = new RvAzure_GPStracker(getContext(), locationManager);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return ;
//        }


        boolean gpsenabled = false;
        boolean networkenabled = false;

        gpsenabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkenabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (gpsenabled)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        }
        else if (networkenabled)
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minimumtimeofrequest, minimumdistanceofrequest, mygps);
        }
        else {
            Toast.makeText(getContext(), "TURN ON GPS", Toast.LENGTH_SHORT).show();
        }












        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {

                Bitmap bp=BitmapFactory.decodeResource(getContext().getResources(),R.drawable.ic_account_circle_black_24dp);
                Uri contactData = data.getData();
                Cursor cursor = getContext().getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String photouri =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                //contactName.setText(name);

                contact1.setText(name);
 //              Picasso.get().load(R.drawable.ic_account_circle_black_24dp).into(contactimage1);
   //             Picasso.get().load(R.drawable.ic_account_circle_black_24dp).error(R.drawable.ic_account_circle_black_24dp).into(contactimage1);
                contactimage1.setImageResource(R.drawable.ic_account_circle_black_24dp);


                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(contact1name,name);
                editor.putString(contact1phone,number);

                contact1phonerestored=number;
                editor.apply();

//    if(photouri!=null)
//                {
//                    try
//                    {
//                       bp=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),Uri.parse(photouri));
//                       contactimage1.setImageBitmap(bp);
//
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }

                //contactEmail.setText(email);
            }
        }

        else if(requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {

                Bitmap bp=BitmapFactory.decodeResource(getContext().getResources(),R.drawable.ic_account_circle_black_24dp);
                Uri contactData = data.getData();
                Cursor cursor = getContext().getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String photouri =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                //contactName.setText(name);
                contact2.setText(name);
                contactimage2.setImageResource(R.drawable.ic_account_circle_black_24dp);

   //             Picasso.get().load(R.drawable.ic_account_circle_black_24dp).into(contactimage2);
              //  Picasso.get().load(R.drawable.ic_account_circle_black_24dp).error(R.drawable.ic_account_circle_black_24dp).into(contactimage2);


                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(contact2name,name);
                editor.putString(contact2phone,number);
                editor.apply();
                contact2phonerestored=number;


//                if(photouri!=null)
//                {
//                    try
//                    {
//                       bp=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),Uri.parse(photouri));
//                       contactimage1.setImageBitmap(bp);
//
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }

                //contactEmail.setText(email);
            }
        }
        else if (requestCode == 3)
        {
            if(resultCode == RESULT_OK)
            {

                Bitmap bp=BitmapFactory.decodeResource(getContext().getResources(),R.drawable.ic_account_circle_black_24dp);
                Uri contactData = data.getData();
                Cursor cursor = getContext().getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String photouri =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                //contactName.setText(name);
                contact3.setText(name);
              contactimage3.setImageResource(R.drawable.ic_account_circle_black_24dp);

//                Picasso.get().load(R.drawable.ic_account_circle_black_24dp).into(contactimage3);

               // Picasso.get().load(R.drawable.ic_account_circle_black_24dp).error(R.drawable.ic_account_circle_black_24dp).into(contactimage3);


                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(contact3name,name);
                editor.putString(contact3phone,number);
                editor.apply();



                //                if(photouri!=null)
//                {
//                    try
//                    {
//                       bp=MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),Uri.parse(photouri));
//                       contactimage1.setImageBitmap(bp);
//
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }

                //contactEmail.setText(email);

                contact3phonerestored=number;


            }
        }






    }


    private void sendSms(String phonenumber)
    {
        String message="Iam in need of help!! http://maps.google.com/?q=<"+mygps.getLatitude()+">,<"+mygps.getLongitude()+">";
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(phonenumber, null, message, null, null);
    }

















}
