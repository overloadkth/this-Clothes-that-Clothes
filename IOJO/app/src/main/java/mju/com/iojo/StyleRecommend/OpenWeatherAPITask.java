package mju.com.iojo.StyleRecommend;

import android.os.AsyncTask;

/**
 * Created by TaeHoon on 2017-06-02.
 */

public class OpenWeatherAPITask extends AsyncTask<Double, Void, Weather> {
    @Override
    protected Weather doInBackground(Double... params) {
        OpenWeatherAPIClient client = new OpenWeatherAPIClient();

        double lat = params[0];
        double lon = params[1];
        // API 호출
        Weather w = client.getWeather(lat,lon);
        //System.out.println("Weather : "+w.getTemprature());

        // 작업 후 리
        return w;
    }
}