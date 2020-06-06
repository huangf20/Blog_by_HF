package com.example.big_work.info;

import java.io.Serializable;

public class ArticleJson implements Serializable {
    private int id;
    private int user_id;
    private String title;
    private String content;
    private int comment_count;
    private String date;
    private String image;
    public ArticleJson()
    {

    }
    public ArticleJson(int id, int user_id, String title, String content, int comment_count, String date, String iamge) {
        super();
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.comment_count = comment_count;
        this.date = date;
        this.image = iamge;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getComment_count() {
        return comment_count;
    }
    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getIamge() {
        return image;
    }
    public void setIamge(String image) {
        this.image = image;
    }
}
