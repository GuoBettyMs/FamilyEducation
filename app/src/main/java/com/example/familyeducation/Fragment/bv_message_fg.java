package com.example.familyeducation.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.familyeducation.Adapter.Listview_BaseA;
import com.example.familyeducation.Bean.IndexGridviewData;
import com.example.familyeducation.Bean.IndexGridviewIcon;
import com.example.familyeducation.R;

import java.util.List;

public class bv_message_fg extends Fragment{

    private Listview_BaseA adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bv_mes, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nav_title = view.findViewById(R.id.nav_title);
        ListView listView,mes_listV1,mes_listV2;
        listView = view.findViewById(R.id.mes_listV);
        mes_listV1 = view.findViewById(R.id.mes_listV1);
        mes_listV2 = view.findViewById(R.id.mes_listV2);
        nav_title.setText("消息通知");

        List<IndexGridviewIcon> listItem = IndexGridviewData.getmesListDatas(0);
        adapter = new Listview_BaseA(listItem);
        listView.setAdapter(adapter);

        //接收登录用户的手机号
        String phone = getActivity().getIntent().getStringExtra("login_phone");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch ((int) adapter.getItemId(position)){
                    case 0:
                        // 1：获取fragment 管理器
                        FragmentManager tea_fm = getActivity().getSupportFragmentManager();
                        //传送值的FragmentManager、FragmentTransaction要唯一，不能共用
                        Bundle bundle = new Bundle();
                        bundle.putString("login_phone",phone);
                        tea_fm.setFragmentResult("key",bundle);
                        // 2：获取FragmentTransaction
                        FragmentTransaction tea_ft = tea_fm.beginTransaction();
                        // 3：将跳转的fragment 添加到栈，这里使用 replace
                        tea_ft.replace(R.id.bv_mes, bv_message_teacher_fg.newInstance());
                        tea_ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        tea_ft.addToBackStack(null);
                        // 4: 提交事务，同一个FragmentTransaction只能使用commit方法提交一次
                        tea_ft.commit();
                        break;
                    case 1:
                        FragmentManager sug_fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction sug_ft = sug_fm.beginTransaction();
                        sug_ft.replace(R.id.bv_mes, bv_message_suggest_fg.newInstance());
                        sug_ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        sug_ft.addToBackStack(null);
                        sug_ft.commit();
                        break;
                    default:
                        break;
                }

            }
        });

        listItem = IndexGridviewData.getmesListDatas(1);
        adapter = new Listview_BaseA(listItem);
        mes_listV1.setAdapter(adapter);

        listItem = IndexGridviewData.getmesListDatas(2);
        adapter = new Listview_BaseA(listItem);
        mes_listV2.setAdapter(adapter);

    }
}
