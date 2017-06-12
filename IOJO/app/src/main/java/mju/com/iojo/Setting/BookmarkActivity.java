package mju.com.iojo.Setting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import mju.com.iojo.ClosetAdmin.ClosetFragment;
import mju.com.iojo.ClosetAdmin.CustomListAdapter;
import mju.com.iojo.ClosetAdmin.StyleImage;
import mju.com.iojo.R;

public class BookmarkActivity extends AppCompatActivity implements OnItemClickListener{
    private static final String TAG_JSON="result";
    private static final String TAG_TOP ="top";
    private static final String TAG_BOTTOM ="bottom";

    ArrayList<Bookmark> arrayList;
    ListView BookmarkListView;
    BookmarkListAdapter adapter;

    String deleteTop;
    String deleteBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arrayList = new ArrayList<Bookmark>();
        BookmarkListView = (ListView) findViewById(R.id.BookmarklistView);

        ReadJSON readJson = new ReadJSON();
        readJson.execute("http://192.168.42.65/Get/GetBookmark.php");

        BookmarkListView.setOnItemClickListener(this);
//        플로팅액션 버튼 비활성화
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
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Bookmark data = arrayList.get(position);
        deleteTop = data.getTop();
        deleteBottom = data.getBottom();
        System.out.println("deleteTop : " + deleteTop);
        System.out.println("deleteBottom : " + deleteBottom);

        DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteBookmark delete = new deleteBookmark();
                delete.execute("http://192.168.42.65/Delete/DeleteBookmark.php?top="+deleteTop+"&bottom="+deleteBottom);
                System.out.println("http://192.168.42.65/Delete/DeleteBookmark.php?top="+deleteTop+"&bottom="+deleteBottom);
                arrayList.remove(position);
                adapter.notifyDataSetChanged();
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("데이터 삭제")
                .setMessage("삭제 하시겠습니까?")
                .setPositiveButton("삭제", deleteListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        } // 내가 추가

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray bookmarkJson =  (JSONArray) jsonObject.getJSONArray(TAG_JSON);

                for(int i =0; i+1<bookmarkJson.length(); i+=2){
                    JSONObject TopJson = (JSONObject) bookmarkJson.getJSONObject(i);
//                    System.out.println("TopJson : " + TopJson);
//                    System.out.println("top : " + TopJson.getString(TAG_TOP));
                    JSONObject BottomJson = (JSONObject) bookmarkJson.getJSONObject(i+1);
//                    System.out.println("BottomJson : " + BottomJson);
//                    System.out.println("bottom : " + BottomJson.getString(TAG_BOTTOM));
                    arrayList.add(new Bookmark(TopJson.getString(TAG_TOP), BottomJson.getString(TAG_BOTTOM)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new BookmarkListAdapter(
                    BookmarkActivity.this, R.layout.bookmark_list, arrayList
            );
            BookmarkListView.setAdapter(adapter);
        }
    }

    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    class deleteBookmark extends AsyncTask<String, Void, String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected void onPostExecute(String s) { super.onPostExecute(s); }

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }
    }
}
