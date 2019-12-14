package iot.hustler.io.easydictionary.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import iot.hustler.io.easydictionary.R;
import iot.hustler.io.easydictionary.base.BaseActivity;
import iot.hustler.io.easydictionary.utils.ConstantsV1;
import iot.hustler.io.easydictionary.utils.FontUtils;

public class MainActivity extends BaseActivity {

    private static final String CHANNEL_ID = "SWIFT";
    private static final int NOTIFY_ID = 5004;
    NotificationManager notificationManager_for_delete;
    NotificationCompat.Builder builder;
    RelativeLayout root;
    Switch aSwitch;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    NotificationManager notificationManager;
    AdView adView;
    TextView info_text;
    ImageView write, rate, quotzy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        buildNotification();
        MobileAds.initialize(this, getString(R.string.ADMOBID));
        root = findViewById(R.id.root);
        aSwitch = findViewById(R.id.enable_button);
        info_text = findViewById(R.id.instructions);
        write = findViewById(R.id.write);
        rate = findViewById(R.id.rate);
        quotzy = findViewById(R.id.quotzy);

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("A5B1E467FD401973F9F69AD2CCC13C30").build();
        adView.loadAd(adRequest);

        FontUtils.findtext_and_applyTypeface(MainActivity.this, root);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = sharedPreferences.edit();

        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (sharedPreferences.getBoolean(ConstantsV1.Toggle_Enabled, false)) {
            buildNotification();
            aSwitch.setChecked(true);
            info_text.setText(getString(R.string.Thanks));
        } else {
            aSwitch.setChecked(false);
            info_text.setText(getString(R.string.instructions));
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean(ConstantsV1.Toggle_Enabled, true);
                    buildNotification();
                    if (editor.commit()) {
                        info_text.setVisibility(View.GONE);
                        info_text.setText(getString(R.string.Thanks));
                        info_text.setVisibility(View.VISIBLE);

                    }

                } else {
                    info_text.setText(getString(R.string.instructions));
                    notificationManager.cancel(NOTIFY_ID);
                    editor.putBoolean(ConstantsV1.Toggle_Enabled, false);
                    if (editor.commit()) {
                        info_text.setVisibility(View.GONE);
                        info_text.setText(getString(R.string.instructions));
                        info_text.setVisibility(View.VISIBLE);

                    }

                }
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "quotzyapp@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From Swift User");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity  object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        quotzy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = "com.hustler.quote"; // getPackageName() from Context or Activity  object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });
    }

    private void buildNotification() {

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
