package iot.hustler.io.easydictionary.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


/**
 * Created by anvaya6 on 22/8/16.
 */
public class MySingleton {
    final static String TAG = MySingleton.class.getSimpleName();
    private static final int DEFAULT_TIMEOUT_MS = 5000;
//    JsonObjectRequest req = new JsonObjectRequest("URL", new JSONObject("params"),
//            new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject feedbacksResponse) {
//                    // handle feedbacksResponse
//                }
//            }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            // handle error
//        }
//    }) {
//
//        @Override
//        public Map<String, String> getHeaders() throws AuthFailureError {
//            HashMap<String, String> headers = new HashMap<String, String>();
//            headers.put("CUSTOM_HEADER", "Yahoo");
//            headers.put("ANOTHER_CUSTOM_HEADER", "Google");
//            return headers;
//        }
//    };

    private static MySingleton mInstance;
    private static int VOLLEY_MAX_RETRIES = 3;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }


    public static void addJsonObjRequest(Context context, JsonObjectRequest jsObjRequest) {

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_TIMEOUT_MS * 4,
                VOLLEY_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Log.e(TAG, jsObjRequest.getUrl());


        MySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(jsObjRequest);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
