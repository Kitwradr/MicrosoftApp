package c.theinfiniteloop.rvsafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
















    }
}
