package com.example.big_work.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.big_work.R;
import com.example.big_work.pageActivity.LoginActivity;
import com.example.big_work.pageActivity.MainActivity;
import com.squareup.picasso.Picasso;

public class personFragment extends Fragment {
    View view;
    Button loginOrRegister;
    ImageView head;
    TextView userName;
    TextView userPassword;
    TextView registerDate;
    TextView userId;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String islogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        view=inflater.inflate(R.layout.person_button,container,false);
        initView();
        if(islogin!=null)
        {
            loginOrRegister.setText("注销");
            userName.setText(sp.getString("name",null));
            userPassword.setText(sp.getString("password",null));
            userId.setText(String.valueOf(sp.getInt("id",0)));
            registerDate.setText(sp.getString("date",null));
            Picasso.with(getContext()).load(sp.getString("image",null)).into(head);

        }
        else
        {
            loginOrRegister.setText("登录");
            userName.setText("");
            userPassword.setText("");
            userId.setText("");
            registerDate.setText("");
            head.setImageResource(R.drawable.head);
        }
        loginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(islogin==null)
                {
                    Intent intent=new Intent();
                    /************** 进入登录界面*/
                    intent.setClass(getActivity(),LoginActivity.class);
                    startActivityForResult(intent,5);// 从个人中心进入登录界面 code为5

                }
                else
                {
                    editor.remove("isLogin");
                    editor.commit();
                    editor.remove("name");
                    editor.commit();
                    editor.remove("password");
                    editor.commit();
                    editor.remove("image");
                    editor.commit();
                    editor.remove("id");
                    editor.commit();
                    editor.remove("date");
                    editor.commit();
                    Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    Toast.makeText(getContext(),"注销成功",Toast.LENGTH_SHORT).show();


                }

            }
        });

        return  view;
    }

    private void initView()
    {
        head=view.findViewById(R.id.headImage);
        userName=view.findViewById(R.id.personPageName);
        userPassword=view.findViewById(R.id.personPagePassword);
        registerDate=view.findViewById(R.id.personPageDate);
        userId=view.findViewById(R.id.personPageId);
        loginOrRegister=view.findViewById(R.id.loginOrRegister);
        sp=getActivity().getSharedPreferences("login",getContext().MODE_PRIVATE);
        editor=sp.edit();
        islogin=sp.getString("isLogin",null);



    }

    @Override
    public void onResume() {
        super.onResume();

        initView();

        if(islogin!=null)
        {
            loginOrRegister.setText("注销");
            userName.setText(sp.getString("name",null));
            userPassword.setText(sp.getString("password",null));
            userId.setText(String.valueOf(sp.getInt("id",0)));
            registerDate.setText(sp.getString("date",null));
            Picasso.with(getContext()).load(sp.getString("image",null)).into(head);
        }
        else
        {
            loginOrRegister.setText("登录");
            userName.setText("");
            userPassword.setText("");
            userId.setText("");
            registerDate.setText("");
            head.setImageResource(R.drawable.head);
        }






    }



    }
