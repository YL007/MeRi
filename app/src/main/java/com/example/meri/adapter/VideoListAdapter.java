package com.example.meri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.meri.R;
import com.example.meri.bean.VideoBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by 磊 on 2017/5/8.
 */

public class VideoListAdapter extends BaseAdapter {
    int[] videoIndexs = {0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0};
    Context context;
    LayoutInflater mInflater;
    private List<VideoBean.V9LG4B3A0Bean> mVideoList;

    public VideoListAdapter(Context context, List<VideoBean.V9LG4B3A0Bean> videoList, int item_video) {
        this.context = context;
        this.mVideoList = videoList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mVideoList.size();
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

        String url = mVideoList.get(position).getMp4_url();
        String title = mVideoList.get(position).getTitle();
        String image = mVideoList.get(position).getCover();

        //This is the point
       /* if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
            ((VideoHolder) convertView.getTag()).jcVideoPlayer.release();
        }

        if (videoIndexs[position] == 1) {
            VideoHolder viewHolder;
            if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
                viewHolder = (VideoHolder) convertView.getTag();
            } else {
                viewHolder = new VideoHolder();
                convertView = mInflater.inflate(R.layout.item_video, null);
                viewHolder.jcVideoPlayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.list_video);
                convertView.setTag(viewHolder);
            }

            viewHolder.jcVideoPlayer.setUp(
                    url, JCVideoPlayer.SCREEN_LAYOUT_LIST, title);
                Glide.with(context).load(image).into(viewHolder.jcVideoPlayer.thumbImageView);
        }
*/
        VideoHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_video, null);
            viewHolder = new VideoHolder();
            viewHolder.jcVideoPlayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.list_video);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (VideoHolder) convertView.getTag();
        }

        viewHolder.jcVideoPlayer.setUp(
                url, JCVideoPlayer.SCREEN_LAYOUT_LIST, title);
        Glide.with(context).load(image).into(viewHolder.jcVideoPlayer.thumbImageView);

        return convertView;
    }

    class VideoHolder {
        JCVideoPlayerStandard jcVideoPlayer;
    }
}
