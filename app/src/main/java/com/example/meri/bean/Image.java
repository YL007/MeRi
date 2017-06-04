package com.example.meri.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 磊 on 2017/5/25.
 */

public class Image extends DataSupport{
    private int id;
    private String url;
    private boolean isShow; // 是否显示CheckBox
    private boolean isChecked; // 是否选中CheckBox

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
