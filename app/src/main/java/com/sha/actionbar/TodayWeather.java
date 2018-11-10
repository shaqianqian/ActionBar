package com.sha.actionbar;

import java.text.DecimalFormat;

/**
 * L'object de meteo
 */
public class TodayWeather {
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public TodayWeather(String temperature, String humidity, String pressure, String wind) {
        DecimalFormat df = new DecimalFormat("0.00");
        this.temperature = "Temperature: "+String.valueOf(df.format(Double.parseDouble(temperature)-273.15)+"°C");
        this.humidity = "Humidite: "+humidity+"%";
        this.pressure = "Pression:" +pressure+"hpa";
        this.wind = "Niveau de vent: "+wind;
    }

    public TodayWeather() {

    }

    public void setPressure(String pressure) {
        this.pressure = pressure;

    }

    @Override
    public String toString() {
        return "TodayWeather{" +
                "temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    private String temperature;
    private String humidity;
    private String pressure;
    private String wind;


}
