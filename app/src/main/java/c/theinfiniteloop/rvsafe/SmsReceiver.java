package c.theinfiniteloop.rvsafe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver
{

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        String format = intent.getStringExtra("format");

        SmsMessage sms = msgs[0];
        String message = sms.getMessageBody();
        mListener.messageReceived(message);

    }

    public static void bindListener(SmsListener listener)
    {
        mListener = listener;
    }



}
