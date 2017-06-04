package com.example.meri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.meri.R;
import com.example.meri.bean.News;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static com.example.meri.R.id.iv_newspic;

/**
 * Created by 磊 on 2017/5/8.
 */

public class NewsLikeAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private List<News> newsList = new ArrayList<>();
    private Context context;
    private int resourceId;

    private OnShowItemClickListener onShowItemClickListener;

    public NewsLikeAdapter(Context context, int textViewResourceId, List<News> list) {
        this.context = context;
        resourceId = textViewResourceId;
        newsList = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_newslist, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_newspic = (ImageView) convertView.findViewById(iv_newspic);
            viewHolder.tv_newscontent = (TextView) convertView.findViewById(R.id.tv_newscontent);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.listview_select_cb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final News newsBean = newsList.get(position);
        List<News> news = DataSupport.findAll(News.class);
        List<String> newsImage = newsBean.getImage();
        Glide.with(context)
                .load(newsImage.get(0))
                .centerCrop()
                .placeholder(R.mipmap.pic_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.iv_newspic);
        viewHolder.tv_newscontent.setText(news.get(position).getTitle());

        // 是否是多选状态
        if (newsBean.isShow()) {
            viewHolder.cb.setVisibility(View.VISIBLE);
        } else {
            viewHolder.cb.setVisibility(View.GONE);
        }

        viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    newsBean.setChecked(true);
                } else {
                    newsBean.setChecked(false);
                }
                // 回调方法，将Item加入已选
                onShowItemClickListener.onShowItemClick(newsBean);
            }
        });

        // 必须放在监听后面
        viewHolder.cb.setChecked(newsBean.isChecked());

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_newspic;
        TextView tv_newscontent;
        CheckBox cb;
    }

    public interface OnShowItemClickListener {
        void onShowItemClick(News bean);
    }

    public void setOnShowItemClickListener(OnShowItemClickListener onShowItemClickListener) {
        this.onShowItemClickListener = onShowItemClickListener;
    }
}



