package com.example.big_work.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.big_work.R;
import com.example.big_work.httpRequest.IpAddress;
import com.example.big_work.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class addFragment extends Fragment implements View.OnClickListener {
    EditText title;
    EditText content;
    ImageButton add;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String islogin;
    String title_get;
    String content_get;
    String address;
    String userId;
    Handler handler;
    final static int ADD_OR_NOT = 0x14;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        view=inflater.inflate(R.layout.add_button,container,false);
        init();
        add.setOnClickListener(this);
        return  view;
    }

    private void init()
    {
        title=view.findViewById(R.id.editText_title);
        content=view.findViewById(R.id.editText_content);
        add=view.findViewById(R.id.imageButton_add);
        sp=getActivity().getSharedPreferences("login",getContext().MODE_PRIVATE);
        editor=sp.edit();
        islogin=sp.getString("isLogin",null);
        userId=sp.getInt("id",0)+"";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {


            case R.id.imageButton_add:
            {
                title_get = title.getText().toString();
                content_get=content.getText().toString();

                address= IpAddress.ipAdress+":8080//android/ArticleServlet?title="+title_get+"&content="+content_get+"&user_id="+userId;
                if(islogin==null)
                {
                    Toast.makeText(getContext(),"您还没有登录，先登录在进行操作吧！",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(content_get.length()>0&&title_get.length()>0)
                    {
                        addArticle();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"标题和内容不能为空！",Toast.LENGTH_LONG).show();

                    }
                }
            }
        }

    }


    private void addArticle()
    {
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                if(msg.what==ADD_OR_NOT)
                {
                    Toast.makeText(getContext(),(String)msg.obj,Toast.LENGTH_SHORT).show();
                }
            }
        };

        new Thread(new Runnable()
        {
            @Override
            public void run() {

                HttpUtil.sendOkHttpRequest(address, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String add_message=response.body().string();
                        Message msg=new Message();
                        msg.what=ADD_OR_NOT;
                        msg.obj=add_message;
                        handler.sendMessage(msg);
                    }
                });


            }
        }).start();

    }
}
