package com.example.meri.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.example.meri.R;
import com.example.meri.base.BaseFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by 磊 on 2017/3/22.
 */

public class ContentFragment extends BaseFragment {

    //利用xUtils3初始化控件
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

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
        //设置默认选中为新闻
        radioGroup.check(R.id.rd_news);
    }
}
