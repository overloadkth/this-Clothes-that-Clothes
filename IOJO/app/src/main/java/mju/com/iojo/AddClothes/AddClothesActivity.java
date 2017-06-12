package mju.com.iojo.AddClothes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import mju.com.iojo.R;

public class AddClothesActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int CROP_FROM_IMAGE = 2;
    private static final int PICK_FROM_ALBUM = 1;
    public static Context context;
    private Uri ImageUri;
    private ImageView AddImageView;
    private int id_view;
    private String absolutePath;
    ArrayAdapter<CharSequence> Type, Style, Color;
    private String _Type, _Style, _Color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AddImageView = (ImageView) findViewById(R.id.AddImageView1);
        Button btn_camera = (Button) findViewById(R.id.AddButton1);
        Button btn_enroll = (Button) findViewById(R.id.AddButton2);
        final Spinner spin1 = (Spinner) findViewById(R.id.addClothesSpinner1);
        final Spinner spin2 = (Spinner) findViewById(R.id.addClothesSpinner2);
        final Spinner spin3 = (Spinner) findViewById(R.id.addClothesSpinner3);

        Type = ArrayAdapter.createFromResource(this, R.array.AddSpinnerArray1, android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(Type);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Type.getItem(i).equals("아우터")) {
                    _Type = "outer";
                    Style = ArrayAdapter.createFromResource(AddClothesActivity.this, R.array.AddSpinnerArray2_1, android.R.layout.simple_spinner_dropdown_item);
                    Style.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(Style);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(Style.getItem(i).equals("코트")){
                                _Style = "coat";
                            } else if (Style.getItem(i).equals("점퍼")){
                                _Style = "jumper";
                            } else if (Style.getItem(i).equals("패딩")){
                                _Style = "padding";
                            } else if (Style.getItem(i).equals("자켓")){
                                _Style = "jacket";
                            } else if (Style.getItem(i).equals("가디건")){
                                _Style = "cardigan";
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else if (Type.getItem(i).equals("상의")) {
                    _Type = "top";
                    Style = ArrayAdapter.createFromResource(AddClothesActivity.this, R.array.AddSpinnerArray2_2, android.R.layout.simple_spinner_dropdown_item);
                    Style.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(Style);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(Style.getItem(i).equals("셔츠")){
                                _Style = "shirts";
                            } else if (Style.getItem(i).equals("니트")){
                                _Style = "knit";
                            } else if (Style.getItem(i).equals("긴팔")){
                                _Style = "longsleeve";
                            } else if (Style.getItem(i).equals("반팔")){
                                _Style = "shortsleeve";
                            } else if (Style.getItem(i).equals("트레이닝")){
                                _Style = "training";
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                } else if (Type.getItem(i).equals("하의")) {
                    _Type = "bottom";
                    Style = ArrayAdapter.createFromResource(AddClothesActivity.this, R.array.AddSpinnerArray2_3, android.R.layout.simple_spinner_dropdown_item);
                    Style.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(Style);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(Style.getItem(i).equals("청바지")){
                                _Style = "jeans";
                            } else if (Style.getItem(i).equals("반바지")){
                                _Style = "shorts";
                            } else if (Style.getItem(i).equals("면바지")){
                                _Style = "chino";
                            } else if (Style.getItem(i).equals("슬랙스")){
                                _Style = "slacks";
                            } else if (Style.getItem(i).equals("트레이닝")){
                                _Style = "training";
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Color = ArrayAdapter.createFromResource(AddClothesActivity.this, R.array.AddSpinnerArray3, android.R.layout.simple_spinner_dropdown_item);
        Color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(Color);
        spin3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(Color.getItem(i).equals("블랙")){
                    _Color = "black";
                } else if (Color.getItem(i).equals("화이트")){
                    _Color = "white";
                } else if (Color.getItem(i).equals("그레이")){
                    _Color = "gray";
                } else if (Color.getItem(i).equals("레드")){
                    _Color = "red";
                } else if (Color.getItem(i).equals("옐로우")){
                    _Color = "yellow";
                } else if (Color.getItem(i).equals("오렌지")){
                    _Color = "orange";
                } else if (Color.getItem(i).equals("그린")){
                    _Color = "green";
                } else if (Color.getItem(i).equals("블루")){
                    _Color = "blue";
                } else if (Color.getItem(i).equals("네이비")){
                    _Color = "navy";
                } else if (Color.getItem(i).equals("브라운")){
                    _Color = "brown";
                } else if (Color.getItem(i).equals("베이지")){
                    _Color = "beige";
                } else if (Color.getItem(i).equals("카키")){
                    _Color = "kakhi";
                } else if (Color.getItem(i).equals("핑크")){
                    _Color = "pink";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
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

    public void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String uri = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        ImageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), uri));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, ImageUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode){
            case PICK_FROM_ALBUM: {
                ImageUri = data.getData();
                Log.d("MyClothes", ImageUri.getPath().toString());
            } case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(ImageUri, "image/*");

                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);

                startActivityForResult(intent, CROP_FROM_IMAGE);
                break;
            } case CROP_FROM_IMAGE: {
                if(resultCode != RESULT_OK){
                    return;
                }

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyClothes/" + System.currentTimeMillis() + ".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    AddImageView.setImageBitmap(photo);

                    storeCropImage(photo, filePath);

                    absolutePath = filePath;
                    break;
                }

                File f = new File(ImageUri.getPath());
                if(f.exists()){
                    f.delete();
                }
            }
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyClothes";
        File directory_MyClothes = new File(dirPath);

        if(!directory_MyClothes.exists()){
            directory_MyClothes.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        id_view = v.getId();
        if(v.getId() == R.id.AddButton1) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    doTakeAlbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        } else if (v.getId() == R.id.AddButton2) {
            if(_Type.equals("outer")) {
                insertToDatabase(_Type, _Style, _Color, ImageUri.toString());
            } else if(_Type.equals("top")) {
                insertToDatabase(_Type, _Style, _Color, ImageUri.toString());
            } else if(_Type.equals("bottom")) {
                insertToDatabase(_Type, _Style, _Color, ImageUri.toString());
            }
        }
    }

    private void insertToDatabase(String type, String style, String color, String uri){
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddClothesActivity.this,
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
                    String type = (String)params[0];
                    String style = (String)params[1];
                    String color = (String)params[2];
                    String uri = (String)params[3];
                    String link = null;
                    if(type.equals("outer")){
                        link="http://192.168.42.65/Insert/InsertOuterStyle.php";
                    } else if (type.equals("top")){
                        link="http://192.168.42.65/Insert/InsertTopStyle.php";
                    } else if (type.equals("bottom")){
                        link="http://192.168.42.65/Insert/InsertBottomStyle.php";
                    }

                    String data  = URLEncoder.encode("style", "UTF-8") + "="
                            + URLEncoder.encode(style, "UTF-8");
                    data += "&" + URLEncoder.encode("color", "UTF-8") + "="
                            + URLEncoder.encode(color, "UTF-8");
                    data += "&" + URLEncoder.encode("uri", "UTF-8") + "="
                            + URLEncoder.encode(uri, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr =
                            new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));

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
        task.execute(type, style, color, uri);
    }

}
