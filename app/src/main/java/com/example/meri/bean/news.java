package com.example.meri.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 磊 on 2017/5/15.
 */

public class News extends DataSupport{
    private int id;
    private String title;
    private List<String> image;
    private String url;
    private boolean isShow; // 是否显示CheckBox
    private boolean isChecked; // 是否选中CheckBox
   /* private int CheckedTrue;    //选中为true的checkbox

    public int getCheckedTrue() {
        return CheckedTrue;
    }

    public void setCheckedTrue(int checkedTrue) {
        CheckedTrue = checkedTrue;
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}
