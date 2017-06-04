package com.example.meri.fragment;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.adapter.VideoListAdapter;
import com.example.meri.base.BaseFragment;
import com.example.meri.bean.VideoBean;
import com.example.meri.utils.HttpUtils;
import com.example.meri.utils.LogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.meri.api.ApiConstants.VIDEO_URL;

/**
 * Created by 磊 on 2017/4/8.
 */

public class VideoFragment extends BaseFragment {

    private List<VideoBean.V9LG4B3A0Bean> videoList = new ArrayList<>();
    private VideoListAdapter adapter;
    private ListView listView;
    private AbsListView.OnScrollListener onScrollListener;
    private int firstVisible;
    private int visibleCount;
    private JCVideoPlayerStandard currPlayer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton ivImageView;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_video, null);
        listView = (ListView) view.findViewById(R.id.listVideoView);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_video);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

/*        // 这句话是为了，第一次进入页面的时候显示加载进度条
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
                getData();
            }
        },1000);
        */

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        videoList.clear();
                        getData();
                    }
                }, 2000);
            }
        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getData();

        adapter = new VideoListAdapter(context, videoList, R.layout.item_video);
        listView.setAdapter(adapter);

        initListener();

    }

    private void initListener() {
        /**
         * 滑动监听
         */
        onScrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        //滑动停止自动播放视频
                        autoPlayVideo(view);
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisible == firstVisibleItem) {
                    return;
                }

                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;
            }
        };

        listView.setOnScrollListener(onScrollListener);
    }

    /**
     * 滑动停止自动播放视频
     */
    private void autoPlayVideo(AbsListView view) {
        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.list_video) != null) {
                currPlayer = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.list_video);
                Rect rect = new Rect();
                //获取当前view 的 位置
                currPlayer.getLocalVisibleRect(rect);
                int videoheight = currPlayer.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL
                            || currPlayer.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        currPlayer.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }

    private void getData() {

        HttpUtils.sendOkHttpRequest(VIDEO_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context, "视频下载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.e("联网请求视频成功==" + response.body().toString());
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    //Gson解析Json数据
                    Gson gson = new Gson();
                    VideoBean videoBean = gson.fromJson(result, VideoBean.class);
                    videoList.addAll(videoBean.getV9LG4B3A0());
                }
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}

