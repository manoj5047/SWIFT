package iot.hustler.io.EasyDictionary.utils;

import android.app.Activity;
import android.widget.Toast;

public class ToastSnackUtils {
    public Activity activity;

    public ToastSnackUtils(Activity activity) {
        this.activity = activity;
    }

    public void showToast(String msg, int gravity) {
        if (msg == null && msg.length() == 0)
            return;
        Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }
}
