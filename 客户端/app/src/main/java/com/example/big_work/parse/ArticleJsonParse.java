package com.example.big_work.parse;

import com.example.big_work.info.ArticleJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ArticleJsonParse {
        public static List<ArticleJson> getFromJson(String Json)
        {
            List<ArticleJson> articleJsons=new ArrayList<>();
            Gson gson=new Gson();
            articleJsons=gson.fromJson(Json,new TypeToken<List<ArticleJson>>(){}.getType());
            return articleJsons;
    }
}
