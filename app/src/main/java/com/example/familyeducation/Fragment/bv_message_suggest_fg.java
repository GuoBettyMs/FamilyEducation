package com.example.familyeducation.Fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.familyeducation.Bean.IndexGridviewData;
import com.example.familyeducation.R;

public class bv_message_suggest_fg extends Fragment {

    public static bv_message_suggest_fg newInstance() {
        return new bv_message_suggest_fg();
    }

    //初始化Fragment的视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bv_mes_order_suggest,container,false);
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
        nav_title.setText("我的建议");

        ArrayAdapter<String> subA = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                IndexGridviewData.subArray){
            @Override
            public View getView(int position, View convertView, ViewGroup parent ){
                TextView textview = (TextView)super.getView(position,convertView,parent);
                textview.setGravity(Gravity.CENTER);
                return textview;
            }
        };
        subA.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        Spinner suggest_sub = view.findViewById(R.id.suggest_sub);
        suggest_sub.setPrompt("科目");
        suggest_sub.setAdapter(subA);
        suggest_sub.setSelection(0);
        suggest_sub.setOnItemSelectedListener(new bv_message_suggest_fg.SubSelectedListener());

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(view1 -> {
            // 获取 fragmentManager
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            // 将当前栈弹出
            fragmentManager.popBackStack();
        });
    }


    class SubSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getContext(),IndexGridviewData.subArray[position],Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}
