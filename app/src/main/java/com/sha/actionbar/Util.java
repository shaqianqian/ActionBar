package com.sha.actionbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Util {

    public final static int NET_NONE = 0;
    public final static int NET_WIFI = 1;
    public final static int NET_MOBILE = 2;
    public static String response = null;
    public static TodayWeather weather = new TodayWeather();

    //Analyse le connextion de internet
    public static int getNetState(Context context) {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null)
            return NET_NONE;
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_MOBILE)
            return NET_MOBILE;
        else if (type == ConnectivityManager.TYPE_WIFI)
            return NET_WIFI;
        return NET_MOBILE;
    }
    //Obtenir le meteo de temps reels par api qui est propose par openweathermap
    public static TodayWeather getWeatherDatafromNet(String addr) {
        final String address = addr;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(address);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        sb.append(str);
                    }
                    response = sb.toString();
                    System.out.println(response);
                    if (response != null) {
                        JSONObject jsonObject = new JSONObject(response);
                        System.out.println(jsonObject.getJSONObject("main"));
                        JSONObject main = jsonObject.getJSONObject("main");
                        String temp = main.getString("temp");
                        String humdity = main.getString("humidity");
                        String pressure = main.getString("pressure");

                        jsonObject = new JSONObject(response);
                        JSONObject wind = jsonObject.getJSONObject("wind");
                        String wind_speed = wind.getString("speed");

                        weather = new TodayWeather(temp, humdity, pressure, wind_speed);
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();

        return weather;
    }


  //Initialiser les donnees de tous les fragements
    public static View initDate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, String ville_nom) {

        View view;
        view = inflater.inflate(R.layout.tab_layout, container, false);

        String address = "http://api.openweathermap.org/data/2.5/weather?q=" + ville_nom + ",fr&appid=3c6857de34a5fdac43b1f0b07b32b35a";
        TodayWeather weather = Util.getWeatherDatafromNet(address);


        TextView ville = (TextView) view.findViewById(R.id.ville);
        ville.setText(ville_nom);

        TextView temp = (TextView) view.findViewById(R.id.temp);
        temp.setText("");
        temp.setText(weather.getTemperature());


        TextView humidity = (TextView) view.findViewById(R.id.humidity);
        humidity.setText("");
        humidity.setText(weather.getHumidity());


        TextView pression = (TextView) view.findViewById(R.id.pression);
        pression.setText("");
        pression.setText(weather.getPressure());

        TextView wind = (TextView) view.findViewById(R.id.wind);
        wind.setText("");
        wind.setText(weather.getWind());

        return view;
    }


}
