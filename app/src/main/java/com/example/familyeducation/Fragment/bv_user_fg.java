package com.example.familyeducation.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.familyeducation.Adapter.Gridview_BaseA;
import com.example.familyeducation.Adapter.Listview_BaseA;
import com.example.familyeducation.Bean.IndexGridviewData;
import com.example.familyeducation.Bean.IndexGridviewIcon;
import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.List;

public class bv_user_fg extends Fragment implements AdapterView.OnItemClickListener {

    private List<IndexGridviewIcon> listItem;
    private Listview_BaseA adapter = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    String phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bv_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView tea_icon = view.findViewById(R.id.tea_icon);
        TextView tea_phone = view.findViewById(R.id.tea_phone);
        GridView user_moneyGridview = view.findViewById(R.id.user_money);
        ListView listView = view.findViewById(R.id.user_listV);
        ListView user_listV1 = view.findViewById(R.id.user_listV1);
        ListView user_listV2 = view.findViewById(R.id.user_listV2);
        user_moneyGridview.setOnItemClickListener(this);

        //接收登录用户的手机号
        phone = getActivity().getIntent().getStringExtra("login_phone");
        tea_phone.setText("手机号："+phone);
        tea_icon.setImageResource(R.drawable.face_without_mouth);

        listItem = IndexGridviewData.getuserListDatas(-1);
        Gridview_BaseA money_adapter = new Gridview_BaseA(listItem);
        user_moneyGridview.setAdapter(money_adapter);

        listItem = IndexGridviewData.getuserListDatas(0);
        adapter = new Listview_BaseA(listItem);
        listView.setAdapter(adapter);

        listItem = IndexGridviewData.getuserListDatas(1);
        adapter = new Listview_BaseA(listItem);
        user_listV1.setAdapter(adapter);

        listItem = IndexGridviewData.getuserListDatas(2);
        adapter = new Listview_BaseA(listItem);
        user_listV2.setAdapter(adapter);


        UserDBHelper mHelper = UserDBHelper.getInstance(getActivity(), 1);
        SQLiteDatabase sql = mHelper.openWriteLink();
        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里
        UserInfo tea_info = new UserInfo();
        tea_info.par_order_phone = "00";
        tea_info.par_order_bookname = "fffff";
        tea_info.par_order_bookprice = "11yuan";
        tea_info.par_order_bookcount = "1";
        tea_info.par_order_bookstate = "派送中";

//        mHelper.insert(UserDBHelper.TABLE_order,tea_info);
        mHelper.closeLink();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 1：获取fragment 管理器
        fragmentManager = getActivity().getSupportFragmentManager();
        //传送值到子frgment
        Bundle bundle = new Bundle();
        bundle.putString("par_phone",phone);
        fragmentManager.setFragmentResult("key",bundle);
        // 2：获取FragmentTransaction
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                // 3：将跳转的fragment 添加到栈，这里使用 replace
                fragmentTransaction.replace(R.id.bv_user, bv_user_order_fg.newInstance());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            case 1:
                fragmentTransaction.replace(R.id.bv_user, bv_user_wallet_fg.newInstance());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            case 2:
                fragmentTransaction.replace(R.id.bv_user, bv_user_reward_fg.newInstance());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                break;
            default:
                break;
        }
        fragmentTransaction.addToBackStack(null);
        // 4: 提交事务
        fragmentTransaction.commit();
    }
}
