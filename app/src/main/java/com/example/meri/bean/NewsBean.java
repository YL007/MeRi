package com.example.meri.bean;

import java.util.List;

/**
 * 新闻解析数据
 * Created by 磊 on 2017/4/9.
 */

public class NewsBean {

    /**
     * date : 20170409
     * stories : [{"images":["https://pic1.zhimg.com/v2-de1f0346bc1572ad2adddeb952fe2ce0.jpg"],"type":0,"id":9345293,"ga_prefix":"040914","title":"从上大学前没接触过电脑，我花了快 20 年修炼成技术大牛"},{"images":["https://pic3.zhimg.com/v2-7ea16cf5d0f4b0874ff977552a7a9666.jpg"],"type":0,"id":9343880,"ga_prefix":"040912","title":"大误 · 我为什么偏偏是个人呢？"},{"images":["https://pic1.zhimg.com/v2-f74c14ce416473062d41888ff0de3e34.jpg"],"type":0,"id":9345794,"ga_prefix":"040911","title":"去过印度的人都一定不会忘掉满大街的狗"},{"images":["https://pic3.zhimg.com/v2-094cdcafb84354b38a3a6a537b67b126.jpg"],"type":0,"id":9337922,"ga_prefix":"040910","title":"我知道你害怕结婚，还好，这个时代不婚也是一种选择"},{"images":["https://pic1.zhimg.com/v2-9ff3c0c19cbfc6d4a7376ac2a33faadc.jpg"],"type":0,"id":9342968,"ga_prefix":"040909","title":"新手开始学 Photoshop，送你 10 个小技巧"},{"images":["https://pic2.zhimg.com/v2-b77243df5ebdddc9c4326a6e20770d99.jpg"],"type":0,"id":9344819,"ga_prefix":"040908","title":"红肉致癌的说法，罪魁祸首是「铁」吗？"},{"images":["https://pic2.zhimg.com/v2-4c5dcf771714442f88d73759f76a9529.jpg"],"type":0,"id":9345880,"ga_prefix":"040907","title":"绘本是最适合孩子阅读的图书形式，那用来学英语吧"},{"images":["https://pic4.zhimg.com/v2-8c2226e6c7bc39648b71c6a7f3af7337.jpg"],"type":0,"id":9343211,"ga_prefix":"040907","title":"这个药好难吃啊，我能不能切碎或者就着果汁喝下去？"},{"images":["https://pic1.zhimg.com/v2-634508c865e4c38f2536d7fca9266758.jpg"],"type":0,"id":9343230,"ga_prefix":"040907","title":"刘看山的人类学研究笔记 ·\r\n 今天你暴躁了吗？"},{"images":["https://pic4.zhimg.com/v2-f3a4b16d8885ae3b3cd0e988a69c2023.jpg"],"type":0,"id":9343761,"ga_prefix":"040906","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic1.zhimg.com/v2-692fa246e3829526303a76b67f27ef34.jpg","type":0,"id":9344819,"ga_prefix":"040908","title":"红肉致癌的说法，罪魁祸首是「铁」吗？"},{"image":"https://pic1.zhimg.com/v2-9521affad7e92974727dde7886604788.jpg","type":0,"id":9343230,"ga_prefix":"040907","title":"刘看山的人类学研究笔记 ·\r\n 今天你暴躁了吗？"},{"image":"https://pic3.zhimg.com/v2-76472a10bf97dca63606d7de6c88bb2e.jpg","type":0,"id":9343132,"ga_prefix":"040808","title":"春天尝起来是什么味道？"},{"image":"https://pic3.zhimg.com/v2-bd566b7fe9007e9a35eb5917aaf026b2.jpg","type":0,"id":9343158,"ga_prefix":"040807","title":"刚刚开始健身，应该怎么练胸肌？"},{"image":"https://pic4.zhimg.com/v2-fda51dbec567d07ceb46b81bc2d1ae13.jpg","type":0,"id":9342986,"ga_prefix":"040716","title":"没有黄易，「玄幻」「穿越」「网络小说」会大不一样"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * images : ["https://pic1.zhimg.com/v2-de1f0346bc1572ad2adddeb952fe2ce0.jpg"]
         * type : 0
         * id : 9345293
         * ga_prefix : 040914
         * title : 从上大学前没接触过电脑，我花了快 20 年修炼成技术大牛
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image : https://pic1.zhimg.com/v2-692fa246e3829526303a76b67f27ef34.jpg
         * type : 0
         * id : 9344819
         * ga_prefix : 040908
         * title : 红肉致癌的说法，罪魁祸首是「铁」吗？
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
