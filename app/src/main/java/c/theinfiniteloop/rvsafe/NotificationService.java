package c.theinfiniteloop.rvsafe;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class NotificationService extends Service
{
    BroadcastReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onDestroy()
    {
        unregisterReceiver(mReceiver);
        Log.i("onDestroy Reciever", "Called");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        final BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
        return super.onStartCommand(intent, flags, startId);

    }




    public class LocalBinder extends Binder
    {
        NotificationService getService()
        {
            return NotificationService.this;
        }

    }


}
