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
