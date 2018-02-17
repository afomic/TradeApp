package com.afomic.tradeapp.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

import com.afomic.tradeapp.ChatActivity;
import com.afomic.tradeapp.MainActivity;
import com.afomic.tradeapp.R;
import com.afomic.tradeapp.data.Constants;
import com.afomic.tradeapp.data.PreferenceManager;
import com.afomic.tradeapp.model.Chat;
import com.afomic.tradeapp.model.Message;

/**
 *
 * Created by afomic on 2/14/18.
 */

public class NotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Message message=intent.getParcelableExtra(Constants.EXTRA_MESSAGE);
        Chat chat=intent.getParcelableExtra(Constants.EXTRA_CHAT);
        PreferenceManager preferenceManager=new PreferenceManager(context);
        String myUsername=preferenceManager.getUsername();
        String senderName= chat.getUserTwo().equals(myUsername)?chat.getUserOne():chat.getUserTwo();
        Intent sentIntent=new Intent(context, ChatActivity.class);
        sentIntent.putExtra(Constants.EXTRA_CHAT,chat);
        sentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi=PendingIntent.getActivity(context, 232,sentIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder=new Notification.Builder(context);
        builder.setContentTitle("You have new message from " +senderName);
        builder.setContentText(message.getMessage());
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setShowWhen(true);
        builder.setSmallIcon(R.drawable.ic_message);
        //make the device vibrate
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        if (Build.VERSION.SDK_INT >= 21){
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        }
        //make sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        builder.setContentIntent(pi);
        builder.setWhen(System.currentTimeMillis());
        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(context);
        managerCompat.notify(3432, builder.build());


    }
}
