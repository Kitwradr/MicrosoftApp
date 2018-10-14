package c.theinfiniteloop.rvsafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RvAzure_login extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure_login);


        Button signin=findViewById(R.id.signin);
        TextView textView =findViewById(R.id.forgot);





        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(RvAzure_login.this,RvAzure_Disaster_cards.class));


            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RvAzure_login.this,Rescuer_View.class));

            }
        });










    }

}
