package com.example.weatherapiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<WeatherReportModel> weatherReportModel;
    Context context;

    public MyAdapter(List<WeatherReportModel> weatherReportModel, Context context) {
        this.weatherReportModel = weatherReportModel;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_weather_report, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println("position is here ------------" + weatherReportModel.get(position).getWeatherConditionPicturePicker().get(weatherReportModel.get(position).getWeather_state_name()));
        holder.weatherInfo.setText(weatherReportModel.get(position).toString());
        Glide.with(this.context).load(weatherReportModel.get(position).getWeatherConditionPicturePicker().get(weatherReportModel.get(position).getWeather_state_name())).into(holder.weatherConditionPicture);
    }

    @Override
    public int getItemCount() {
        return weatherReportModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView weatherConditionPicture;
        TextView weatherInfo;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherConditionPicture = itemView.findViewById(R.id.iv_weather_condition_picture);
            weatherInfo = itemView.findViewById(R.id.tv_weather_info);
        }
    }
}
