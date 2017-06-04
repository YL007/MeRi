package com.example.meri.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.meri.R;
import com.example.meri.bean.Image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 磊 on 2017/5/25.
 */
public class RecyclerViewLikeAdapter extends RecyclerView.Adapter<RecyclerViewLikeAdapter.ViewHolder>{

    private List<Image> imageList;
    private Context mContext;
    private ImageView imageView;
    private static int SCREE_Hight = 0;
    private OnShowItemClickListener onShowItemClickListener;

    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();

    //创建item监听接口
    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private RecyclerViewAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(RecyclerViewAdapter.OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public RecyclerViewLikeAdapter(Context context, List<Image> picList) {
        this.mContext = context;
        this.imageList = picList;
        SCREE_Hight = mContext.getResources().getDisplayMetrics().heightPixels;
        initMap();
    }

    //初始化map集合,默认为不选中
    private void initMap() {
        for (int i = 0; i < imageList.size(); i++) {
            map.put(i, false);
        }
    }

    @Override
    public RecyclerViewLikeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_pic,parent,false);
        imageView = (ImageView) view.findViewById(R.id.recycler_image);
//        imageView.getLayoutParams().height = (new Random().nextInt(300) + 400);
        imageView.getLayoutParams().height = SCREE_Hight / 4;


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerViewLikeAdapter.ViewHolder holder, final int position) {

        final Image imageBean = imageList.get(position);
        String url = imageBean.getUrl();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.pic_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        final Integer tag=new Integer(position);//初始化一个Integer实例，其值为position
        holder.cb.setTag(tag);

        if (map.containsKey(tag)){
            holder.cb.setChecked(map.get(tag));
        }else {
            holder.cb.setChecked(false);//true or false 都可以，看实际需求
        }
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cb.isChecked()){
                    map.put(tag,true);
                }else {
                    map.put(tag,false);
                }
            }
        });

        holder.cb.setOnCheckedChangeListener(null);

        // 是否是多选状态
        if (imageBean.isShow()) {
            holder.cb.setVisibility(View.VISIBLE);
        } else {
            holder.cb.setVisibility(View.GONE);
        }
        /*holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用map集合保存
                map.put(position, isChecked);

                if (map.containsKey(tag)) {
                    imageBean.setChecked(true);
                } else {
                    imageBean.setChecked(false);
                }
                // 回调方法，将Item加入已选
                onShowItemClickListener.onShowItemClick(imageBean);
            }
        });*/

        //用setOnClickListener取代setOnCheckedChangeListener，避免复用导致勾选错乱问题
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (holder.cb.isChecked()){
                    map.put(tag,true);
                }else {
                    map.put(tag,false);
                }
                // 回调方法，将Item加入已选
                onShowItemClickListener.onShowItemClick(imageBean);
            }
        });

//        // 必须放在监听后面
        holder.cb.setChecked(imageBean.isChecked());


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
        return imageList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        CheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recycler_image);
            cb = (CheckBox) itemView.findViewById(R.id.listview_select_cb);
        }
    }

    public interface OnShowItemClickListener {
        void onShowItemClick(Image bean);
    }

    public void setOnShowItemClickListener(RecyclerViewLikeAdapter.OnShowItemClickListener onShowItemClickListener) {
        this.onShowItemClickListener = onShowItemClickListener;
    }
}
