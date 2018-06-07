package iot.hustler.io.EasyDictionary.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import iot.hustler.io.EasyDictionary.R;
import iot.hustler.io.EasyDictionary.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private static final String CHANNEL_ID = "SWIFT";
    private static final int NOTIFY_ID = 5004;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildNotification();
    }

    private void buildNotification() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        //SETS NOTIFICATION CHANNEL FOR ABOVE ANDROID O (26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            setbuilder();
        } else {
            builder = new NotificationCompat.Builder(this);
            setbuilder();
        }
    }

    public void setbuilder() {
        Intent resultIntent = new Intent(this, SwiftSearchActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(getString(R.string.app_name));
        builder.setSmallIcon(R.drawable.ic_action_name);
        builder.setContentText(getString(R.string.click_to_search));
        builder.setSubText(getString(R.string.tap_to_open));
        builder.setAutoCancel(false);
        builder.setBadgeIconType(R.drawable.ic_action_name);
        builder.setOngoing(true);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.click_to_search)));
        builder.setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        assert notificationManager != null;
        notificationManager.notify(NOTIFY_ID, builder.build());

    }

}
