package com.example.familyeducation.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;
import java.util.List;

public class bv_message_teacher_fg extends Fragment {

    public static bv_message_teacher_fg newInstance() {
        return new bv_message_teacher_fg();
    }

    //初始化Fragment的视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bv_mes_order_teach,container,false);
        // 在本Fragment 中 消费点击事件，防止在子fragment能点击父fragment内容
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView nav_title = view.findViewById(R.id.nav_title);
        ListView order_teach_list = view.findViewById(R.id.order_teach_list);
        ImageView back = view.findViewById(R.id.back);

        nav_title.setText("我预约的教师");
        back.setOnClickListener(view1 -> {
            // 获取 fragmentManager
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // 将当前栈弹出
            fragmentManager.popBackStack();
        });

        UserDBHelper mHelper = UserDBHelper.getInstance(getContext(), 1);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                List<String> names = new ArrayList<>();
                String par_phone = result.getString("login_phone");

                SQLiteDatabase sql = mHelper.openReadLink();
                mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

                ArrayList<UserInfo> reverseList = mHelper.queryAll(UserDBHelper.TABLE_reserve);
                for(int j=0;j<reverseList.size();j++){

                    UserInfo tea = reverseList.get(j);
                    Log.i("kk TABLE_reserve","query---"+reverseList.get(j).toString(UserDBHelper.TABLE_reserve));
                    if(tea.reserve_parphone.equals(par_phone)){
                        String s = tea.reserve_teaname+" -- "+tea.reserve_teasub+" -- "+reverseList.get(j).reserve_teagrade;
                        names.add(s);
                    }
//                    names.toArray();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, android.R.id.text1, names);
                    order_teach_list.setAdapter(adapter);
                }
            }
        });
        mHelper.closeLink();

    }

}
