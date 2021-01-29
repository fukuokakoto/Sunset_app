package com.nifcloud.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.FetchFileCallback;
import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBFile;
import com.nifcloud.mbaas.core.NCMBUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView sunset;
    Button button;
    Button button3;

    //HTTP 通信
    MyTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button3 = findViewById(R.id.button3);
        sunset = findViewById(R.id.sunset);
        task = new MyTask( sunset){};
        task.execute(makeUrl());


        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this.getApplicationContext(), "6a25cbacd3ab5fe193371c50b1bd08c3e5e4b90963aa0b0c81499647979122fb", "6ba0bcd5d985b73eb05c1b227b18e69ba06f7c67f6f3e17f7269c461af7a3ba0");

        NCMBFile file = null;
        try {
            file = new NCMBFile("Screenshot_1608082444.png");
        } catch (NCMBException e) {
            e.printStackTrace();
        }
        file.fetchInBackground(new FetchFileCallback() {
            @Override
            public void done(byte[] data, NCMBException e) {
                if (e != null) {
                    //失敗
                } else {
                    //成功
                }
            }
        });
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void sub( View view){
        Intent intent = new Intent( getApplication(), YoutubeView.class );
        startActivity(intent);
    }

    public void grid(View view){
        Intent intent = new Intent( getApplication(), GridActivity.class );
        //Intent intent = new Intent( this, GridActivity.class );
        startActivity(intent);
    }

    //日時作成
    public  String getNowdate(){
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    //パラメータ作成
    public String makeUrl(){
        String year;
        String month;
        String day;
        year = getNowdate().substring(0,4);
        month =getNowdate().substring(5,7);
        day = getNowdate().substring(8,10);
        return "http://labs.bitmeister.jp/ohakon/api/?mode=sun_moon_rise_set&year=" +  year + "&month=" + month + "&day=" + day + "&lat=33.9288&lng=130.6850";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            NCMBUser.logoutInBackground(new DoneCallback() {
                @Override
                public void done(NCMBException e) {
                    if (e != null) {
                        //エラー時の処理
                    }
                }
            });
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //非同期　HTTP通信　 Async 
    private class MyTask extends AsyncTask< String, String, String > {

        TextView sunsetnew;

        public MyTask(TextView sunset) {
            sunsetnew = sunset;
        }

        @Override
        protected String doInBackground(String... param) {

            String result = "";
            HttpURLConnection con = null;
            InputStream is = null;
            try {
                URL url = new URL(param[0]);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();
                result = is2String(is);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        //APIを叩いて返ってきたXMLから、sunset時刻を抽出
        private String is2String(InputStream is) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuffer sb = new StringBuffer();

            ArrayList<String> foo = new ArrayList<>();
            String line = null;
            while ((line = br.readLine()) != null) {
                foo.add(line);
            }
            String str2 = "</sunset_hm>";
            int result = foo.get(18).indexOf(str2);

            String sunset_a = foo.get(18).substring(11, result);
            return sunset_a;
        }

        @Override
        protected void onPostExecute(String sunset_a) {
            sunsetnew.setText( "Sunset  " +  sunset_a);
            //super.onPostExecute(o);
        }

    }
}
