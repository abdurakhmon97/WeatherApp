package com.example.weatherapiapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {
    public static final String QUERY_BY_CITY_NAME = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";
    String cityID;
    Context context;
    String theCityID;

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

    public interface ForeCastByIDResponse {
        void onError(String message);

        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void getWeatherByID(String cityID, final ForeCastByIDResponse foreCastByIDResponse) {
        final List<WeatherReportModel> report = new ArrayList<>();
        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    JSONArray consolidated_weather_list = response.getJSONArray("consolidated_weather");

                    for(int i = 0; i < consolidated_weather_list.length(); i++) {
                        WeatherReportModel one_day = new WeatherReportModel();

                        JSONObject first_object_from_api = consolidated_weather_list.getJSONObject(i);
                        one_day.setId(first_object_from_api.getInt("id"));
                        one_day.setWeather_state_name(first_object_from_api.getString("weather_state_name"));
                        one_day.setWeather_state_abbr(first_object_from_api.getString("weather_state_abbr"));
                        one_day.setWind_direction_compass(first_object_from_api.getString("wind_direction_compass"));
                        one_day.setCreated(first_object_from_api.getString("created"));
                        one_day.setApplicable_date(first_object_from_api.getString("applicable_date"));
                        one_day.setMin_temp(first_object_from_api.getLong("min_temp"));
                        one_day.setMax_temp(first_object_from_api.getLong("max_temp"));
                        one_day.setThe_temp(first_object_from_api.getLong("the_temp"));
                        one_day.setWind_speed(first_object_from_api.getLong("wind_speed"));
                        one_day.setWind_direction(first_object_from_api.getLong("wind_direction"));
                        one_day.setAir_pressure(first_object_from_api.getInt("air_pressure"));
                        one_day.setHumidity(first_object_from_api.getInt("humidity"));
                        one_day.setVisibility(first_object_from_api.getLong("visibility"));
                        one_day.setPredictability(first_object_from_api.getInt("predictability"));

                        report.add(one_day);
                    }

                    foreCastByIDResponse.onResponse(report);


                } catch (JSONException e) {
                    e.printStackTrace();
                    foreCastByIDResponse.onError("Did not work as expected");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface GetWeatherByCityNameResponse {
        void onError(String message);
        void onResponse (List<WeatherReportModel> weatherReportModels);
    }

    public void getTheWeatherByName (String cityName, final GetWeatherByCityNameResponse getWeatherByCityNameResponse) {
        getCityID(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(context, "The city ID not found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String cityID) {
                getWeatherByID(cityID, new ForeCastByIDResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(context, "The name is not found", Toast.LENGTH_SHORT).show();
                        getWeatherByCityNameResponse.onError("The name is not found");
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        getWeatherByCityNameResponse.onResponse(weatherReportModels);


                    }
                });


            }
        });
        //MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
