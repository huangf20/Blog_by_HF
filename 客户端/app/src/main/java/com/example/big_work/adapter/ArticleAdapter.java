package com.example.big_work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.big_work.R;
import com.example.big_work.info.ArticleJson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends BaseAdapter {
    List<ArticleJson> articleJsonList ;
    Context context;
    ImageView imageView;
    TextView article;
    TextView date;
    TextView comment;


    public ArticleAdapter(Context context,List<ArticleJson> articleJsonList)
    {
        this.articleJsonList=articleJsonList;
        this.context=context;
    }


    @Override
    public int getCount() {
        return articleJsonList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleJsonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.article_items,parent,false);
        }

        imageView=convertView.findViewById(R.id.icon_article);
        article=convertView.findViewById(R.id.tv_title);
        date=convertView.findViewById(R.id.tv_date);
        comment=convertView.findViewById(R.id.tv_comment);


        article.setText(articleJsonList.get(position).getTitle());
        date.setText(articleJsonList.get(position).getDate());
        comment.setText("评论："+articleJsonList.get(position).getComment_count());
        Picasso.with(context).load(articleJsonList.get(position).getIamge()).into(imageView);

        return convertView;
    }
}
