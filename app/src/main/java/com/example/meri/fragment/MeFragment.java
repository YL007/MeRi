package com.example.meri.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meri.R;
import com.example.meri.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 磊 on 2017/4/8.
 */

public class MeFragment extends BaseFragment {
    private CircleImageView icon;
    private ImageView ivHead;
    private TextView tvText;
    private TextView tvText1;
    private TextView tvVersion;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_me,null);
        ivHead = (ImageView) view.findViewById(R.id.iv_head);
        tvText = (TextView) view.findViewById(R.id.text);
        tvText1 = (TextView) view.findViewById(R.id.text1);
        tvVersion = (TextView) view.findViewById(R.id.version);
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
