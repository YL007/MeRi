package com.example.meri.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.activity.PreviewImageActivity;
import com.example.meri.adapter.RecyclerViewAdapter;
import com.example.meri.base.BaseFragment;
import com.example.meri.bean.PicBean;
import com.example.meri.utils.HttpUtils;
import com.example.meri.utils.LogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.meri.api.ApiConstants.PIC_IMAGE;

/**
 * Created by 磊 on 2017/4/8.
 */

public class PicFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private List<PicBean.ResultsBean> picList = new ArrayList<>();

    //创建一个list集合存储recyclerview中的图片的高度
    private List<Integer> heights = new ArrayList<>();

    private int index = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoading = true;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_pic, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));

//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapter.notifyDataSetChanged();

        //获取下拉刷新对象
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_pic);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //初次进入，显示加载动画，加载初始的数据
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(1);
            }
        }, 1000);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        picList.clear();
                        getData(1);
                    }
                }, 2000);
            }
        });

        adapter = new RecyclerViewAdapter(context, picList);
        recyclerView.setAdapter(adapter);

        //加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            int lastVisibleItem ;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止第一行到顶部有空白区域
//                layoutManager.invalidateSpanAssignments();

//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItem + 1 == adapter.getItemCount()) {
//                    swipeRefreshLayout.setRefreshing(true);
//                    getData(index);
//                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isScrollToEnd(recyclerView)) {
                    LogUtils.e("tag", "============scroll to end");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            index++;
                            getData(index);
                            LogUtils.e("test", "load more completed");
//                            isLoading = false;
                        }
                    }, 1000);
                }
            }
        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        /*//联网请求数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(index);
            }
        },1000);*/


        //设置item点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, PreviewImageActivity.class);
                intent.putExtra("URL", picList.get(position).getUrl());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, int position) {

                return false;
            }
        });
    }

    private boolean isScrollToEnd(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    private void getData(int index) {
        HttpUtils.sendOkHttpRequest(PIC_IMAGE + index, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(context, "联网请求失败", Toast.LENGTH_SHORT).show();
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

                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //设置适配器
//                                adapter = new RecyclerViewAdapter(context,picList);
//                                setAdapter();
                                adapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                                adapter.notifyItemRemoved(adapter.getItemCount());
                            }
                        });
                    }
                }
        );

    }

}
