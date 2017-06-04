package com.example.meri.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.meri.R;
import com.example.meri.bean.Image;

/**
 * Created by 磊 on 2017/4/24.
 */
public class PreviewImageActivity extends AppCompatActivity {
    private ImageView imageView;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_image);

        imageView = (ImageView) findViewById(R.id.image_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_collection);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        Glide.with(this)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        //toolbar收藏按钮监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                Image imagelike = new Image();
                if (menuItemId == R.id.action_collection) {
                    if (!item.isChecked()) {
                        item.setIcon(R.drawable.collection_true);
                        String url = getIntent().getStringExtra("URL");
                        imagelike.setUrl(url);
                        imagelike.save();
                        if (imagelike.save()) {
                            Toast.makeText(PreviewImageActivity.this, "存储成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PreviewImageActivity.this, "存储失败", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(PreviewImageActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                    }else{
                        item.setIcon(R.drawable.collection_false);
                        imagelike.delete();
                        Toast.makeText(PreviewImageActivity.this, "取消收藏 ", Toast.LENGTH_SHORT).show();
                        item.setChecked(false);
                    }
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}