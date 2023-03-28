package com.example.familyeducation.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.familyeducation.Adapter.Banner_RecycleViewA;
import com.example.familyeducation.Adapter.Gridview_BaseA;
import com.example.familyeducation.Bean.IndexGridviewData;
import com.example.familyeducation.R;
import com.to.aboomy.pager2banner.Banner;
import com.to.aboomy.pager2banner.IndicatorView;

public class bv_index_fg extends Fragment {

    private Context mContext;
    private ViewFlipper vFlipper;//滚动头条
    private GridView gridView;//课程图标和文字
    private Banner banner;//优秀教师图片滚动
    private Gridview_BaseA gridviewA = null;
    private Banner_RecycleViewA bannerA = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.bv_index, container, false);
        return view;
    }

    //一般onCreateView()用于初始化Fragment的视图，
    // onViewCreated()一般用于初始化视图内各个控件，
    // 而onCreate()用于初始化与Fragment视图无关的变量。
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.index_gridview);
        vFlipper = view.findViewById(R.id.marquee_view);
        banner = view.findViewById(R.id.banner);
        initData();
    }

    private void initData() {

        gridviewA = new Gridview_BaseA(IndexGridviewData.getclassDatas());
        gridView.setAdapter(gridviewA);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
            }
        });

        vFlipper.addView(View.inflate(getContext(),R.layout.bv_index_viewflipper_item,null));
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv = getActivity().findViewById(R.id.scroll_mes);
        tv.setText("特价优惠课程来啦！！！");
        vFlipper.addView(View.inflate(getContext(),R.layout.bv_index_viewflipper_item,null));

        bannerA = new Banner_RecycleViewA(getContext(),IndexGridviewData.getteacherDatas());
        //使用内置Indicator
        IndicatorView indicator = new IndicatorView(getContext())
                .setIndicatorColor(getResources().getColor(R.color.bg_pageview))
                .setIndicatorSelectorColor(getResources().getColor(R.color.text_login));
        //传入RecyclerView.Adapter 即可实现无限轮播
        banner.setIndicator(indicator)
                .setAdapter(bannerA);

    }

}
