package mju.com.iojo.ClosetAdmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import mju.com.iojo.R;
import mju.com.iojo.StyleRecommend.StyleRecommendActivity;

public class ClosetFragment extends Fragment implements OnItemClickListener {
    private static String TAG = "getStyle_MainActivity";

    private static final String TAG_JSON="result";
    private static final String TAG_URI ="uri";

    ArrayList<StyleImage> arrayList;
    ListView StyleListView;
    Context context = null;
    String type;
    String style;
    CustomListAdapter adapter;

    String deleteType;
    String deleteUri;

    public ClosetFragment(Context context, String type, String style) {
        this.context = context;
        this.type = type;
        this.style = style;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_clothes_admin, container, false);

        arrayList = new ArrayList<>();
        StyleListView = (ListView) view.findViewById(R.id.StylelistView);

        ReadJSON readJson = new ReadJSON();
        readJson.execute("http://192.168.42.65/Get/GetStyle.php?type="+type+"&style="+style);
        deleteType = type;

        StyleListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        StyleImage data = arrayList.get(position);
        deleteUri = data.getImage();
        DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayList.remove(position);      //
                adapter.notifyDataSetChanged(); // 이거 두개 자리바꿈 오류뜨면 속상함
                deleteData delete = new deleteData();
                delete.execute("http://192.168.42.65/Delete/DeleteStyle.php?type="+deleteType+"&uri="+deleteUri);

                System.out.println("deleteType : " + deleteType);
                System.out.println("deleteUri : " + deleteUri);
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(getActivity())
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
                JSONArray jsonArray =  jsonObject.getJSONArray(TAG_JSON);

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject styleObject = jsonArray.getJSONObject(i);
                    arrayList.add(new StyleImage(
                            styleObject.getString(TAG_URI)
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new CustomListAdapter(context, R.layout.item_list, arrayList);
            StyleListView.setAdapter(adapter);
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

    class deleteData extends AsyncTask<String, Void, String> {
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
