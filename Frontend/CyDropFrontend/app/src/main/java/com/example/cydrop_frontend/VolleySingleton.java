package com.example.cydrop_frontend;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * VolleySingleton class to manage the Volley request queue and image caching.
 * Provides a centralized way to make HTTP requests and load images within the application.
 */
public class VolleySingleton {

    /**
     * Singleton instance of VolleySingleton.
     */
    private static VolleySingleton instance;

    /**
     * RequestQueue for managing network requests.
     */
    private RequestQueue requestQueue;

    /**
     * ImageLoader for managing image caching.
     */
    private ImageLoader imageLoader;

    /**
     * Application context.
     */
    private static Context ctx;

    /**
     * URL of the backend server.
     */
    public static String backendURL = "http://coms-3090-038.class.las.iastate.edu:8080";

    /**
     * Logged-in user's email.
     */
    public static String email = "";

    /**
     * User ID of the logged-in user.
     */
    public static String userId = "-1";

    /**
     * User type of the logged-in user.
     */
    public static String userType = "none";

    // TODO: I NEED TO REMOVE THIS AND REPLACE ALL INSTANCES WITH VETIDS
    /**
     * Temporary vet ID to be removed and replaced with actual vet IDs as necessary.
     */
    public static final String vetIdTEMP = "1";

    public static Map<String, Integer> userPetsToId = new HashMap<>() {
    };

    public static Map<String, Integer> medicationToId = new HashMap<>();

    /**
     * Private constructor to initialize the request queue and image loader.
     *
     * @param context The application context.
     */
    private VolleySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
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


    /**
     * Provides a synchronized instance of VolleySingleton to ensure a single instance is used.
     *
     * @param context The application context.
     * @return The single instance of VolleySingleton.
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    /**
     * Provides the RequestQueue, creating it if it does not already exist.
     *
     * @return The RequestQueue for making network requests.
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Adds a request to the Volley request queue.
     *
     * @param req The request to be added.
     * @param <T> The type of the request response.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Provides the ImageLoader for loading and caching images.
     *
     * @return The ImageLoader used for image caching.
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
