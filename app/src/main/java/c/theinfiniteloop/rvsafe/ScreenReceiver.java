package c.theinfiniteloop.rvsafe;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver
{
    public static boolean wasScreenOn = true;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            // do whatever you need to do here
            wasScreenOn = false;

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context,"M_CH_ID")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("RVSAFE MEDICAL ID")
                            .setContentText("123456789D")
                            .setStyle(new NotificationCompat.InboxStyle()
                                    .addLine("BLOOD GROUP A+")
                                    .addLine("AGE 21"));
            NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1,mBuilder.build());

            //Log.e("LOB","wasScreenOn"+wasScreenOn);
            Log.e("Screen ","shutdown now");
        }
   /*This can be used if needed*/
   /*     else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            // and do whatever you need to do here
/*           wasScreenOn = true;
            Log.e("Screen ","awaked now");
            Intent i = new Intent(context, MainActivity.class);  //MyActivity can be anything which you want to start on bootup...
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } */
/*        else if(intent.getAction().equals(Intent.ACTION_USER_PRESENT))
        {
            Log.e("LOB","userpresent");
            //  Log.e("LOB","wasScreenOn"+wasScreenOn);
        }
   */
    }


}
