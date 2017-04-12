package com.example.meri.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 磊 on 2017/4/8.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> mFragment;

    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
