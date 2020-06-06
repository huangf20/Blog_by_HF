package com.example.big_work.pageActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.big_work.R;
import com.example.big_work.adapter.ArticleAdapter;
import com.example.big_work.fragment.addFragment;
import com.example.big_work.fragment.newsFragment;
import com.example.big_work.fragment.personFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    int previousId;                  ///////////设置一个变量，以防重复点击是会重复加载

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener//navigation选择器
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //searchText.setText(R.string.title_home);
                    if(R.id.navigation_home!=previousId)
                    {
                        previousId=R.id.navigation_home;
                        replaceFragment(new newsFragment());

                    }


                    return true;
                case R.id.navigation_dashboard:
                    if(R.id.navigation_dashboard!=previousId)
                    {
                        previousId=R.id.navigation_dashboard;
                        replaceFragment(new addFragment());
                    }

                    return true;
                case R.id.navigation_notifications:
                    if(R.id.navigation_notifications!=previousId)
                    {
                        previousId=R.id.navigation_notifications;
                        replaceFragment(new personFragment());
                    }

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new newsFragment());

        SharedPreferences sp=getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getString("isLogin",null)!=null)
        {
            Toast.makeText(this,"已登录",Toast.LENGTH_SHORT).show();
        }
        //initView();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);   //设置进入主页便选中导航栏主页按钮
        previousId=navView.getSelectedItemId();




    }


    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.flagment,fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("退出");
        builder.setMessage("你确定退出吗");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        });
        builder.show();
    }
}
