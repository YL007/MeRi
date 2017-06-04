package com.example.meri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.adapter.RecyclerViewAdapter;
import com.example.meri.adapter.RecyclerViewLikeAdapter;
import com.example.meri.bean.Image;
import com.example.meri.bean.PicBean;
import com.example.meri.utils.HttpUtils;
import com.example.meri.utils.LogUtils;
import com.example.meri.view.DividerGridItemDecoration;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.meri.api.ApiConstants.PIC_IMAGE;

/**
 * Created by 磊 on 2017/5/25.
 */

public class ImageLikeActivity extends AppCompatActivity implements RecyclerViewLikeAdapter.OnShowItemClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Image> imageList = new ArrayList<>();
    private RecyclerViewLikeAdapter adapter;
    private List<PicBean.ResultsBean> picList = new ArrayList<>();
    private int index = 1;
    private boolean isShow;
    private HashSet<Integer> SelectId;
    private LinearLayout lay;
    private List<Image> selectList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_like);
        initView();
        initData();

        //设置布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(ImageLikeActivity.this,3));
        //设置adapter
        adapter = new RecyclerViewLikeAdapter(this,imageList);
        recyclerView.setAdapter(adapter);
        adapter.setOnShowItemClickListener(this);

        //添加分割线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isShow) {
                    Image bean = imageList.get(position);
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
                    Intent intent = new Intent(ImageLikeActivity.this, PreviewImageActivity.class);
                    intent.putExtra("URL", imageList.get(position).getUrl());
                    startActivity(intent);
//                    Toast.makeText(NewsLikeActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                if (isShow) {
                    return false;
                } else {
                    isShow = true;
                    for (Image bean : imageList) {
                        bean.setShow(true);
                    }
                    adapter.notifyDataSetChanged();
                    showOpervate();
//                    listView.setLongClickable(false);
                }
                return true;
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_delete);
        toolbar.setTitle("图片收藏");
        recyclerView = (RecyclerView) findViewById(R.id.rv_image);
        lay = (LinearLayout) findViewById(R.id.lay);
        selectList = new ArrayList<Image>();
        SelectId = new HashSet<>();

        //设置Item增加、移除动画
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //取代actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //toolbar删除按钮监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(ImageLikeActivity.this, "删除news", Toast.LENGTH_SHORT).show();
                if (isShow) {
                    return false;
                } else {
                    isShow = true;
                    for (Image bean : imageList) {
                        bean.setShow(true);
                    }
                    adapter.notifyDataSetChanged();
                    showOpervate();
//                    listView.setLongClickable(false);
                }
                return true;
            }
        });
    }

    private void initData() {
        imageList = DataSupport.findAll(Image.class);
        LogUtils.e("获取图片数据="+imageList.size());

//        getDataFromNet();
    }

    public void getDataFromNet() {
        HttpUtils.sendOkHttpRequest(PIC_IMAGE + index, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(ImageLikeActivity.this, "联网请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtils.e("联网请求图片成功==" + response.body().toString());
                        if (response.isSuccessful()) {
                            String result = response.body().string();
                            //Gson解析Json数据
                            Gson gson = new Gson();
                            PicBean picBean = gson.fromJson(result, PicBean.class);
                            picList.addAll(picBean.getResults());
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //设置适配器
//                                adapter = new RecyclerViewAdapter(context,picList);
//                                setAdapter();
                                adapter.notifyDataSetChanged();
                                adapter.notifyItemRemoved(adapter.getItemCount());
                            }
                        });
                    }
                }
        );
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
                    for (Image bean : imageList) {
                        bean.setChecked(false);
                        bean.setShow(false);
//                        bean.getCheckedTrue();
                    }
                    adapter.notifyDataSetChanged();
                    isShow = false;
//                    listView.setLongClickable(true);
                    dismissOperate();
                }
            }
        });
        tvSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (Image bean : imageList) {
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
                for (Image bean : imageList){
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
                    imageList.removeAll(selectList);
                    adapter.notifyDataSetChanged();
                    selectList.clear();

                    for ( int id : SelectId){
                        DataSupport.delete(Image.class, id);
                    }
                    LogUtils.e("删除 = "+ SelectId);
                } else {
                    Toast.makeText(ImageLikeActivity.this,"请选择条目", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 隐藏操作界面
     */
    private void dismissOperate() {
        Animation anim = AnimationUtils.loadAnimation(ImageLikeActivity.this, R.anim.operate_out);
        lay.setVisibility(View.GONE);
        lay.setAnimation(anim);
    }

    @Override
    public void onShowItemClick(Image bean) {
        if (bean.isChecked() && !selectList.contains(bean)) {
            selectList.add(bean);
        } else if (!bean.isChecked() && selectList.contains(bean)) {
            selectList.remove(bean);
        }
    }
}
