package com.example.familyeducation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_password_first,et_password_second,et_verifycode; // 声明一个编辑框对象
    private String mVerifyCode,mPhone; // 验证码

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_forget);
        // 从布局文件中获取名叫et_password_first的编辑框
        et_password_first = findViewById(R.id.et_password_first);
        // 从布局文件中获取名叫et_password_second的编辑框
        et_password_second = findViewById(R.id.et_password_second);
        // 从布局文件中获取名叫et_verifycode的编辑框
        et_verifycode = findViewById(R.id.et_verifycode);
        findViewById(R.id.btn_verifycode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        // 从前一个页面获取要修改密码的手机号码
        mPhone = getIntent().getStringExtra("par_phone");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verifycode:
                if (mPhone == null) {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 生成六位随机数字的验证码
                mVerifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
                // 弹出提醒对话框，提示用户六位验证码数字
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住验证码");
                builder.setMessage("手机号" + mPhone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                builder.setPositiveButton("好的", null);
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.btn_confirm:
                String password_first = et_password_first.getText().toString();
                String password_second = et_password_second.getText().toString();
//                if (password_first.length() < 6 || password_second.length() < 6) {
//                    Toast.makeText(this, "请输入正确的新密码", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (!password_first.equals(password_second)) {
                    Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!et_verifycode.getText().toString().equals(mVerifyCode)) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                } else {
                    //获得数据库帮助器的唯一实例，实例版本为1
                    UserDBHelper mHelper = UserDBHelper.getInstance(this, 1);
                    // 打开数据库帮助器的写连接
                    SQLiteDatabase sql = mHelper.openWriteLink();
                    mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里
                    UserInfo userInfo = new UserInfo();
                    userInfo.par_password = password_first;
                    mHelper.updateKey(UserDBHelper.TABLE_NAME,
                            userInfo,"par_password",String.format("par_phone='%s'", mPhone));
                    mHelper.closeLink();

                    Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    // 把修改好的新密码返回给前一个页面
                    Intent intent = new Intent();
                    intent.putExtra("new_password", password_first);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            default:
                break;
        }
    }
}