package com.example.familyeducation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.familyeducation.Adapter.BottomBar_FragmentPA;
import com.example.familyeducation.Fragment.bv_index_fg;
import com.example.familyeducation.Fragment.bv_message_fg;
import com.example.familyeducation.Fragment.bv_search_fg;
import com.example.familyeducation.Fragment.bv_user_fg;
import com.example.familyeducation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigation_Act extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bv_bottomNavigation;
    private ViewPager vpager;
    private BottomBar_FragmentPA mAdapter;
    private List<Fragment> list;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bv_bottomNavigation = (BottomNavigationView) findViewById(R.id.bv_bottomNavigation);
        vpager = (ViewPager) findViewById(R.id.vpager);

        //把fragment添加到list
        list = new ArrayList<>();
        list.add(new bv_index_fg());
        list.add(new bv_search_fg());
        list.add(new bv_message_fg());
        list.add(new bv_user_fg());
        mAdapter = new BottomBar_FragmentPA(getSupportFragmentManager(),list);

        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(PAGE_ONE);
        vpager.addOnPageChangeListener(this);
        vpager.setOffscreenPageLimit(4); //限制只有4个view可以左右滑动

        bv_bottomNavigation.setOnItemSelectedListener(this);
        //默认选中底部导航栏中的第一个
        bv_bottomNavigation.getMenu().getItem(0).setChecked(true);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //ViewPager 左右滑动时，BottomNavigationView显示对应item
    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                bv_bottomNavigation.getMenu().getItem(0).setChecked(true);
                break;
            case 1:
                bv_bottomNavigation.getMenu().getItem(1).setChecked(true);
                break;
            case 2:
                bv_bottomNavigation.getMenu().getItem(2).setChecked(true);
                break;
            case 3:
                bv_bottomNavigation.getMenu().getItem(3).setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /***
     * ViewPager与BottomNavigationView的联动--点击
     * ***/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_index:
                //ViewPager 跳转到对应fragment
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.menu_search:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.menu_message:
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.menu_profile:
                vpager.setCurrentItem(PAGE_FOUR);
                break;
        }
        return true;
        //这里返回true，表示事件已经被处理。如果返回false，为了达到条目选中效果，还需要下面的代码
        // item.setChecked(true);  不论点击了哪一个，都手动设置为选中状态true（该控件并没有默认实现)
        // 。如果不设置，只有第一个menu展示的时候是选中状态，其他的即便被点击选中了，图标和文字也不会做任何更改
    }
}