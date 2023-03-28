package com.example.familyeducation.Fragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import com.example.familyeducation.Adapter.Gridview_BaseA;
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

public class bv_user_reward_fg extends Fragment {

    public static bv_user_reward_fg newInstance() {
        return new bv_user_reward_fg();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bv_user_reward,container,false);
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
        ImageView back = view.findViewById(R.id.back);
        GridView user_reward_gridv = view.findViewById(R.id.user_reward_title);
        ListView user_reward_list = view.findViewById(R.id.user_reward_list);

        nav_title.setText("我的奖学券");
        back.setOnClickListener(view1 -> {
            // 获取 fragmentManager
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // 将当前栈弹出
            fragmentManager.popBackStack();
        });

        List<IndexGridviewIcon> gridvItem = IndexGridviewData.getuserListDatas(4);
        Gridview_BaseA money_adapter = new Gridview_BaseA(gridvItem);
        user_reward_gridv.setAdapter(money_adapter);

         //从父fragment获取 par_phone
        getParentFragmentManager().setFragmentResultListener(
                "key", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                        String par_phone = bundle.getString("par_phone");

                        UserDBHelper mHelper = UserDBHelper.getInstance(getActivity(), 1);
                        SQLiteDatabase sql = mHelper.openReadLink();
                        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

                        ArrayList<UserInfo> infolist = mHelper.query(UserDBHelper.TABLE_reward,String.format("reward_phone='%s'",par_phone));
                        List<RewardBean> listItem = new ArrayList<>();

                        if (infolist != null) {
                            for (int i = 0; i < infolist.size(); i++) {
                                UserInfo info = infolist.get(i);
                                RewardBean bean = new RewardBean();
                                bean.iAmount = info.reward_price;
                                bean.iDuetime = info.reward_date;
                                listItem.add(bean);
                            }
                        }
                        RewardListview_BaseA reward_adapter = new RewardListview_BaseA(listItem);
                        user_reward_list.setAdapter(reward_adapter);
                        mHelper.closeLink();
                    }
                });

    }

}
