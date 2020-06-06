package com.example.big_work.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.big_work.R;
import com.example.big_work.adapter.ArticleAdapter;
import com.example.big_work.httpRequest.IpAddress;
import com.example.big_work.info.ArticleJson;
import com.example.big_work.pageActivity.ArticleActivity;
import com.example.big_work.pageActivity.MainActivity;
import com.example.big_work.pageActivity.SearchResultActivity;
import com.example.big_work.parse.ArticleJsonParse;
import com.example.big_work.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class newsFragment extends Fragment implements View.OnClickListener{

    SliderLayout sliderShow;

    TextSliderView textSliderView;
    TextSliderView textSliderView1;
    TextSliderView textSliderView2;

    View view;
    EditText searchText;
    ImageButton search_button;
    ListView articleList;
    Handler handler;
    static List<ArticleJson> articleJsons=new ArrayList<>();
    final static int SHOW_ARTICLES=0x11;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        textSliderView = new TextSliderView(getContext());
        textSliderView
                .description("江铃汽车：收到政府扶持金1.68亿元")
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .image(R.mipmap.jmc);

        textSliderView1 = new TextSliderView(getContext());
        textSliderView1
                .description("云顶之弈：新羁绊\"月光\"")
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .image(R.mipmap.yunding);

        textSliderView2 = new TextSliderView(getContext());
        textSliderView2
                .description("官宣！赣州高铁公交快线12月23号开通")
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .image(R.mipmap.gaotie);

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        view=inflater.inflate(R.layout.new_buton,container,false);
        initView();

        search_button.setOnClickListener(this);

        showAll();

        slider_add();




        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ArticleActivity.class);
                ArticleJson aj=articleJsons.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("article",aj);
                intent.putExtra("request","homepage");
                intent.putExtras(bundle);
                startActivityForResult(intent,1);  //从主页进入新闻详情 code为1
            }
        });

        return  view;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.searchButton:
            {
                String search_word=searchText.getText().toString();
                Intent intent=new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra("request","Search");
                intent.putExtra("word",search_word);
                startActivityForResult(intent,2); //从主页进入搜索结果页面 code为2


            }
        }
    }
    private void initView()
    {

        searchText = view.findViewById(R.id.edit_text);
        search_button=(ImageButton) view.findViewById(R.id.searchButton);
        articleList=view.findViewById(R.id.article_listView);
        sliderShow=view.findViewById(R.id.slider);
    }

    public void showAll()
    {
        /********通过handleMessage将消息传递到主线程******/
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                if(msg.what==SHOW_ARTICLES)
                {
                    articleJsons=(List<ArticleJson>)msg.obj;
                    ArticleAdapter articleAdapter=new ArticleAdapter(getActivity(),articleJsons);
                    articleList.setAdapter(articleAdapter);
                }
            }
        };

//************开启子线程发起网络请求

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUtil.sendOkHttpRequest(IpAddress.ipAdress+":8080//android/ArticleServlet", new okhttp3.Callback() {
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

    private void slider_add()
    {

        sliderShow.addSlider(textSliderView);


        sliderShow.addSlider(textSliderView1);


        sliderShow.addSlider(textSliderView2);




    }

    @Override
    public void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }
}
