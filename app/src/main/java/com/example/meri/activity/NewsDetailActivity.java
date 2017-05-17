package com.example.meri.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.meri.R;
import com.example.meri.bean.News;

import java.util.List;

public class NewsDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webview;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initView();
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webview = (WebView) findViewById(R.id.webview);
        toolbar.inflateMenu(R.menu.menu_collection);//设置右上角的填充菜单

        //取代actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //toolbar收藏按钮监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                News newslike = new News();
                if (menuItemId == R.id.action_collection) {
                    if (!item.isChecked()) {
                        item.setIcon(R.drawable.collection_true);
                        String title = getIntent().getStringExtra("title");
                        List<String> image = (List) getIntent().getSerializableExtra("image");
                        newslike.setTitle(title);
                        newslike.setImage(image);
                        newslike.save();
                        Toast.makeText(NewsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                    }else{
                        item.setIcon(R.drawable.collection_false);
                        newslike.delete();
                        Toast.makeText(NewsDetailActivity.this, "取消收藏 ", Toast.LENGTH_SHORT).show();
                        item.setChecked(false);
                    }
                }
                return true;
            }
        });
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

    private void getData() {
        url = getIntent().getStringExtra("url");

        WebSettings websettings = webview.getSettings();
        websettings.setJavaScriptEnabled(true);
        //设置双击变大变小
        websettings.setUseWideViewPort(true);
        //设置缩放按钮
        websettings.setBuiltInZoomControls(true);
        //不让从当前网页跳转到系统的浏览器中
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        //加载页面
        webview.loadUrl(url);
    }
}
