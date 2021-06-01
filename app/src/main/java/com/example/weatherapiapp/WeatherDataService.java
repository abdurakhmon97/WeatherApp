package com.example.weatherapiapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {
    public static final String QUERY_BY_CITY_NAME = "https://www.metaweather.com/api/location/search/?query=";
    String cityID;

    Context context;

    public WeatherDataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(String cityID);
    }

    public void getCityID(String cityName, final VolleyResponseListener volleyResponseListener) {


        String url = QUERY_BY_CITY_NAME + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(context, cityID, Toast.LENGTH_SHORT).show();
                volleyResponseListener.onResponse(cityID);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "something went wrong!", Toast.LENGTH_SHORT).show();
                volleyResponseListener.onError("Something is wrong!");

            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
