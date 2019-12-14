package iot.hustler.io.easydictionary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

import iot.hustler.io.easydictionary.utils.ConstantsV1;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(intent.getAction())) {
            if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(ConstantsV1.Toggle_Enabled, false)) {
                SwiftService.enqueueWork(context, new Intent());
            } else {

            }

            Log.d(this.getClass().getSimpleName(), "BOOT RECIEVED");

        }
    }
}
