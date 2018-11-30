package c.theinfiniteloop.rvsafe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvAzure_CustomAdapterForRecyclerView extends RecyclerView.Adapter<RvAzure_CustomAdapterForRecyclerView.MyViewHolder> {
    private ArrayList<RvAzure_DataModelForCards> dataSet;

public static class MyViewHolder extends RecyclerView.ViewHolder
{

    TextView textViewName;
    TextView textViewtype;
    ImageView imageViewIcon;
    Button stuck_in_this_disaster;
    Button want_to_help_out;
    final Context context;
    public MyViewHolder(View itemView)
    {
        super(itemView);
        this.context=itemView.getContext();
        this.textViewName = (TextView) itemView.findViewById(R.id.disastername);
        this.textViewtype = (TextView) itemView.findViewById(R.id.disaster_type);
        this.imageViewIcon = (ImageView) itemView.findViewById(R.id.disaster_card1_image);
        this.stuck_in_this_disaster=(Button)itemView.findViewById(R.id.stuck_in_this_disaster);
        this.want_to_help_out=(Button)itemView.findViewById(R.id.want_to_help_out);

    }
}

    public RvAzure_CustomAdapterForRecyclerView(ArrayList<RvAzure_DataModelForCards> data)
    {
        this.dataSet = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.disaster_card_view, parent, false);

        view.setOnClickListener(RvAzure_Disaster_cards.myOnClickListener);








        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition)
    {
        int disaster_id;
        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewtype;
        ImageView imageView = holder.imageViewIcon;
        Button stuck_in_this_disaster=holder.stuck_in_this_disaster;
        Button want_to_help_out=holder.want_to_help_out;
        final Context context=holder.context;
        disaster_id=dataSet.get(listPosition).getId();
        textViewName.setText(dataSet.get(listPosition).getName());
        textViewVersion.setText(dataSet.get(listPosition).getVersion());
        Picasso.get().load(dataSet.get(listPosition).getImage()).placeholder(R.drawable.keralafloodsimage1).error(R.drawable.tamilnadutsunami).into(imageView);


//        imageView.setImageResource(dataSet.get(listPosition).getImage());



        if(disaster_id==-1)
        {
            stuck_in_this_disaster.setText("INSTA HELP");

            stuck_in_this_disaster.setTextColor(Color.WHITE);

            stuck_in_this_disaster.setBackgroundColor(Color.RED);
         //   stuck_in_this_disaster.setBackgroundResource(R.drawable.oval_button_light_red);



            want_to_help_out.setVisibility(View.GONE);
        }







        stuck_in_this_disaster.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {



                Intent i=new Intent(context,RvAzure_StuckInThisDisaster.class);


                i.putExtra("disaster_id",disaster_id);

                context.startActivity(i);

            }
        });


        want_to_help_out.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {



                Intent i=new Intent(context,RvAzure_WantToHelpOut.class);

                i.putExtra("disaster_id",disaster_id);

                context.startActivity(i);


            }
        });




    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
