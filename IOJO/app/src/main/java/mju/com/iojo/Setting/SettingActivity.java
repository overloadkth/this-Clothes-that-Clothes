package mju.com.iojo.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import mju.com.iojo.R;

public class SettingActivity extends AppCompatActivity {
    private Intent Noti;
    private Intent Bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Noti = new Intent(getApplicationContext(), NotiActivity.class);
        Bookmark = new Intent(getApplicationContext(), BookmarkActivity.class);

        Button NotiButton = (Button) findViewById(R.id.NotiButton);
        NotiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Noti);
            }
        });

        Button BookButton = (Button) findViewById(R.id.BookmarkButton);
        BookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Bookmark);
            }
        });
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
}
