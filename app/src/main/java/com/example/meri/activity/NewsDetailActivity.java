package com.example.meri.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.meri.R;

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

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webview = (WebView) findViewById(R.id.webview);

        //取代actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        //加载页面
        webview.loadUrl(url);
    }
}
