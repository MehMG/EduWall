package com.eduwall.Session;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.eduwall.Authentication.Activity.AuthenticationActivity;
import com.eduwall.R;
import com.eduwall.Session.Activity.BoxActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    SharePreference preference;

    private static final String TAG = "MyFirebaseMsgService";

    public MyFirebaseMessagingService() {
        Log.e("TAG", "ONReceived..");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getData());

        preference = new SharePreference(this);

        if (!preference.getActive()) {
            Log.e("Active1", preference.getActive().toString());
            Intent intent = new Intent(getApplicationContext(), BoxActivity.class);
            intent.putExtra("Message", remoteMessage.getData().get("message"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            sendNotification(remoteMessage.getNotification().getBody());
        } else {
            Log.e("Active2", preference.getActive().toString());
            Log.e("Result", "Else");
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String messageBody) {

        preference = new SharePreference(this);

        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("EduWall")
                .setContentText(messageBody)
                .setAutoCancel(true);

        if (preference.getNotification().equalsIgnoreCase("Tone")) {

            notificationBuilder.setSound(defaultSoundUri);
            Log.e("TAG", "Tone");

        } else if (preference.getNotification().equalsIgnoreCase("Vibrate")) {

            notificationBuilder.setVibrate(new long[]{500, 500, 500, 500});
            Log.e("TAG", "Vibrate");

        } else if (preference.getNotification().equalsIgnoreCase("PopUp")) {

            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setVibrate(new long[]{500, 500, 500, 500});
            Log.e("TAG", "PopUp");

        } else if (preference.getNotification().equalsIgnoreCase("Mute")) {

            notificationBuilder.setDefaults(0);
            Log.e("TAG", "Mute");

        } else {
            notificationBuilder.setSound(defaultSoundUri);
            notificationBuilder.setVibrate(new long[]{500, 500, 500, 500});
            Log.e("TAG", "DEFAULT");
        }

        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}