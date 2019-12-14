package iot.hustler.io.easydictionary;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import iot.hustler.io.easydictionary.ui.SwiftSearchActivity;

public class SwiftService extends JobIntentService {
    public static final int JOB_ID = 1000;
    private static final String CHANNEL_ID = "SWIFT";
    private static final int NOTIFY_ID = 5004;
    NotificationManager notificationManager;
    NotificationCompat.Builder builder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SwiftService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        buildNotification();
        Log.d(this.getClass().getSimpleName(), "ON HANDLE WORK");
        toast("ON HANDLE WORK");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toast("All work complete");
    }

    private void buildNotification() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
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
        builder.setAutoCancel(false);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.click_to_search)));
        builder.setBadgeIconType(R.drawable.ic_action_name);
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        assert notificationManager != null;
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SwiftService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
