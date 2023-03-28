package com.example.familyeducation.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.familyeducation.Bean.IndexGridviewData;
import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;
import java.util.List;

public class bv_search_fg extends Fragment {

    private ListView listView;
    private String currentsub = "";
    private String currentgrade = "";
    private ArrayAdapter<String> adapter = null;
    private UserDBHelper mHelper = null;
    private SQLiteDatabase sql = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bv_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nav_title = view.findViewById(R.id.nav_title);
        Spinner sp_sub = view.findViewById(R.id.sp_sub);
        Spinner sp_grade = view.findViewById(R.id.sp_grade);
        nav_title.setText("查找教师");
        listView = view.findViewById(R.id.select_teacher);

        //数组适配器,simple_list_item_1 : 单独一行的文本框
        ArrayAdapter<String> subA = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                IndexGridviewData.subArray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent ){
                TextView textview = (TextView)super.getView(position,convertView,parent);
                textview.setGravity(Gravity.CENTER);
                return textview;
            }
        };
        ArrayAdapter<String> gradeA = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,
                IndexGridviewData.gradeArray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent ){
                TextView textview = (TextView)super.getView(position,convertView,parent);
                textview.setGravity(Gravity.CENTER);
                return textview;
            }
        };
        //下拉列表的布局样式
        subA.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        gradeA.setDropDownViewResource(android.R.layout.simple_list_item_checked);

        sp_sub.setPrompt("科目");
        sp_sub.setAdapter(subA);
        sp_sub.setSelection(0);
        sp_sub.setOnItemSelectedListener(new SubSelectedListener());

        sp_grade.setPrompt("年级");
        sp_grade.setAdapter(gradeA);
        sp_grade.setSelection(0);
        sp_grade.setOnItemSelectedListener(new GradeSelectedListener());

        listClick();
        //创建单例
        mHelper = UserDBHelper.getInstance(getActivity(), 1);
        //测试例子
        sql = mHelper.openWriteLink();
        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里
        UserInfo info = new UserInfo();
        info.tea_name = "Android";
        info.tea_grade = "一年级";
        info.tea_sub = "数学";
        info.tea_sex = "f";
        info.tea_phone = "11";

        UserInfo info1 = new UserInfo();
        info1.tea_name = "Java";
        info1.tea_grade = "二年级";
        info1.tea_sub = "语文";
        info1.tea_sex = "f";
        info1.tea_phone = "22";
        mHelper.insert(UserDBHelper.TABLE_teacher,info);
        mHelper.insert(UserDBHelper.TABLE_teacher,info1);
        mHelper.closeLink();
    }

    private void listClick(){
        //接收登录用户的手机号
        String phone = getActivity().getIntent().getStringExtra("login_phone");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String value = adapter.getItem(position);

                // 1：获取fragment 管理器
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("teachername",value);
                bundle.putString("parentPhone",phone);
                bundle.putString("currentsub",currentsub);
                bundle.putString("currentgrade",currentgrade);
                fragmentManager.setFragmentResult("key",bundle);//传送值到子frgment
                // 2：获取FragmentTransaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // 3：将跳转的fragment 添加到栈，这里使用 add
                fragmentTransaction.replace(R.id.bv_search, bv_search_teacher_fg.newInstance());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                // 4: 提交事务
                fragmentTransaction.commit();
            }
        });
    }

    class SubSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            List<String> names = new ArrayList<>();
            currentsub = IndexGridviewData.subArray[position];

            sql = mHelper.openReadLink();
            mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里
            ArrayList<UserInfo> teacherList = mHelper.queryAll(UserDBHelper.TABLE_teacher);
            for(int j=0;j<teacherList.size();j++){
//               Log.i("kk TABLE_teacher",""+teacherList.get(j).toString(UserDBHelper.TABLE_teacher));
                UserInfo info = teacherList.get(j);
//                if(info.tea_sub.equals(currentsub) && info.tea_grade.equals(currentgrade)){
//                    listItem = getResources().getStringArray(R.array.array_sub);
////                    listItem = getResources().getStringArray(R.array.array_grade);
////                    listItem = getResources().getStringArray(R.array.array_technology);
//                    adapter = new ArrayAdapter<String>(getContext(),
//                            android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
//                }
                if(info.tea_sub.equals(currentsub) && info.tea_grade.equals(currentgrade)){
                    names.add(info.tea_name);
                }
            }
            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);
            listView.setAdapter(adapter);
            mHelper.closeLink();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    class GradeSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            List<String> names = new ArrayList<>();
            currentgrade = IndexGridviewData.gradeArray[position];

            sql = mHelper.openReadLink();
            mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

            ArrayList<UserInfo> teacherList = mHelper.queryAll(UserDBHelper.TABLE_teacher);
            for(int j=0;j<teacherList.size();j++){
                UserInfo info = teacherList.get(j);
                if(info.tea_sub.equals(currentsub) && info.tea_grade.equals(currentgrade)){
                    names.add(info.tea_name);
                }
            }
            adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);
            listView.setAdapter(adapter);
            mHelper.closeLink();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
