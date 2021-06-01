package com.example.weatherapiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btn_getCityID, btn_getWeatherByCityID, btn_getWeatherByCityName;
    EditText et_dateInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_getCityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByCityID = findViewById(R.id.btn_getWeatherByCityID);
        btn_getWeatherByCityName = findViewById(R.id.btn_getWeatherByCityName);
        et_dateInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReports);
        final WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        btn_getCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityID(et_dateInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toast.makeText(MainActivity.this, "Returned ID is " + cityID, Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        btn_getWeatherByCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_getWeatherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You typed " + et_dateInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}