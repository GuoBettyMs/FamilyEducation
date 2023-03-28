package com.example.familyeducation.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.familyeducation.Adapter.Listview_BaseA;
import com.example.familyeducation.Adapter.RewardListview_BaseA;
import com.example.familyeducation.Bean.IndexGridviewData;
import com.example.familyeducation.Bean.IndexGridviewIcon;
import com.example.familyeducation.Bean.RewardBean;
import com.example.familyeducation.Bean.UserInfo;
import com.example.familyeducation.R;
import com.example.familyeducation.SQLiteDB.UserDBHelper;

import java.util.ArrayList;
import java.util.List;

public class bv_user_wallet_fg extends Fragment {

    public static bv_user_wallet_fg newInstance() {
        return new bv_user_wallet_fg();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bv_user_wallet,container,false);
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
        super.onViewCreated(view, savedInstanceState);

        TextView nav_title = view.findViewById(R.id.nav_title);
        ListView user_wallet_listV = view.findViewById(R.id.user_wallet_listV);
        ImageView back = view.findViewById(R.id.back);

        nav_title.setText("我的钱包");
        back.setOnClickListener(view1 -> {
            // 获取 fragmentManager
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // 将当前栈弹出
            fragmentManager.popBackStack();
        });

        //从父fragment获取 par_phone
        getParentFragmentManager().setFragmentResultListener(
                "key", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        String par_phone = bundle.getString("par_phone");

                        UserDBHelper mHelper = UserDBHelper.getInstance(getActivity(), 1);
                        SQLiteDatabase sql = mHelper.openReadLink();
                        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

                        UserInfo info = mHelper.queryByPhone(UserDBHelper.TABLE_money, par_phone);
                        List<IndexGridviewIcon> list = IndexGridviewData.getuserListDatas(3);

                        if (info != null) {
                            list.get(0).setiName("我的余额："+info.money_yue);
                            list.get(1).setiName("我的积分："+info.money_jifen);
                        }
                        Listview_BaseA adapter = new Listview_BaseA(list);
                        user_wallet_listV.setAdapter(adapter);
                        mHelper.closeLink();
                    }
                });
    }
}
