package com.example.meri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.adapter.NewsLikeAdapter;
import com.example.meri.bean.News;
import com.example.meri.bean.NewsBean;
import com.example.meri.utils.HttpUtils;
import com.example.meri.utils.LogUtils;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.meri.api.ApiConstants.NEWS_URL;

/**
 * Created by 磊 on 2017/5/17.
 */
public class NewsLikeActivity extends AppCompatActivity implements NewsLikeAdapter.OnShowItemClickListener {

    private Toolbar toolbar;
    private ListView listView;
    private NewsLikeAdapter adapter;
    private List<News> newsList;

    private static boolean isShow; // 是否显示CheckBox标识
    private List<News> selectList;
    private LinearLayout lay;
    private HashSet<Integer> SelectId;
    private List<NewsBean.StoriesBean> stories;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_like);

        initView();
        initData();
        adapter = new NewsLikeAdapter(NewsLikeActivity.this, R.layout.item_newslist, newsList);
        listView.setAdapter(adapter);

        adapter.setOnShowItemClickListener(this);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_delete);
        toolbar.setTitle("新闻收藏");
        listView = (ListView) findViewById(R.id.ls_newslike);

        lay = (LinearLayout) findViewById(R.id.lay);
        newsList = new ArrayList<News>();
        selectList = new ArrayList<News>();
        SelectId = new HashSet<>();


        //取代actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //toolbar删除按钮监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(NewsLikeActivity.this, "删除news", Toast.LENGTH_SHORT).show();
                if (isShow) {
                    return false;
                } else {
                    isShow = true;
                    for (News bean : newsList) {
                        bean.setShow(true);
                    }
                    adapter.notifyDataSetChanged();
                    showOpervate();
                    listView.setLongClickable(false);
                }
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isShow) {
                    News bean = newsList.get(position);
                    boolean isChecked = bean.isChecked();
                    if (isChecked) {
                        bean.setChecked(false);
                        SelectId.remove(bean.getId());
                    } else {
                        bean.setChecked(true);
                        SelectId.add(bean.getId());
                    }
                    LogUtils.e("id = " + SelectId);
                    adapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(NewsLikeActivity.this, NewsDetailActivity.class);
                    intent.putExtra("url", newsList.get(position).getUrl());
                    startActivity(intent);
//                    Toast.makeText(NewsLikeActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShow) {
                    return false;
                } else {
                    isShow = true;
                    for (News bean : newsList) {
                        bean.setShow(true);
                    }
                    adapter.notifyDataSetChanged();
                    showOpervate();
                    listView.setLongClickable(false);
                }
                return true;
            }
        });




    }

    public void onShowItemClick(News bean) {
        if (bean.isChecked() && !selectList.contains(bean)) {
            selectList.add(bean);
        } else if (!bean.isChecked() && selectList.contains(bean)) {
            selectList.remove(bean);
        }
    }

    /**
     * 显示操作界面
     */
    private void showOpervate() {

        lay.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.operate_in);
        lay.setAnimation(anim);
        // 返回、删除、全选和反选按钮初始化及点击监听
        TextView tvBack =(TextView) findViewById(R.id.operate_back);
        TextView tvDelete = (TextView) findViewById(R.id.operate_delete);
        TextView tvSelect = (TextView) findViewById(R.id.operate_select);
        TextView tvInvertSelect = (TextView) findViewById(R.id.invert_select);

        tvBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isShow) {
                    selectList.clear();
                    for (News bean : newsList) {
                        bean.setChecked(false);
                        bean.setShow(false);
//                        bean.getCheckedTrue();
                    }
                    adapter.notifyDataSetChanged();
                    isShow = false;
                    listView.setLongClickable(true);
                    dismissOperate();
                }
            }
        });
        tvSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (News bean : newsList) {
                    if (!bean.isChecked()) {
                        bean.setChecked(true);
//                        SelectId = bean.getId();
                        if (!selectList.contains(bean)) {
                            selectList.add(bean);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        tvInvertSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (News bean : newsList){
                    if (!bean.isChecked()){
                        bean.setChecked(true);
                        if (!selectList.contains(bean)) {
                            selectList.add(bean);
                        }
                    }else {
                        bean.setChecked(false);
                        if (!selectList.contains(bean)) {
                            selectList.remove(bean);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectList != null && selectList.size() > 0) {
                    newsList.removeAll(selectList);
                    adapter.notifyDataSetChanged();
                    selectList.clear();

                    for ( int id : SelectId){
                        DataSupport.delete(News.class, id);
                    }
                    LogUtils.e("删除 = "+ SelectId);
                } else {
                    Toast.makeText(NewsLikeActivity.this, "请选择条目", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 隐藏操作界面
     */
    private void dismissOperate() {
        Animation anim = AnimationUtils.loadAnimation(NewsLikeActivity.this, R.anim.operate_out);
        lay.setVisibility(View.GONE);
        lay.setAnimation(anim);
    }

    //获取收藏的news列表
    private void initData() {
        newsList = DataSupport.findAll(News.class);
        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        HttpUtils.sendOkHttpRequest(NEWS_URL, new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                LogUtils.e("联网请求成功==" + response.body().toString());
                final String result = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        NewsBean bean = gson.fromJson(result, NewsBean.class);
                        stories = bean.getStories();
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("联网请求失败==" + e.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isShow) {
            selectList.clear();
            for (News bean : newsList) {
                bean.setChecked(false);
                bean.setShow(false);
            }
            adapter.notifyDataSetChanged();
            isShow = false;
            listView.setLongClickable(true);
            dismissOperate();
        } else {
            super.onBackPressed();
        }
    }
}
