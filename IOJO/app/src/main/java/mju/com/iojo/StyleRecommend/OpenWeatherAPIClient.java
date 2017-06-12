package mju.com.iojo.StyleRecommend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TaeHoon on 2017-06-02.
 */

public class OpenWeatherAPIClient {
    final static String openWeatherURL = "http://api.openweathermap.org/data/2.5/forecast/daily";
    Weather w = new Weather();
    public Weather getWeather(double lat, double lon){
        String urlString = openWeatherURL + "?lat="+lat+"&lon="+lon + "&units=metric&appid=514eb35a7ecc1a42ab06a447945740a7";
        System.out.println("위도 : " + lat + "\n" + "경도 : " + lon);
        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
//            urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));
            // parse JSON
            w = parseJSON(json, "temp");
            w = parseJSON(json, "weather");
            w = parseJSON(json, "city");
            w = parseJSON(json, "icon");
            w.setLon(lon);
            w.setLat(lat);
        }catch(MalformedURLException e){
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;
        }catch(JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        }catch(IOException e){
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }
        // set Weather Object
        return w;
    }

    private Weather parseJSON(JSONObject json, String Type) throws JSONException {
        JSONArray ListJson = (JSONArray) json.getJSONArray("list");
        JSONObject dayForecast = (JSONObject) ListJson.getJSONObject(1);
        System.out.println("dayForecast : " + dayForecast);
        if(Type.equals("temp")){
            JSONObject temperatureObject = dayForecast.getJSONObject("temp");
            w.setMin_Temperature(temperatureObject.getInt("min"));
            w.setMax_Temperature(temperatureObject.getInt("max"));
        } else if (Type.equals("weather")){
            JSONObject weatherJson = dayForecast.getJSONArray("weather").getJSONObject(0);
            w.setWeather(weatherJson.getString("main"));
        } else if (Type.equals("city")){
            w.setCity(json.getJSONObject("city").getString("name"));
        } else if (Type.equals("icon")){
            JSONObject weatherJson = dayForecast.getJSONArray("weather").getJSONObject(0);
            w.setIcon(weatherJson.getString("icon"));
        }
        // w.setWeather(json2.getString("name"));
        //w.setCloudy();
        return w;
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}