package mju.com.iojo.StyleRecommend;

/**
 * Created by TaeHoon on 2017-06-02.
 */

public class Weather {
    double lat;
    double lon;
    int min_temperature;
    int max_temperature;
    String weather;
    String city;
    String icon;

    public void setLat(double t){this.lat = t;}
    public void setLon(double t){this.lon = t;}
    public void setMin_Temperature(int t){ this.min_temperature = t;}
    public void setMax_Temperature(int t){ this.max_temperature = t;}
    public void setWeather(String weather){ this.weather = weather;}
    public void setCity(String city){this.city = city;}
    public void setIcon(String icon){this.icon = icon;}

    public int getMin_Temperature() { return min_temperature;}
    public int getMax_Temperature() { return max_temperature;}
    public String getWeather() { return weather; }
    public String getCity() { return city;}
    public String getIcon() { return icon;}
}