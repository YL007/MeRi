package com.example.meri.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.activity.NewsDetailActivity;
import com.example.meri.base.BaseFragment;
import com.example.meri.bean.NewsBean;
import com.example.meri.utils.CacheUtils;
import com.example.meri.utils.HttpUtils;
import com.example.meri.utils.LogUtils;
import com.google.gson.Gson;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.meri.R.id.iv_newspic;
import static com.example.meri.api.ApiConstants.NEWS_DETAIL;
import static com.example.meri.api.ApiConstants.NEWS_URL;

/**
 * Created by 磊 on 2017/4/8.
 */

public class NewsFragment extends BaseFragment {

    private ViewPager viewPager;
    private TextView tv_todaynews;
    private ListView listview;
    private LinearLayout topstory_dotgroups;

    private List<NewsBean.TopStoriesBean> top_stories;  //顶部轮播图数据
    private List<NewsBean.StoriesBean> stories; //listView新闻数据
    private NewsListAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View initView() {
        View view = View.inflate(context,R.layout.main_newscontent,null);
        listview = (ListView) view.findViewById(R.id.listview);

        View topNews = View.inflate(context, R.layout.topnews, null);
        viewPager = (ViewPager) topNews.findViewById(R.id.news_storiespagers);
        tv_todaynews = (TextView) topNews.findViewById(R.id.tv_todaynews);
        TextPaint tp = tv_todaynews.getPaint();
        tp.setFakeBoldText(true);
        topstory_dotgroups = (LinearLayout) topNews.findViewById(R.id.topstory_dotgroups);

        //顶部轮播图以头的方式添加到listView
        listview.addHeaderView(topNews);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDataFromNet();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        //设置listView的item的点击监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position - 1;
                NewsBean.StoriesBean newsDate = stories.get(itemPosition);
                Toast.makeText(context,"newsDate_id="+newsDate.getId()+
                        ",newsDate_title"+newsDate.getTitle(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("url",NEWS_DETAIL+stories.get(itemPosition).getId());
                startActivity(intent);
            }
        });


        return view;

    }

    @Override
    public void initData() {
        super.initData();

        //获取缓存数据
        String saveJson = CacheUtils.getString(context, NEWS_URL);
        if (!TextUtils.isEmpty(saveJson)) {
            //解析数据
            processDate(saveJson);
        }

        //联网请求数据
        getDataFromNet();

    }

    private void getDataFromNet() {
        HttpUtils.sendOkHttpRequest(NEWS_URL, new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                LogUtils.e("联网请求成功==" + response.body().toString());
                final String result = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //缓存数据
                        CacheUtils.putString(context, NEWS_URL, result);
                        //解析json数据并显示
                        processDate(result);
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("联网请求失败==" + e.toString());
            }
        });
    }

    /**
     * 之前高亮点显示的位置
     */
    private int prePosition;

    private void processDate(String result) {
        Gson gson = new Gson();
        NewsBean bean = gson.fromJson(result, NewsBean.class);
        //顶部轮播图数据
        top_stories = bean.getTop_stories();
        //设置viewPager适配器
        viewPager.setAdapter(new TopStoriesPagerAdapter());

        //添加红点
        addPoint();

        //监听页面改变，设置圆点变化和文本改变
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_todaynews.setText(top_stories.get(prePosition).getTitle());

        //准备listView数据
        stories = bean.getStories();
        //设置ListView适配器
        adapter = new NewsListAdapter();
        listview.setAdapter(adapter);


    }

    private void addPoint() {
        topstory_dotgroups.removeAllViews();    //移除所有红点

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(8), DensityUtil.dip2px(8));


        for (int i = 0; i < top_stories.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);
            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }

            imageView.setLayoutParams(params);

            topstory_dotgroups.addView(imageView);
        }
    }

//
//    class MyOnItemClickListener implements AdapterView.OnItemClickListener {
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            int itemPosition = position - 1;
//            NewsBean.StoriesBean newsDate = stories.get(itemPosition);
//            Toast.makeText(context,"newsDate_id="+newsDate.getId()+",newsDate_title"+newsDate.getTitle(),Toast.LENGTH_SHORT).show();        }
//
//            Intent intent = new Intent(activity, NewsDetailActivity.class);
//            startActivity(intent);
//
//    }

    class NewsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stories.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_newslist, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_newspic = (ImageView) convertView.findViewById(iv_newspic);
                viewHolder.tv_newscontent = (TextView) convertView.findViewById(R.id.tv_newscontent);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置获取数据
            NewsBean.StoriesBean newsStories = stories.get(position);
            //设置图片
            List<String> storiesImage = newsStories.getImages();
            x.image().bind(viewHolder.iv_newspic, storiesImage.get(0));
            //设置标题
            viewHolder.tv_newscontent.setText(newsStories.getTitle());


            return convertView;
        }
    }

    static class ViewHolder {
        ImageView iv_newspic;
        TextView tv_newscontent;
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //1.设置文本
            tv_todaynews.setText(top_stories.get(position).getTitle());
            //2.圆点高亮
            topstory_dotgroups.getChildAt(prePosition).setEnabled(false);
            topstory_dotgroups.getChildAt(position).setEnabled(true);
            prePosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class TopStoriesPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return top_stories.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.news_default);
            imageView.setScaleType(imageView.getScaleType().FIT_XY);
            container.addView(imageView);

            NewsBean.TopStoriesBean top_storiesDate = top_stories.get(position);
            String imageDate = top_storiesDate.getImage();

            //联网请求图片
            x.image().bind(imageView, imageDate);

//            Glide.with(context)
//                    .load(imageDate)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
