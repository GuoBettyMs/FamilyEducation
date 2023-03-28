package com.example.familyeducation.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.familyeducation.R;
import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;

public class Register_Act extends AppCompatActivity implements View.OnClickListener {

    private UserDBHelper mHelper; // 声明一个用户数据库的帮助器对象
    private SQLiteDatabase sql;
    private EditText register_numberphoneET,register_paswdET,register_encodeET;
    private Button registerBtn,encodeBtn;
    private String mVerifyCode; // 验证码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        register_numberphoneET = findViewById(R.id.register_numberphoneET);
        register_paswdET = findViewById(R.id.register_paswdET);
        register_encodeET = findViewById(R.id.register_encodeET);
        registerBtn = findViewById(R.id.registerBtn);
        encodeBtn = findViewById(R.id.encodeBtn);

        registerBtn.setOnClickListener(this);
        encodeBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获得数据库帮助器的唯一实例，实例版本为1
        mHelper = UserDBHelper.getInstance(this, 1);
        // 打开数据库帮助器的写连接
        sql = mHelper.openWriteLink();
        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerBtn:
                String numberphone = register_numberphoneET.getText().toString();
                String paswd = register_paswdET.getText().toString();

                if (numberphone.length() <= 0 || paswd.length() <= 0) {
                    Toast.makeText(this, "号码密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!register_encodeET.getText().toString().equals(mVerifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfo info1 = new UserInfo();
                    info1.par_phone = register_numberphoneET.getText().toString();
                    info1.par_password = register_paswdET.getText().toString();
                    mHelper.insert(UserDBHelper.TABLE_NAME,info1);
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.encodeBtn:
                // 生成六位随机数字的验证码
                mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                // 弹出提醒对话框，提示用户六位验证码数字
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("本次验证码是" + mVerifyCode + "，请输入验证码");
                builder.setPositiveButton("好的", null);
                AlertDialog alert = builder.create();
                alert.show();
                break;
            default:
                break;
        }
    }



}
