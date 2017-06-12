package mju.com.iojo.StyleRecommend;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.R.attr.description;

import mju.com.iojo.AddClothes.AddClothesActivity;
import mju.com.iojo.ClosetAdmin.ClosetFragment;
import mju.com.iojo.ClosetAdmin.CustomListAdapter;
import mju.com.iojo.ClosetAdmin.StyleImage;
import mju.com.iojo.R;


public class StyleRecommendActivity extends AppCompatActivity {
    private TextView lowTemp;
    private TextView highTemp;
    private TextView Weather;
    private TextView City;
    private ImageView weatherIcon;
    private ImageView styleTop;
    private ImageView styleBottom;

    private String url;
    private double latitude;
    private double longitude;
    private int minTemp; // category와 비교하기 위해 온도 값을 int로 받아온다.
    private int maxTemp; // category와 비교하기 위해 온도 값을 int로 받아온다.
    private String weather;
    private String topUri;
    private String bottomUri;

    private Button YesButton;
    private Button NoButton;

    private GPSInfo gps;

    private static final String TAG_JSON="result";
    private static final String TAG_TOP_URI ="turi";
    private static final String TAG_BOTTOM_URI ="buri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_recommend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        url = null;
        lowTemp = (TextView)findViewById(R.id.lowTemp);
        highTemp = (TextView)findViewById(R.id.HighTemp);
        City = (TextView)findViewById(R.id.city);
        weatherIcon = (ImageView)findViewById(R.id.weather_icon);

        styleTop = (ImageView)findViewById(R.id.TopImg);
        styleBottom = (ImageView)findViewById(R.id.BottomImg);

        YesButton = (Button)findViewById(R.id.AcceptButton1);
        NoButton = (Button)findViewById(R.id.AcceptButton2);

       PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(StyleRecommendActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(StyleRecommendActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        new TedPermission(StyleRecommendActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();

        gps = new GPSInfo(StyleRecommendActivity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            this.getWeather();
//            System.out.println(weather + " " + maxTemp + " " + minTemp);
            getStyleRecommend(weather, maxTemp);
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }

        YesButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v){
                        DialogInterface.OnClickListener insertListener = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                insertBookmark(topUri, bottomUri);
                            }
                        };
                        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.dismiss();
                            }
                        };

                        new AlertDialog.Builder(StyleRecommendActivity.this)
                                .setTitle("즐겨찾기에 추가하시겠습니까?")
                                .setPositiveButton("즐겨찾기 등록", insertListener)
                                .setNegativeButton("취소", cancelListener)
                                .show();
                    }
                }
        ); // Yes버튼 클릭 시 즐겨 찾기 등록 여부 확인

        NoButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                         getStyleRecommend(weather, maxTemp);
                    }
                }
        ); // No버튼 클릭 시 다른 스타일 추천

//       플로팅 액션 버튼 비활성화
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    public void getWeather() {
        // 날씨를 읽어오는 API 호출
        OpenWeatherAPITask WeatherTask= new OpenWeatherAPITask();
        try {
            Weather w = WeatherTask.execute(latitude,longitude).get();

            String minTemperature = String.valueOf(w.getMin_Temperature());
            String maxTemperature = String.valueOf(w.getMax_Temperature());
            minTemp = w.getMin_Temperature();
            maxTemp = w.getMax_Temperature();
            weather = w.getWeather();
            String city = w.getCity();
            String icon = w.getIcon();

            lowTemp.setText(minTemperature);
            highTemp.setText(maxTemperature);
            City.setText(city);
            Picasso.with(this)
                    .load("http://openweathermap.org/img/w/" + icon + ".png")
                    .fit()
                    .into(weatherIcon);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void getStyleRecommend(String weather, int max){
//        if(min >= 30) minTemp = 30;
//        //else if ( min >= 25) minTemp = 25;
//        else if (min >= 20) minTemp = 20;
//        //else if (min >= 15) minTemp = 15;
//        else if (min >= 10) minTemp = 10;
//        //else if (min >= 5) minTemp = 5;
//        else if (min >= 0) minTemp = 0;
//        //else if (min >= -5) minTemp = -5;
//        else if (min >= -10) minTemp = -10;

//        if(max >= 30) maxTemp = 30;
//        else if (max >= 25) maxTemp = 25;
//        else if (max >= 20) maxTemp = 20;
//        else if (max >= 15) maxTemp = 15;
//        else if (max >= 10) maxTemp = 10;
//        else if (max >= 5) maxTemp = 5;
//        else if (max >= 0) maxTemp = 0;
//        else if (max >= -5) maxTemp = -5;
//        else if (max >= -10) maxTemp = -10;

        maxTemp = 25;

        if(weather.equals("Clear")){
            weather = "sunny";
        } else if(weather.equals("Clouds")){
            weather = "cloud";
        } else if(weather.equals("Snow")){
            weather = "snow";
        } else if(weather.equals("Rain") || weather.equals("Thunderstorm") || weather.equals("Drizzle")){
            weather = "rain";
        }

//        Toast.makeText(StyleRecommendActivity.this, weather + " " + maxTemp + " " + minTemp, Toast.LENGTH_SHORT).show();
//        System.out.println(weather + " " + maxTemp + " " + minTemp);
        // 스타일을 읽어오는 API 호출
        StyleRecommendAPITask StyleTask= new StyleRecommendAPITask();
        try {
            Style s = StyleTask.execute(weather, maxTemp).get();

            topUri = s.getTopStyle();
            bottomUri = s.getBottomStyle();
            // System.out.println("topUri : " + topUri + "\n" + "bottomUri : " + bottomUri);
            Picasso.with(this)
                    .load(topUri)
                    .fit()
                    .into(styleTop);
            Picasso.with(this)
                    .load(bottomUri)
                    .fit()
                    .into(styleBottom);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void insertBookmark(String top, String bottom){
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(StyleRecommendActivity.this,
                        "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                try{
                    String top = (String)params[0];
                    String bottom = (String)params[1];
                    String link = "http://192.168.42.65/Insert/InsertBookmark.php";

                    String data  = URLEncoder.encode("top", "UTF-8") + "="
                            + URLEncoder.encode(top, "UTF-8");
                    data += "&" + URLEncoder.encode("bottom", "UTF-8") + "="
                            + URLEncoder.encode(bottom, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(top, bottom);
    }
}
