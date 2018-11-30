package c.theinfiniteloop.rvsafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class RvAzure_VolunteerGroupProfile extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure__volunteer_group_profile);

        RvAzure_DataModelForVolunteerService volunteergroupinfo= (RvAzure_DataModelForVolunteerService) getIntent().getSerializableExtra("VOLUNTEER-INFO");

        TextView teamname=findViewById(R.id.vteam_name);
        TextView groupdescription=findViewById(R.id.vteam_description);
        TextView startedby=findViewById(R.id.vteam_teamlead);
        TextView startlocation=findViewById(R.id.vteam_start_location);
        TextView destination=findViewById(R.id.vteam_destination_location);
        TextView startdate=findViewById(R.id.vteam_start_date);
        TextView expectedduration=findViewById(R.id.vteam_duration);
        TextView malemembers=findViewById(R.id.vteam_number_of_male_members);
        TextView femalemembers=findViewById(R.id.vteam_number_of_female_members);
        ImageView teamimage=findViewById(R.id.v_team_image);
        Button request_to_join=findViewById(R.id.vteam_request_to_join);






         teamname.setText(volunteergroupinfo.getTeamname());
         groupdescription.setText(volunteergroupinfo.getDescription());
         startedby.setText(volunteergroupinfo.getStartedby());
         startlocation.setText(volunteergroupinfo.getStartlocation());
         destination.setText(volunteergroupinfo.getDestination());
         startdate.setText(volunteergroupinfo.getStartdate());
         expectedduration.setText(volunteergroupinfo.getExpectedduration());
         malemembers.setText(volunteergroupinfo.getMalemembers());
         femalemembers.setText(volunteergroupinfo.getFemalemembers());








        Picasso.get().load(volunteergroupinfo.getTeamimage()).placeholder(R.drawable.volunteergroup1).error(R.drawable.volunteergroup2).into(teamimage);



     request_to_join.setOnClickListener(new View.OnClickListener()
     {
         @Override
         public void onClick(View view)
         {
             Intent phoneIntent=new Intent(Intent.ACTION_DIAL);
             if (ActivityCompat.checkSelfPermission(RvAzure_VolunteerGroupProfile.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
             {
                 Toast.makeText(RvAzure_VolunteerGroupProfile.this,"GRANT PHONE CALL PERMISSION",Toast.LENGTH_SHORT).show();
                 return;
             }

             phoneIntent.setData(Uri.parse("tel:9886968274"));

             startActivity(phoneIntent);
         }
     });












    }
}
