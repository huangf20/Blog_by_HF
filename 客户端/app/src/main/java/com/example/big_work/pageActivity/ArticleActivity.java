package com.example.big_work.pageActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.big_work.R;
import com.example.big_work.fragment.newsFragment;
import com.example.big_work.httpRequest.IpAddress;
import com.example.big_work.info.ArticleJson;
import com.example.big_work.info.User;
import com.example.big_work.parse.UserJsonParse;
import com.example.big_work.util.HttpUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ArticleActivity extends AppCompatActivity {

    ImageButton back;
    TextView title;
    TextView date;
    TextView comment;
    ImageView imageView;
    TextView content;
    Intent intent;
    Button delete;
    TextView Author;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String islogin;
    int Article_userId;
    int userId;
    ArticleJson article;

    Handler handler,handler1;
    static List<User> users = new ArrayList<>();
    final static int SHOW_USER = 0x13;
    final static int SHOW_DELETE = 0x14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detiail);
        init();
        intent=getIntent();
        final String request=intent.getStringExtra("request");

        Bundle bundle=intent.getExtras();
        article=(ArticleJson) bundle.getSerializable("article");

        Article_userId=article.getUser_id();
        if(islogin!=null)
        {
            if(userId==Article_userId)
            {
                delete.setVisibility(View.VISIBLE);
            }
            else
            {
                delete.setVisibility(View.INVISIBLE);
            }

        }
        else
        {
            delete.setVisibility(View.INVISIBLE);
        }
        title.setText(article.getTitle());


        //System.out.println(aticle.getTitle());
        getUser();//    获取用户名并显示
        date.setText(article.getDate());
        comment.setText("评论："+article.getComment_count());
        content.setText(article.getContent());
        Picasso.with(ArticleActivity.this).load(article.getIamge()).into(imageView);


        back.setOnClickListener(new View.OnClickListener() {                  //返回键按下的操作
            @Override
            public void onClick(View v) {
                switch(request){
                    case "homepage":
                    {
                        setResult(1,intent);
                        finish();
                        break;
                    }
                    case "searchpage":
                    {
                        setResult(3,intent);
                        finish();
                        break;
                    }

                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {          //删除键按下的操作
            @Override
            public void onClick(View v) {
                 AlertDialog.Builder builder=new AlertDialog.Builder(ArticleActivity.this);
                 builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         delete();
                     }
                 });
                 builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 });
                 builder.setTitle("提示");
                 builder.setMessage("确定要删除吗？");
                 builder.show();
            }
        });



    }

    private void init()
    {
        back=findViewById(R.id.imageButton_home);
        title=findViewById(R.id.article_title);
        date=findViewById(R.id.article_date);
        comment=findViewById(R.id.article_comment);
        imageView=findViewById(R.id.article_image);
        content=findViewById(R.id.article_content);
        delete=findViewById(R.id.delete);
        Author=findViewById(R.id.article_owner);
        sp=this.getSharedPreferences("login",MODE_PRIVATE);

        islogin=sp.getString("isLogin",null);
        userId=sp.getInt("id",0);
    }

    private void getUser()
    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==SHOW_USER)
                {
                    users=(List<User>) msg.obj;
                    Author.setText(users.get(0).getName());


                }

            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendOkHttpRequest(IpAddress.ipAdress+":8080//android/UserServlet?userId="+article.getUser_id(), new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String json=response.body().string();
                        List<User> userList= UserJsonParse.getFromJson(json);
                        Message msg=new Message();
                        msg.what=SHOW_USER;
                        msg.obj=userList;
                        handler.sendMessage(msg);

                    }
                });

            }
        }).start();
    }

    private void delete()
    {
        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==SHOW_DELETE)
                {
                    Toast.makeText(ArticleActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    setResult(1,intent);
                    finish();

                }

            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendOkHttpRequest(IpAddress.ipAdress + ":8080//android/ArticleServlet?article_id="+article.getId(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String deleteMessage=response.body().string();
                        Message msg=new Message();
                        msg.what=SHOW_DELETE;
                        msg.obj=deleteMessage;
                        handler1.sendMessage(msg);
                    }
                });
            }
        }).start();
    }
}
