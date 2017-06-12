package mju.com.iojo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mju.com.iojo.StyleRecommend.StyleRecommendActivity;
import mju.com.iojo.ClosetAdmin.ClosetAdminActivity;
import mju.com.iojo.AddClothes.AddClothesActivity;
import mju.com.iojo.Setting.SettingActivity;

public class MainActivity extends AppCompatActivity {
    private Intent ClosetAdmin;
    private Intent StyleRecommend;
    private Intent AddClothes;
    private Intent Setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StyleRecommend = new Intent(getApplicationContext(), StyleRecommendActivity.class);
        ClosetAdmin = new Intent(getApplicationContext(), ClosetAdminActivity.class);
        AddClothes = new Intent(getApplicationContext(), AddClothesActivity.class);
        Setting = new Intent(getApplicationContext(), SettingActivity.class);

        Button styleRecommend = (Button) findViewById(R.id.StyleRecommendButton);
        styleRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(StyleRecommend);
            }
        });

        Button closetAdmin = (Button) findViewById(R.id.ClosetAdminButton);
        closetAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(ClosetAdmin);
            }
        });

        Button addClothes = (Button) findViewById(R.id.AddClothesButton);
        addClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(AddClothes);
            }
        });

        Button setting = (Button) findViewById(R.id.SettingButton);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Setting);
            }
        });
    }
}
