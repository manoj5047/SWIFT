package iot.hustler.io.EasyDictionary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(intent.getAction())) {
            SwiftService.enqueueWork(context, new Intent());
            Log.d(this.getClass().getSimpleName(), "BOOT RECIEVED");

        }
    }
}
