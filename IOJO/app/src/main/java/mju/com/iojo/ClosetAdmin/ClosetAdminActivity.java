package mju.com.iojo.ClosetAdmin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import mju.com.iojo.R;

public class ClosetAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String title = "옷장";
        int id = item.getItemId();

        if (id == R.id.coat) {
            fragment = new ClosetFragment(this.getApplicationContext(), "outer", "coat");
            title = "코트";
        } else if (id == R.id.jumper){
            fragment = new ClosetFragment(this.getApplicationContext(), "outer", "jumper");
            title = "점퍼";
        } else if (id == R.id.jacket) {
            fragment = new ClosetFragment(this.getApplicationContext(), "outer", "jacket");
            title = "자켓";
        } else if (id == R.id.cardigan) {
            fragment = new ClosetFragment(this.getApplicationContext(), "outer", "cardigan");
            title = "가디건";
        } else if (id == R.id.padding) {
            fragment = new ClosetFragment(this.getApplicationContext(), "outer", "padding");
            title = "패딩";
        } else if (id == R.id.shirts) {
            fragment = new ClosetFragment(this.getApplicationContext(), "top", "shirts");
            title = "셔츠";
        } else if (id == R.id.knit) {
            fragment = new ClosetFragment(this.getApplicationContext(), "top", "knit");
            title = "니트";
        } else if (id == R.id.longsleeve) {
            fragment = new ClosetFragment(this.getApplicationContext(), "top", "longsleeve");
            title = "긴팔";
        } else if (id == R.id.shortsleeve) {
            fragment = new ClosetFragment(this.getApplicationContext(), "top", "shortsleeve");
            title = "반팔";
        } else if (id == R.id.training) {
            fragment = new ClosetFragment(this.getApplicationContext(), "top", "training");
            title = "트레이닝 탑";
        } else if (id == R.id.jeans) {
            fragment = new ClosetFragment(this.getApplicationContext(), "bottom", "jeans");
            title = "청바지";
        } else if (id == R.id.shorts) {
            fragment = new ClosetFragment(this.getApplicationContext(), "bottom", "shorts");
            title = "반바지";
        } else if (id == R.id.chino) {
            fragment = new ClosetFragment(this.getApplicationContext(), "bottom", "chino");
            title = "면바지";
        } else if (id == R.id.slacks) {
            fragment = new ClosetFragment(this.getApplicationContext(), "bottom", "slacks");
            title = "슬랙스";
        } else if (id == R.id.training_pants) {
            fragment = new ClosetFragment(this.getApplicationContext(), "bottom", "training");
            title = "트레이닝 팬츠";
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, fragment);
            ft.commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
