package com.example.meri.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.meri.R;
import com.example.meri.bean.PicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 磊 on 2017/4/20.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private RecyclerView recyclerView;
    private LayoutInflater mInflater;

    private List<Integer> mHeights;
    private List<PicBean.ResultsBean> mPicList = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter(Context context, List<PicBean.ResultsBean> picList, List<Integer> heights) {
        this.mContext = context;
        this.mPicList = picList;
        this.mHeights = heights;

        //设置随机高度
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mPicList.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.from(mContext).inflate(R.layout.item_pic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        //通过itemview得到每个图片的pararms对象
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();

        //将高度修改为传入的随机高度
        params.height = mHeights.get(position);

        //设置修改参数
        holder.itemView.setLayoutParams(params);

        String url = mPicList.get(position).getUrl();

        Glide.with(mContext)
                .load(url)
                .placeholder(R.drawable.pic_default)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.imageView);

//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(mContext,PreviewImageActivity.class);
//                intent.putExtra("url",PIC_IMAGE);
//                mContext.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mPicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recycler_image);
        }
    }
}
