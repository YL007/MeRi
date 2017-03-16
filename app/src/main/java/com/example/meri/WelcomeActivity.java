package com.example.meri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.meri.activity.GuildActivity;
import com.example.meri.activity.MainActivity;
import com.example.meri.utils.CacheUtils;

public class WelcomeActivity extends AppCompatActivity {
    //静态常量
    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        rl_welcome = (RelativeLayout) findViewById(R.id.rl_welcome);

        //欢迎页播放动画，渐变、缩放、旋转
        AlphaAnimation aa = new AlphaAnimation(0,1);
        ScaleAnimation sa = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        //添加动画
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.addAnimation(ra);
        set.setDuration(2000);
        set.setFillAfter(true);
        //开启动画
        rl_welcome.startAnimation(set);
        //设置动画监听
        set.setAnimationListener(new MyAnimationListener());

    }

    class MyAnimationListener implements Animation.AnimationListener{

        //动画开始播放
        @Override
        public void onAnimationStart(Animation animation) {

        }
        //动画播放结束
        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent;
            //判断是否进入主界面
            boolean isStartMain = CacheUtils.getBoolean(WelcomeActivity.this,START_MAIN);
            if (isStartMain){
                //非第一次，直接进入主界面
                intent = new Intent(WelcomeActivity.this,MainActivity.class);
            }else{
                //第一次，进入引导界面
                intent = new Intent(WelcomeActivity.this,GuildActivity.class);
            }
            startActivity(intent);
            //关闭欢迎页面
            finish();
        }
        //动画重复播放
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
