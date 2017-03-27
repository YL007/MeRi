package com.example.meri.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.meri.R;
import com.example.meri.base.BaseFragment;
import com.example.meri.base.BasePager;
import com.example.meri.pager.NewsPager;
import com.example.meri.pager.PicPager;
import com.example.meri.pager.SettingPager;
import com.example.meri.pager.VideoPager;
import com.example.meri.view.NoScrollViewPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by 磊 on 2017/3/22.
 */

public class ContentFragment extends BaseFragment {

    //利用xUtils3初始化控件
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewPager;

    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    //4个页面的集合
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
          View view = View.inflate(context,R.layout.content_fragment,null);
//        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
//        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        //把视图注入到框架中，让ContentFragment和View关联起来
        x.view().inject(ContentFragment.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //初始化4个页面，并放入到集合中
        basePagers = new ArrayList<>();
        basePagers.add(new NewsPager(context)); //每日益读
        basePagers.add(new PicPager(context));  //每日美图
        basePagers.add(new VideoPager(context));    //每日短片
        basePagers.add(new SettingPager(context));  //我的

        //设置默认选中为新闻
        radioGroup.check(R.id.rb_news);
        basePagers.get(0).initData();

        //设置ViewPager适配器
        viewPager.setAdapter(new ContentFragmentAdapter());

        //设置rodioGroup的选中状态改变的监听
        radioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //监听某个页面被选中，初始化对应的页面数据
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中时，回调此方法
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            //调用被选中页面的initData()
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        /**
         *
         * @param group     radioGroup
         * @param checkedId     被选中的radioButton的id
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_news:
                    viewPager.setCurrentItem(0,false);  //false表示没有动画效果
                    break;
                case R.id.rb_pic:
                    viewPager.setCurrentItem(1,false);
                    break;
                case R.id.rb_video:
                    viewPager.setCurrentItem(2,false);
                    break;
                case R.id.rb_me:
                    viewPager.setCurrentItem(3,false);
                    break;
            }
        }
    }

    class ContentFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //每个页面的实例
            BasePager basePager = basePagers.get(position);
            //各个子页面
            View rootView = basePager.rootView;
            //调用各个页面的initData(),初始化数据
            //basePager.initData();
            //添加到容器中
            container.addView(rootView);
            return rootView;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
