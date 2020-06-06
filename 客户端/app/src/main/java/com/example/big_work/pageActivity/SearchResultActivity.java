package com.example.big_work.pageActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.big_work.R;
import com.example.big_work.adapter.ArticleAdapter;
import com.example.big_work.httpRequest.IpAddress;
import com.example.big_work.info.ArticleJson;
import com.example.big_work.parse.ArticleJsonParse;
import com.example.big_work.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener{

    Handler handler;
    static List<ArticleJson> articleJsons=new ArrayList<>();
    final static int SHOW_ARTICLES=0x12;
    ImageButton searchPageToHome;
    TextView textViewResult;
    ListView searchResultList;
    Intent intent;
    String searchword;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        init();
        searchPageToHome.setOnClickListener(this);
        //System.out.println(searchword);
        textViewResult.setText("搜索中");
        Toast.makeText(SearchResultActivity.this,"正在搜索",Toast.LENGTH_SHORT).show();
        //网络申请是放在子线程  所以需要延时等待网络加载完成

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                  show();          //判断
            }


        },2000);


        searchResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SearchResultActivity.this,ArticleActivity.class);
                ArticleJson articleJson=articleJsons.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("article",articleJson);
                intent.putExtra("request","searchpage");
                intent.putExtras(bundle);
                startActivityForResult(intent,3); ///从搜索页面进入新闻详情 code为3
            }
        });






    }


    private void init()
    {
        searchPageToHome=findViewById(R.id.searchPageButton_home);
        searchResultList=findViewById(R.id.searchPage_listView);
        textViewResult=findViewById(R.id.textViewResult);
        intent=getIntent();
        searchword=intent.getStringExtra("word");
        address= IpAddress.ipAdress+":8080//android/ArticleServlet?word="+searchword;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.searchPageButton_home:
            {
                setResult(2);
                finish();
            }
        }
    }

    private void show()
    {
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                if(msg.what==SHOW_ARTICLES)
                {
                    articleJsons=(List<ArticleJson>)msg.obj;
                    ArticleAdapter articleAdapter=new ArticleAdapter(SearchResultActivity.this,articleJsons);
                    setSearchText();
                    searchResultList.setAdapter(articleAdapter);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendOkHttpRequest(address, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json=response.body().string();
                        List<ArticleJson> articleJsons= ArticleJsonParse.getFromJson(json);
                        Message msg=new Message();
                        msg.what=SHOW_ARTICLES;
                        msg.obj=articleJsons;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }

    private void setSearchText()
    {
        if(articleJsons.size()==0)
        {
            textViewResult.setTextColor(Color.RED);
            textViewResult.setText("搜索结果为空！");
        }
        else
        {
            textViewResult.setTextColor(Color.BLACK);
            textViewResult.setText("为您找到"+articleJsons.size()+"条结果");
        }
    }
}
