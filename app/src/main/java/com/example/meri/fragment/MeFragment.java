package com.example.meri.fragment;

import android.view.View;

import com.example.meri.R;
import com.example.meri.base.BaseFragment;

/**
 * Created by 磊 on 2017/4/8.
 */

public class MeFragment extends BaseFragment {

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_me,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.content_me,container,false);
//        return view;
//    }
//
//    @Override
//    public View initView() {
//        return null;
//    }
//
//    @Override
//    public void initData() {
//        super.initData();
//    }
}
