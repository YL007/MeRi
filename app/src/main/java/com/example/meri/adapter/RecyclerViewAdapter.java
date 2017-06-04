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

import java.util.List;
import java.util.Random;

/**
 * Created by 磊 on 2017/4/20.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static int SCREE_WIDTH = 0;
    private List<Integer> mHeights;
    private List<PicBean.ResultsBean> mPicList;
    private Context mContext;
    private ImageView imageView;


    //创建item监听接口
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        boolean onItemLongClick(View view , int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }




    public RecyclerViewAdapter(Context context, List<PicBean.ResultsBean> picList) {
        this.mContext = context;
        this.mPicList = picList;
        SCREE_WIDTH = mContext.getResources().getDisplayMetrics().widthPixels;
/*
        //设置随机高度
        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < mPicList.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }*/
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pic,parent,false);
        imageView = (ImageView) view.findViewById(R.id.recycler_image);
        imageView.getLayoutParams().height = (new Random().nextInt(300) + 400);
        imageView.getLayoutParams().width = SCREE_WIDTH / 2;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

       /* //通过itemview得到每个图片的pararms对象
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();

        //将高度修改为传入的随机高度
        params.height = mHeights.get(position);

        //设置修改参数
        holder.itemView.setLayoutParams(params);*/

        final String url = mPicList.get(position).getUrl();

        //Glide加载图片
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.pic_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

/*        //设置图片点击跳转
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,PreviewImageActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });*/

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }

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
