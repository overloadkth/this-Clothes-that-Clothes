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
import java.util.Random;

/**
 * Created by TaeHoon on 2017-06-02.
 */

public class StyleRecommendAPIClient {
    final static String StyleRecommendURL = "http://192.168.42.65/Recommend/StyleRecommend.php?";
    Style s = new Style();
    final static String TAG_JSON = "result";
    final static String TAG_TOP = "top";
    final static String TAG_BOTTOM = "bottom";

    public Style getStyle(String weather, int max){
        String urlString = StyleRecommendURL + "weather="+weather+"&high_temp="+max;
        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
//            urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

//            System.out.println(url);
            // parse JSON
            s = parseJSON(json, TAG_TOP);
            s = parseJSON(json, TAG_BOTTOM);
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
        return s;
    }

    private Style parseJSON(JSONObject json, String Type) throws JSONException {
        JSONArray ListJson = (JSONArray) json.getJSONArray(TAG_JSON);
        JSONObject TopJson = (JSONObject) ListJson.getJSONObject(0);
        JSONObject BottomJson = (JSONObject) ListJson.getJSONObject(1);
//        System.out.println("TopJSon : " + TopJson.toString());
//        System.out.println("BottomJSon : " + BottomJson.toString());
        if(Type.equals(TAG_TOP)) {
            s.setTopStyle(TopJson.getString(TAG_TOP));
//            System.out.println("TopStyle : " + s.getTopStyle());
        }
        else if (Type.equals(TAG_BOTTOM)){
            s.setBottomStyle(BottomJson.getString(TAG_BOTTOM));
//            System.out.println("BottomStyle : " + s.getBottomStyle());
        }
        return s;
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
