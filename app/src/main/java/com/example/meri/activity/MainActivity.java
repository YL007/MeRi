package com.example.meri.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.adapter.MyFragmentPagerAdapter;
import com.example.meri.fragment.MeFragment;
import com.example.meri.fragment.NewsFragment;
import com.example.meri.fragment.PicFragment;
import com.example.meri.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.meri.R.id.radioGroup;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    public static final String MAIN_CONTENT_TAG = "main_content_tag";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private View headView;
    private ImageView headImage;
    private ActionBarDrawerToggle mToggle;
    //    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private List<Fragment> mFragment = new ArrayList<Fragment>();

//    private List<Fragment> listFragment;
    private ViewPager mPager;
//    private ViewPagerAdapter adapter;
    private RadioGroup mRadioGroup;
    private RadioButton rb_news;
    private RadioButton rb_pic;
    private RadioButton rb_video;
    private RadioButton rb_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initDrawerLayout();
    }


    //初始化视图
    private void initViews() {

        /**
         * Toolbar
         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        mNavigationView = (NavigationView) findViewById(R.id.navigateion_main);

        //通过NavigarView拿到Headview,然后才能拿到HeadView中的其他View
        headView = mNavigationView.getHeaderView(0);
        headImage = (ImageView) headView.findViewById(R.id.header_image);

        //设置NavigationView背景颜色
        mNavigationView.setItemTextColor(ColorStateList.valueOf(Color.rgb(0xff, 0x66, 0x00)));

//        ivRunningMan = (ImageView) findViewById(R.id.iv_main);

        /**
         * radioGroup
         */
        mRadioGroup = (RadioGroup) findViewById(radioGroup);
        rb_news = (RadioButton) findViewById(R.id.rb_news);
        rb_pic = (RadioButton) findViewById(R.id.rb_pic);
        rb_video = (RadioButton) findViewById(R.id.rb_video);
        rb_me = (RadioButton) findViewById(R.id.rb_me);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_news:
                        mPager.setCurrentItem(0);//选择某一页
                        break;
                    case R.id.rb_pic:
                        mPager.setCurrentItem(1);
                        break;
                    case R.id.rb_video:
                        mPager.setCurrentItem(2);
                        break;
                    case R.id.rb_me:
                        mPager.setCurrentItem(3);
                        break;
                }
            }
        });

        /**
         * ViewPager
         */
        mPager = (ViewPager) findViewById(R.id.content_viewpager);
        mFragment = getData();
//        adapter = new ViewPagerAdapter(viewList);
//        mPager.setAdapter(adapter);
//        mPager.addOnPageChangeListener(new TabOnPageChangeListener());
        //viewPager适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),mFragment));
        //viewPager设置第一个Fragment
        mPager.setCurrentItem(0);
        //viewPager页面切换监听器
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rb_news.setChecked(true);
                        break;
                    case 1:
                        rb_pic.setChecked(true);
                        break;
                    case 2:
                        rb_video.setChecked(true);
                        break;
                    case 3:
                        rb_me.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDrawerLayout() {
        mToolbar.setTitle("每日新闻");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mDrawerLayout.addDrawerListener(mToggle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);   //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现监听
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.navigateion_main);
        mNavigationView.setNavigationItemSelectedListener(this);

        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了Header View头像", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(GravityCompat.START);//关闭侧滑
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_news:
                Toast.makeText(MainActivity.this, "点击了news", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_pic:
                Toast.makeText(MainActivity.this, "点击了pic", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_interest:
                Toast.makeText(MainActivity.this, "点击了我的关注", Toast.LENGTH_SHORT).show();
                break;
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public List<Fragment> getData() {

//        mFragment = new ArrayList<Fragment>();
        NewsFragment newsFragment = new NewsFragment();
        PicFragment  picFragment = new PicFragment();
        VideoFragment videoFragment = new VideoFragment();
        MeFragment meFragment = new MeFragment();

        mFragment.add(newsFragment);
        mFragment.add(picFragment);
        mFragment.add(videoFragment);
        mFragment.add(meFragment);

        return mFragment;
    }

/*    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.content_viewpager);
        viewList = getData();
        adapter = new ViewPagerAdapter(viewList);
        mPager.setAdapter(adapter);
        mPager.addOnPageChangeListener(new TabOnPageChangeListener());


    }

    private List<View> getData() {
        viewList = new ArrayList<View>();

        LayoutInflater mInflater = LayoutInflater.from(this);
        View tab01 = mInflater.inflate(R.layout.content_news, null);
        View tab02 = mInflater.inflate(R.layout.content_pic, null);
        View tab03 = mInflater.inflate(R.layout.content_video, null);
        View tab04 = mInflater.inflate(R.layout.content_me, null);

        viewList.add(tab01);
        viewList.add(tab02);
        viewList.add(tab03);
        viewList.add(tab04);
        return viewList;
    }

    *//**
     * 页卡改变事件
     *//*
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    rb_news.setChecked(true);
                    break;
                case 1:
                    rb_pic.setChecked(true);
                    break;
                case 2:
                    rb_video.setChecked(true);
                    break;
                case 3:
                    rb_me.setChecked(true);
                    break;
            }
        }
    }*/
}