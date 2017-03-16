package com.example.meri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.meri.R;
import com.example.meri.WelcomeActivity;
import com.example.meri.utils.CacheUtils;
import com.example.meri.utils.DensityUtil;

import java.util.ArrayList;

public class GuildActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;
    private LinearLayout.LayoutParams params;
    private ImageView iv_red_point;
    private int leftmax;

    private int withdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);

        //引导页数据
        int[] ids = new int[]{
                R.drawable.guild_news,
                R.drawable.guild_picture,
                R.drawable.guild_weather
        };

        withdpi = DensityUtil.dip2px(this,10);

        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);
            //添加到集合
            imageViews.add(imageView);
            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            /**
             * 参数的单位是像素
             * 适配：把单位dp转为像素
             */
            params = new LinearLayout.LayoutParams(withdpi,withdpi);
            if (i!=0){
                //设置圆点间的间距
                params.leftMargin = withdpi;
            }
            point.setLayoutParams(params);
            //添加到线性布局中
            ll_point_group.addView(point);

        }
        //设置ViewPager适配器
        viewPager.setAdapter(new MyPagerAdapter());

        //根据view的生命周期，当执行到onLayout或onDraw时，已经获取视图的宽，高和边距
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{

        @Override
        public void onGlobalLayout() {
            //执行不止一次
            iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            //计算间距 间距=第一个点距离左边的距离-第0个点距离左边的距离
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
            //得到屏幕滑动的百分比
            viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

            //设置按钮点击事件，进入主页面
            btn_start_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1.保存曾经进入过的主页面
                    CacheUtils.putBoolean(GuildActivity.this, WelcomeActivity.START_MAIN,true);
                    //2.跳转到主页面
                    Intent intent = new Intent(GuildActivity.this,MainActivity.class);
                    startActivity(intent);
                    //3.关闭引导页面
                }
            });
        }
        class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
            private RelativeLayout.LayoutParams params;

            /**
             * 页面滚动回调
             * @param position  当前滑动的位置
             * @param positionOffset    页面滑动百分比
             * @param positionOffsetPixels  滑动的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //两点间移动距离=屏幕百分比*间距
                //int leftmargin = (int) (positionOffset*leftmax);
                //两点间滑动距离对应的坐标=原来的起始位置+两点间移动的距离
                int leftmargin = (int) (position*leftmax+positionOffset*leftmax);
                //parms.leftMargin=两点间滑动距离对应的坐标
                params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
                params.leftMargin = leftmargin;
                iv_red_point.setLayoutParams(params);
            }

            /**
             *页面被选中时回调
             * @param position 选中的位置
             */
            @Override
            public void onPageSelected(int position) {
                /**
                 * 最后一个页面
                 */
                if (position==imageViews.size()-1){
                    btn_start_main.setVisibility(View.VISIBLE);
                }else{
                    btn_start_main.setVisibility(View.GONE);
                }
            }

            /**
             *页面状态改变时回调
             * @param state 状态（拖动，静止，释放）
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        /**
         * 返回数据总个数
         *
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * @param container ViewPager
         * @param position  要创建页面的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加到容器
            container.addView(imageView);
            return imageView;
        }

        /**
         * @param view   当前创建的视图
         * @param object instantiateItem（）返回的结果
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 销毁页面
         *
         * @param container ViewPager
         * @param position  要销毁页面的位置
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}