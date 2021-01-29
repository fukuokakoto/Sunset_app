package com.nifcloud.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {



    // 表示する画像の名前（拡張子無し）
    private String[] members = {
            "img1","img2","img3","img4","img5"
            ,"img6","img7","img8"
            ,"img9","img10"
    };

    // Resource IDを格納
    private List<Integer> imgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);


        // for-each member名をR.drawable.名前としてintに変換してarrayに登録
        for (String member: members){
            int imageId = getResources().getIdentifier(
                    member,"drawable", getPackageName());
            imgList.add(imageId);
        }

        // GridViewのインスタンスを生成
        GridView gridview = findViewById(R.id.gridview);
        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル grid_items.xml を
        // activity_main.xml に inflate するためにGridAdapterに引数として渡す
        GridAdapter adapter = new GridAdapter(this.getApplicationContext(),
                R.layout.grid_items,
                imgList,
                members
        );
        Log.i("checkGrid","imgList:" + imgList.toString());
        Log.i("checkGrid","members:" + members.toString());
        Log.i("checkGrid","gridAdapter:" + adapter.toString());
        //Log.i("checkGrid","gridView:" + gridview.getId());


        //gridViewにadapterをセット
        gridview.setAdapter(adapter);
        //gridview.setAdapter(null);

        //item clickのListnerをセット
        gridview.setOnItemClickListener(this);


    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplication(), SubActivity.class);
        intent.putExtra("IMAGEID", imgList.get(position));
        startActivity( intent );
    }




}