package c.theinfiniteloop.rvsafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RvAzure_login extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_azure_login);

       startActivity(new Intent(RvAzure_login.this,RvAzure_Disaster_cards.class));


    }
}
