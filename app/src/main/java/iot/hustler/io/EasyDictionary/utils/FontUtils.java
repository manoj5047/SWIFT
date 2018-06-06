package iot.hustler.io.EasyDictionary.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FontUtils {


    public static void setnormalFont(Activity activity, View textView) {
//        getCurrentLocale(activity).getLanguage().equalsIgnoreCase(Locale.ENGLISH.toString())
        if (true) {
            if (textView instanceof EditText) {
                ((EditText) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "av_n.otf"));

            } else {
                ((TextView) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "av_n.otf"));
            }
        } else {

        }

    }

    public static void setLightFont(Activity activity, View textView) {
        if (true) {
            if (textView instanceof EditText) {
                ((EditText) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "m_h.otf"));

            } else {
                ((TextView) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "m_h.otf"));

            }
        } else {

        }


    }

    public static void setHeavyFont(Activity activity, View textView) {
        if (true) {

            if (textView instanceof EditText) {
                ((EditText) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "av_h.ttf"));

            } else {
                ((TextView) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "av_h.ttf"));

            }
        } else {

        }


    }

    public static void setMoonFont(Activity activity, View textView) {
        if (true) {

            if (textView instanceof EditText) {
                ((EditText) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "av_h.ttf"));

            } else {
                ((TextView) textView).setTypeface(Typeface.createFromAsset(activity.getAssets(), "av_h.ttf"));

            }
        } else {

        }


    }


    public static void findtext_and_applyTypeface(Activity activity, ViewGroup viewGroup) {
        if (true) {

            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (viewGroup.getChildAt(i) instanceof TextView) {
                    setMoonFont(activity, (TextView) viewGroup.getChildAt(i));
                } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    findtext_and_applyTypeface(activity, (ViewGroup) viewGroup.getChildAt(i));
                } else if (viewGroup.getChildAt(i) instanceof EditText) {
                    setMoonFont(activity, (EditText) viewGroup.getChildAt(i));
                }
            }
        } else {

        }


    }

    /*METHOS TO APPLY FONT TO TABLAYOUT TEXT*/
    public static void findText_and_apply_typeface_in_TabLayout(Activity activity, ViewGroup tablayout) {

        if (true) {

            ViewGroup tabView_Group = (ViewGroup) tablayout.getChildAt(0);

            int tab_View_tabs_count = tabView_Group.getChildCount();

            for (int j = 0; j < tab_View_tabs_count; j++) {

                ViewGroup viewGroupTab = (ViewGroup) tabView_Group.getChildAt(j);

                int tabchildCount = viewGroupTab.getChildCount();

                for (int i = 0; i < tabchildCount; i++) {

                    View tabViewChild = viewGroupTab.getChildAt(i);

                    if (tabViewChild instanceof TextView) {

                        TextView textView = (TextView) tabViewChild;

                        textView.setAllCaps(false);

                        setLightFont(activity, textView);
                    }
                }
            }

        } else {

        }


    }

//    private static void setMenuFont(MenuItem menuItem, Activity activity) {
//        Typeface font = Typeface.createFromAsset(activity.getAssets(), "m_h.otf");
//        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
//        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        menuItem.setTitle(mNewTitle);
//
//    }
}
