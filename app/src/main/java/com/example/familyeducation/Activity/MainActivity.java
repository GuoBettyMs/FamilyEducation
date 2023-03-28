package com.example.familyeducation.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,View.OnFocusChangeListener {

    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象
    private SQLiteDatabase sql;
    private Boolean bRemember = true;
    private TextView loginTV,register_userTV,forget_paswdTV;
    private EditText login_numberphoneET,login_paswdET;
    private CheckBox reme_paswdCB;

    //一般onCreateView()用于初始化Fragment的视图，
    // onViewCreated()一般用于初始化视图内各个控件，
    // 而onCreate()用于初始化与Fragment视图无关的变量。
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login_numberphoneET = findViewById(R.id.login_numberphoneET);
        login_paswdET = findViewById(R.id.login_paswdET);
        reme_paswdCB = findViewById(R.id.reme_paswdCB);
        loginTV = findViewById(R.id.loginTV);
        register_userTV = findViewById(R.id.register_userTV);
        forget_paswdTV = findViewById(R.id.forget_paswdTV);

        login_paswdET.setOnFocusChangeListener(this);//焦点变化监听器
        loginTV.setOnClickListener(this);
        register_userTV.setOnClickListener(this);
        reme_paswdCB.setOnCheckedChangeListener(new CheckListener());

        //registerForActivityResulta方法只能在onCreate()中注册。onstart()之后就不能注册了
        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            // 从后一个页面携带参数返回当前页面时触发
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Log.d("kk", "onActivityResult: data = "
                            + result.getData().getStringExtra("new_password"));
                }
            }
        });
        forget_paswdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget = new Intent(MainActivity.this, LoginForgetActivity.class);
                // 携带手机号码跳转到找回密码页面
                forget.putExtra("par_phone", login_numberphoneET.getText().toString());
                launcher.launch(forget);
            }
        });
    }

    // 从修改密码页面返回登录页面，要清空密码的输入框
    @Override
    protected void onRestart() {
        login_paswdET.setText("");
        super.onRestart();
    }

    // 恢复页面，则打开数据库连接
    @Override
    protected void onResume() {
        super.onResume();
        // 获得用户数据库帮助器的一个实例
        mHelper = UserDBHelper.getInstance(this, 1);
        sql = mHelper.openWriteLink();
        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里
    }

    // 暂停页面，则关闭数据库连接
    @Override
    protected void onPause() {
        super.onPause();
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        String phone = login_numberphoneET.getText().toString();
        String paswd = login_paswdET.getText().toString();
        switch (v.getId()) {
            case R.id.loginTV:
                if (phone.length() <= 0 || paswd.length() <= 0) {
                    Toast.makeText(this, "号码密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserInfo info = mHelper.queryByPhone(UserDBHelper.TABLE_NAME,phone);
                if(info == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("号码错误");
                    builder.setMessage("请重新注册");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    if (info.par_phone != null
                            && paswd.equals(info.par_password)) {
                        Intent intent = new Intent(this, BottomNavigation_Act.class);
                        //将登录用户的手机号传到 BottomNavigation_Act 的个人信息页面
                        intent.putExtra("login_phone",phone);
                        startActivity(intent);
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        sql = mHelper.openReadLink();
                        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里
                        ArrayList<UserInfo> infoArrayList = mHelper.queryAll(UserDBHelper.TABLE_money);
                        for(int i=0;i<infoArrayList.size();i++){
                            UserInfo userInfo = infoArrayList.get(i);
                            Log.i("kk",""+userInfo.toString(UserDBHelper.TABLE_money));
                        }
                    }
                }

                break;
            case R.id.register_userTV:
                Intent intent1 = new Intent(this, Register_Act.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    // 定义是否记住密码的勾选监听器
    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.reme_paswdCB) {
                bRemember = isChecked;
            }
        }
    }

    //自动填充密码
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String phone = login_numberphoneET.getText().toString();
        //判断是否是密码编辑框发生焦点变化
        if(v.getId()==R.id.login_paswdET){
            //用户已输入手机号码，且密码框获得焦点
            if(phone.length()>0 && hasFocus && bRemember){
                UserInfo info = mHelper.queryByPhone(UserDBHelper.TABLE_NAME,phone);
                if(info != null){
                    login_paswdET.setText(info.par_password);
                }else{
                    Toast.makeText(this, "号码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}