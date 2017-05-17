package com.example.meri.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    List<News> news = new ArrayList<>();
    public Activity context;
    @Override
    public int getCount() {
        return news.size();
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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        List<News> news = DataSupport.findAll(News.class);
        viewHolder.iv_newspic.setImageURI((Uri) news.get(position).getImage());
        viewHolder.tv_newscontent.setText(news.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
    ImageView iv_newspic;
    TextView tv_newscontent;
    }
}



