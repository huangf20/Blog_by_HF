package com.example.big_work.pageActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.big_work.R;
import com.example.big_work.httpRequest.IpAddress;
import com.example.big_work.info.User;
import com.example.big_work.parse.UserJsonParse;
import com.example.big_work.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    Button login, register;
    EditText name;
    EditText password;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String address;
    Handler handler;
    static List<User> users = new ArrayList<>();
    final static int SHOW_USER = 0x13;
    CheckBox remember,auto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        init();
        auto.setChecked(true);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = IpAddress.ipAdress+":8080//android/UserServlet?name=" + name.getText().toString() + "&password=" + password.getText().toString();
                System.out.println(address);
                getUser();
                Toast.makeText(LoginActivity.this,"正在登录",Toast.LENGTH_SHORT).show();

                //网络申请是放在子线程  所以需要延时等待网络加载完成
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        judge();            //判断
                    }


                },1500);



            }
        });


    }




    private void init() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        remember=findViewById(R.id.remember);
        auto=findViewById(R.id.auto);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        editor = sp.edit();
        if(sp.getString("remember",null)!=null)
        {
            remember.setChecked(true);
            name.setText(sp.getString("remember_name",null));
            password.setText(sp.getString("remember_password",null));
        }
        else
        {
            remember.setChecked(false);
        }


    }

    private void getUser() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==SHOW_USER)
                {
                    users=(List<User>) msg.obj;


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

    private void judge()
    {
        if(users.size()==0)
        {
            Toast.makeText(LoginActivity.this,"密码错误或用户名不存在！",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=getIntent();
            editor.putString("isLogin","YES");
            editor.commit();
            User user=users.get(0);

            editor.putString("name",user.getName());
            editor.commit();
            editor.putString("password",user.getPassword());
            editor.commit();
            editor.putString("date",user.getUser_register_time());
            editor.commit();
            editor.putInt("id", user.getId());
            editor.commit();
            editor.putString("image",user.getUser_image());
            editor.commit();

            if(remember.isChecked())
            {
                editor.putString("remember","YES");
                editor.commit();
                editor.putString("remember_name",name.getText().toString());
                editor.commit();
                editor.putString("remember_password",password.getText().toString());
                editor.commit();
            }
            else
            {
                editor.remove("remember");
                editor.commit();
                editor.remove("remember_name");
                editor.commit();
                editor.remove("remember_password");
                editor.commit();
            }

            setResult(5);
            finish();
        }
    }
}
