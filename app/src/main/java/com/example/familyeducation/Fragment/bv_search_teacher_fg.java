package com.example.familyeducation.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;

public class bv_search_teacher_fg extends Fragment implements View.OnClickListener {

    TextView tea_name,tea_sex,tea_phone,tea_sub,tea_grade;
    Button orderBtn;
    private String teaname_fr,parPhone_fr,sub_fr,grade_fr;
    UserDBHelper mHelper = null;
    SQLiteDatabase sql;

    public static bv_search_teacher_fg newInstance() {
        return new bv_search_teacher_fg();
    }

    //初始化与Fragment视图无关的变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //初始化Fragment的视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bv_search_teacherinfor,container,false);
        // 在本Fragment 中 消费点击事件，防止在子fragment能点击父fragment内容
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return view;
    }

    //初始化视图内各个控件
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nav_title = view.findViewById(R.id.nav_title);
        ImageView back = view.findViewById(R.id.back);
        tea_name = view.findViewById(R.id.tea_name);
        tea_sex = view.findViewById(R.id.tea_sex);
        tea_phone = view.findViewById(R.id.tea_phone);
        tea_sub = view.findViewById(R.id.tea_sub);
        tea_grade = view.findViewById(R.id.tea_grade);
        orderBtn = view.findViewById(R.id.orderBtn);

        nav_title.setText("教师个人信息");
        orderBtn.setOnClickListener(this);
        back.setOnClickListener(view1 -> {
            // 获取 fragmentManager
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // 将当前栈弹出
            fragmentManager.popBackStack();
        });

        //获得数据库帮助器的唯一实例，实例版本为1
        mHelper = UserDBHelper.getInstance(getActivity(), 1);

        // 从父fragment获取 teachername
        getParentFragmentManager().setFragmentResultListener(
                "key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                teaname_fr = bundle.getString("teachername");
                parPhone_fr = bundle.getString("parentPhone");
                sub_fr = bundle.getString("currentsub");
                grade_fr = bundle.getString("currentgrade");

                tea_name.setText(teaname_fr);
                tea_sub.setText(sub_fr);
                tea_grade.setText(grade_fr);

                // 打开数据库帮助器的写连接
                sql = mHelper.openReadLink();
                mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

                //查询教师表，显示教师的基本信息
                ArrayList<UserInfo> teacherList = mHelper.queryAll(UserDBHelper.TABLE_teacher);
                for(int j=0;j<teacherList.size();j++){
//                    Log.i("kk TABLE_teacher","query---"+teacherList.get(j).toString(UserDBHelper.TABLE_teacher));
                    UserInfo info = teacherList.get(j);
                    if(info.tea_name.equals(teaname_fr)
                            && info.tea_sub.equals(sub_fr)
                            && info.tea_grade.equals(grade_fr)){
                        tea_sex.setText(info.tea_sex);
                        tea_phone.setText(info.tea_phone);
                    }
                }

                //查询预约表，如果用户号相同，教师名称相同，预约按钮设置为灰色
                ArrayList<UserInfo> reserveList = mHelper.queryAll(UserDBHelper.TABLE_reserve);
                for(int j=0;j<reserveList.size();j++){
                    UserInfo info = reserveList.get(j);
//                    Log.i("kk TABLE_reserve","query---"+reserveList.get(j).toString(UserDBHelper.TABLE_reserve));
                    if(info.reserve_parphone.equals(parPhone_fr)
                            && info.reserve_teaname.equals(teaname_fr)
                            && info.reserve_teasub.equals(sub_fr)
                            && info.reserve_teagrade.equals(grade_fr)){
                        orderBtn.setText("已预约");
                        orderBtn.setBackgroundColor(getResources().getColor(R.color.text_chechkbox));
                        orderBtn.setClickable(false);
                    }
                }

            }
        });

        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orderBtn:
                orderBtn.setText("已预约");
                orderBtn.setBackgroundColor(getResources().getColor(R.color.text_chechkbox));

                sql = mHelper.openWriteLink();
                mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

                UserInfo tea_info = new UserInfo();
                tea_info.reserve_teaname = teaname_fr;
                tea_info.reserve_parphone = parPhone_fr;
                tea_info.reserve_teaphone = tea_phone.getText().toString();
                tea_info.reserve_teasub = sub_fr;
                tea_info.reserve_teagrade = grade_fr;
                mHelper.insert(UserDBHelper.TABLE_reserve,tea_info);
                mHelper.closeLink();

                Toast.makeText(getActivity(), "预约成功", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }
}
