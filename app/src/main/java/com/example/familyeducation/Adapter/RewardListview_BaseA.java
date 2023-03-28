package com.example.familyeducation.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.familyeducation.Bean.RewardBean;
import com.example.familyeducation.R;

import java.util.List;

public class RewardListview_BaseA extends BaseAdapter {

    private List<RewardBean> gridList;

    //创建一个构造方法
    public RewardListview_BaseA(List<RewardBean> mList) {
        this.gridList = mList;
    }

    @Override
    public int getCount() {
        return  gridList.size();
    }

    @Override
    public Object getItem(int position) {
        return gridList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RewardListview_BaseA.GridViewHolder viewHolder = null;

        if (convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.bv_user_reward_listitem,null);
            //创建viewHolder对象
            viewHolder = new RewardListview_BaseA.GridViewHolder();
            viewHolder.txt_amount = (TextView) convertView.findViewById(R.id.txt_amount);
            viewHolder.txt_duetime = (TextView) convertView.findViewById(R.id.txt_duetime);
            //让viewholder挂在convertview上面一起复用
            convertView.setTag(viewHolder);
        }else {
            //当convertView不为空时,吧viewholder取出来
            viewHolder = (RewardListview_BaseA.GridViewHolder) convertView.getTag();
        }

        viewHolder.txt_amount.setText(gridList.get(position).getiAmount());
        viewHolder.txt_duetime.setText(gridList.get(position).getiDuetime());
        return convertView;
    }

    //创建一个viewholder,用来复用对象
    class GridViewHolder{
        TextView txt_amount;
        TextView txt_duetime;
    }
}
