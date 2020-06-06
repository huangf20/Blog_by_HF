package com.example.big_work.parse;

import com.example.big_work.info.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UserJsonParse {
    public static List<User> getFromJson(String Json)
    {
        List<User> users=new ArrayList<>();
        Gson gson=new Gson();
        users=gson.fromJson(Json,new TypeToken<List<User>>(){}.getType());
        return users;
    }
}
