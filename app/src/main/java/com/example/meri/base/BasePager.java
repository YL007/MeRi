package com.example.meri.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.meri.R;

/**
 * Created by 磊 on 2017/3/23.
 * ViewPager基类
 */

public class BasePager{
    //上下文，MainActivity
    public final Context context;
    //不同的页面
    public View rootView;
    //标题
    public TextView tv_title;
    //左侧菜单
    public ImageButton ib_menu;
    //加载子页面
    public FrameLayout fl_content;

    public BasePager(Context context){
        this.context = context;
        rootView = initView();
    }

    /**
     * 用于初始化公共部分的视图，并初始化加载子视图的Fragment
     * @return
     */
    private View initView() {
        View view = View.inflate(context,R.layout.base_pager,null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        return view;
    }

    /**
     * 初始化数据，当子视图需要初始化或绑定数据时，重写此方法
     */
    public void initData(){

    }
}
