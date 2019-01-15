package c.theinfiniteloop.rvsafe;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class RvAzure_SmsReceiverService extends Service
{

    private SmsReceiver smsReceiver;
    private IntentFilter intentFilter;

    @Override
    public void onCreate()
    {
        super.onCreate();

        smsReceiver=new SmsReceiver();
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver,intentFilter);
    }

    @Override
    public  void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
