package com.example.familyeducation.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.familyeducation.Activity.BottomNavigation_Act;
import com.example.familyeducation.Adapter.Gridview_BaseA;
import com.example.familyeducation.Adapter.RewardListview_BaseA;
import com.example.familyeducation.Bean.IndexGridviewData;
import com.example.familyeducation.Bean.IndexGridviewIcon;
import com.example.familyeducation.Bean.RewardBean;
import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;
import java.util.List;

public class bv_user_order_fg extends Fragment {

    public static bv_user_order_fg newInstance() {
        return new bv_user_order_fg();
    }

    //初始化与Fragment视图无关的变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //初始化Fragment的视图
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.bv_user_order,container,false);
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
        GridView user_order_gridv = view.findViewById(R.id.user_order_title);
        GridView user_order_gridv1 = view.findViewById(R.id.user_order_content);

        nav_title.setText("我的订单");
        back.setOnClickListener(view1 -> {
            // 获取 fragmentManager
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // 将当前栈弹出
            fragmentManager.popBackStack();
        });

        List<IndexGridviewIcon> gridvItem = IndexGridviewData.getuserListDatas(5);
        Gridview_BaseA order_adapter = new Gridview_BaseA(gridvItem);
        user_order_gridv.setAdapter(order_adapter);

        // 从父fragment获取 par_phone
        getParentFragmentManager().setFragmentResultListener(
                "key", this, new FragmentResultListener() {
             @Override
             public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                 String par_phone = bundle.getString("par_phone");

                 UserDBHelper mHelper = UserDBHelper.getInstance(getActivity(), 1);
                 SQLiteDatabase sql = mHelper.openReadLink();
                 mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

                 ArrayList<UserInfo> infolist = mHelper.query(UserDBHelper.TABLE_order,String.format("par_order_phone='%s'",par_phone));
                 List<IndexGridviewIcon> gridvItem1 = new ArrayList<>();

                 if (infolist != null) {
                     for (int i = 0; i < infolist.size(); i++) {
                         UserInfo info = infolist.get(i);
                         String[] amount = new String[]{info.par_order_bookname+"/"+info.par_order_bookcount,
                                 info.par_order_bookprice, info.par_order_bookstate};
                         for (int j = 0; j < amount.length; j++){
                             IndexGridviewIcon bean = new IndexGridviewIcon();
                             bean.iName = amount[j];
                             gridvItem1.add(bean);
                         }
                     }
                 }
                 Gridview_BaseA money_adapter = new Gridview_BaseA(gridvItem1);
                 user_order_gridv1.setAdapter(money_adapter);
                 mHelper.closeLink();
             }
        });

    }
}
