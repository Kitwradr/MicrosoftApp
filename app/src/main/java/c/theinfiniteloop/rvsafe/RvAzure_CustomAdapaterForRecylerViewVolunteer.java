package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RvAzure_CustomAdapaterForRecylerViewVolunteer extends RecyclerView.Adapter<RvAzure_CustomAdapaterForRecylerViewVolunteer.VolunteerViewHolder>
{
    private ArrayList<RvAzure_DataModelForVolunteerService> dataSet;

    public static class VolunteerViewHolder extends RecyclerView.ViewHolder
    {

        TextView textViewVTeamName;
        TextView textViewVStartLocation;
        TextView textViewVStartDate;
        TextView textViewNumberOfMembers;
        ImageView imageViewVteamImage;
        Button ViewComposition;
        final Context context;
        public VolunteerViewHolder(View itemView)
        {
            super(itemView);
            this.context=itemView.getContext();
           this.textViewVTeamName = (TextView) itemView.findViewById(R.id.vteam_name);
           this.textViewVStartDate=(TextView)itemView.findViewById(R.id.vteam_start_date);
           this.textViewVStartLocation=(TextView)itemView.findViewById(R.id.vteam_start_location);
           this.textViewNumberOfMembers=(TextView)itemView.findViewById(R.id.vteam_number_of_members);
           this.imageViewVteamImage=(ImageView)itemView.findViewById(R.id.v_team_image);
           this.ViewComposition=(Button)itemView.findViewById(R.id.vteam_view_composition);

        }
    }

    public RvAzure_CustomAdapaterForRecylerViewVolunteer(ArrayList<RvAzure_DataModelForVolunteerService> data)
    {
        this.dataSet = data;

    }

    @Override
    public VolunteerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.volunteer_card_view, parent, false);

        view.setOnClickListener(RvAzure_Volunteer.myOnClickListener);


        VolunteerViewHolder volunteerViewHolder = new VolunteerViewHolder(view);
        return volunteerViewHolder;


    }

    @Override
    public void onBindViewHolder(final VolunteerViewHolder holder, final int listPosition)
    {





        TextView textViewVTeamName=holder.textViewVTeamName;
        TextView textViewVStartLocation=holder.textViewVStartLocation;
        TextView textViewVStartDate=holder.textViewVStartDate;
        TextView textViewNumberOfMembers=holder.textViewNumberOfMembers;
        ImageView imageViewVteamImage=holder.imageViewVteamImage;
        Button viewComposition=holder.ViewComposition;
        final Context context=holder.context;




        textViewVTeamName.setText(dataSet.get(listPosition).getTeamname());
        textViewVStartLocation.setText(dataSet.get(listPosition).getStartlocation());
        textViewVStartDate.setText(dataSet.get(listPosition).getStartdate());
        textViewNumberOfMembers.setText(dataSet.get(listPosition).getNumber_of_members());
        imageViewVteamImage.setImageResource(dataSet.get(listPosition).getTeamimage());


          viewComposition.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                Intent i=new Intent(context,RvAzure_VolunteerGroupProfile.class);

                context.startActivity(i);


            }
        });






    }

    @Override
    public int getItemCount()
    {

        return dataSet.size();
    }







}
