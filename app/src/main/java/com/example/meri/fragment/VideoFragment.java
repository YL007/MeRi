package com.example.meri.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meri.R;
import com.example.meri.base.BaseFragment;

/**
 * Created by 磊 on 2017/4/8.
 */

public class VideoFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_video,container,false);
        return view;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
