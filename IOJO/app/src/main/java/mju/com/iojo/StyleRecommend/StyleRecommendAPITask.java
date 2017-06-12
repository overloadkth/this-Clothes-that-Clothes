package mju.com.iojo.StyleRecommend;

import android.os.AsyncTask;

/**
 * Created by TaeHoon on 2017-06-03.
 */

public class StyleRecommendAPITask extends AsyncTask<Object, String, Style> {
    @Override
    protected Style doInBackground(Object... params) {
        StyleRecommendAPIClient client = new StyleRecommendAPIClient();

        String weather = (String)params[0];
        int max = (int)params[1];
       // int min = (int)params[2];
        // API 호출
        Style s = client.getStyle(weather, max);
        //System.out.println("Weather : "+w.getTemprature());

        // 작업 후 리
        return s;
    }
}
