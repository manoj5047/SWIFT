package iot.hustler.io.easydictionary.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import iot.hustler.io.easydictionary.JsonObjectRequestWithAuthHeader;
import iot.hustler.io.easydictionary.R;
import iot.hustler.io.easydictionary.app.MySingleton;
import iot.hustler.io.easydictionary.model.listeners.WebResponseListener;
import iot.hustler.io.easydictionary.model.pojo.ResponseWordsDefinitionsDTO;

public class RestUtility {

    Activity activity;

    public RestUtility(Activity activity) {
        this.activity = activity;
    }

    public void getMeaning(final Activity activity, String reequest, final WebResponseListener listener) {
        if (activity == null) {
            Log.e(activity.getPackageName(), "Context null in restutility");
            return;
        }
        String request = ConstantsV1.Dictionary_end_point.replace("query", reequest);
        JsonObjectRequestWithAuthHeader request1 = new JsonObjectRequestWithAuthHeader(Request.Method.GET, request, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("API CALL1", response.toString());
                        ResponseWordsDefinitionsDTO response1 = new Gson().fromJson(response.toString(), ResponseWordsDefinitionsDTO.class);
                        if (listener != null) {
                            listener.onSuccess(response1);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("API CALL1", error.toString());

                listener.onError(getRelevantVolleyErrorMessage(activity, error));
            }
        });

        MySingleton.addJsonObjRequest(activity, request1);
    }

    public String getRelevantVolleyErrorMessage(Context context, VolleyError volleyError) {
        try {
            volleyError.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        try {
            if (volleyError instanceof NoConnectionError) {
                return context.getString(R.string.no_internet);
            } else if (volleyError instanceof TimeoutError) {
                return context.getString(R.string.TIMEOUT_ERROR);

            } else if (volleyError instanceof ServerError) {
                return context.getString(R.string.SERVER_ERROR);
            } else if (volleyError instanceof NetworkError) {
                return context.getString(R.string.NETWORK_ERROR);
            } else if (volleyError instanceof ParseError) {
                return context.getString(R.string.PARSE_ERROR);
            }
            return null;
        } catch (Exception e) {
//            FirebaseCrash.log(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
