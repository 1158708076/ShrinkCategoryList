package com.yyzm.shrinkcategorylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yyzm.shrinkcategoryrecycleview.itemModel;
import com.yyzm.shrinkcategoryrecycleview.shrinkcategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;
    private List<childModel> mData = new ArrayList<>();
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
        adapter.setColumNum(4).setVisiblenum(4).setOnclicklistener(new shrinkcategoryAdapter.setOnItemClicklistener() {
            @Override
            public void onItemListener(int position, View v, itemModel itemmodel) {
                Toast.makeText(MainActivity.this, "点击了第" + position + "个，名字为：" + itemmodel.getWord(), Toast.LENGTH_SHORT).show();
            }
        });
        list.setAdapter(adapter);
    }

    private void setData() {
        //对item的数据进行初始化
        for (int i = 0; i < 10; i++) {
            childModel im = new childModel();
            im.setWord("item" + i);
            im.setTag("历史");
            im.setDrawableid(R.drawable.ms);
            im.setSingleline(2);
            mData.add(im);
        }

        for (int i = 0; i < 7; i++) {
            childModel im = new childModel();
            im.setWord("item" + 9 + i);
            im.setTag("现在");
            im.setDrawableid(R.drawable.ms);
            im.setSingleline(2);
            mData.add(im);
        }

        for (int i = 0; i < 7; i++) {
            childModel im = new childModel();
            im.setWord("item" + 16 + i);
            im.setTag("测试");
            im.setDrawableid(R.drawable.ms);
            im.setSingleline(2);
            mData.add(im);
        }
    }

}
