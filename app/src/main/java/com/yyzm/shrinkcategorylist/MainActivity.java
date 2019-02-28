package com.yyzm.shrinkcategorylist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yyzm.shrinkcategoryrecycleview.itemModel;
import com.yyzm.shrinkcategoryrecycleview.shrinkcategoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;
    private List<itemModel> mData = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    shrinkcategoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        setData();
        list = findViewById(R.id.relist);
        mLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(mLayoutManager);
        adapter = new shrinkcategoryAdapter(this, mData);
        adapter.setColumNum(4).setVisiblenum(4);
        adapter.setHasStableIds(true);
        list.setAdapter(adapter);
    }

    private void setData() {
        //对item的数据进行初始化
        for (int i = 0; i < 10; i++) {
            itemModel im = new itemModel();
            im.setWord("item");
            im.setTag("历史");
            im.setDrawableid(R.drawable.ms);
            im.setSingleline(2);
            mData.add(im);
        }

        for (int i = 0; i < 7; i++) {
            itemModel im = new itemModel();
            im.setWord("item");
            im.setTag("现在");
            im.setDrawableid(R.drawable.ms);
            im.setSingleline(2);
            mData.add(im);
        }

        for (int i = 0; i < 7; i++) {
            itemModel im = new itemModel();
            im.setWord("item");
            im.setTag("测试");
            im.setDrawableid(R.drawable.ms);
            im.setSingleline(2);
            mData.add(im);
        }
    }

}
