package com.example.meri.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.adapter.RecyclerViewAdapter;
import com.example.meri.base.BaseFragment;
import com.example.meri.bean.PicBean;
import com.example.meri.utils.HttpUtils;
import com.example.meri.utils.LogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.meri.api.ApiConstants.PIC_IMAGE;

/**
 * Created by 磊 on 2017/4/8.
 */

public class PicFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private List<PicBean.ResultsBean> picList = new ArrayList<>();
    //图片的高度
    private List<Integer> heights = new ArrayList<>();

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.content_pic,container,false);
//        return view;
//    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_pic, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));

//        //加载更多
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(isScrollToEnd(mReyclerView)){
//                    Log.e("tag","============scroll to end");
//                    index += 1;
//                    loadApi(index);
//                }
//            }
//        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //联网请求数据
        getDate();

    }

    private void getDate() {
        HttpUtils.sendOkHttpRequest(PIC_IMAGE, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(context, "联网请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LogUtils.e("联网请求图片成功==" + response.body().toString());
                        final String result = response.body().string();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Gson解析Json数据
                                Gson gson = new Gson();
                                PicBean picBean = gson.fromJson(result, PicBean.class);
                                picList = picBean.getResults();

//                                //设置图片随机高度
//                                Random random = new Random();
//                                for (int i = 0; i < picList.size(); i++) {
//                                    //集合中存储每个回调图片对应的随机高度
//                                    heights.add(random.nextInt(200) + 200);
//                                }

                                //设置适配器
                                adapter = new RecyclerViewAdapter(context, picList, heights);
                                recyclerView.setAdapter(adapter);
                                //刷新适配器
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                }

        );
    }
}
