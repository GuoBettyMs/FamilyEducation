package com.example.familyeducation.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.familyeducation.Bean.IndexGridviewIcon;
import com.example.familyeducation.R;

import java.util.List;

public class Listview_BaseA extends BaseAdapter {

    private List<IndexGridviewIcon> gridList;

    //创建一个构造方法
    public Listview_BaseA(List<IndexGridviewIcon> mList) {
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
        Listview_BaseA.ListViewHolder viewHolder = null;

        if (convertView==null){
            convertView = View.inflate(parent.getContext(), R.layout.list_item,null);
            //创建viewHolder对象
            viewHolder = new Listview_BaseA.ListViewHolder();
            viewHolder.list_imgV = (ImageView) convertView.findViewById(R.id.list_imgV);
            viewHolder.list_title = (TextView) convertView.findViewById(R.id.list_title);

            //让viewholder挂在convertview上面一起复用
            convertView.setTag(viewHolder);
        }else {
            //当convertView不为空时,吧viewholder取出来
            viewHolder = (Listview_BaseA.ListViewHolder) convertView.getTag();
        }
        viewHolder.list_imgV.setImageResource(gridList.get(position).getiId());
        viewHolder.list_title.setText(gridList.get(position).getiName());

        return convertView;
    }

    //创建一个viewholder,用来复用对象
    class ListViewHolder{
        ImageView list_imgV;
        TextView list_title;
    }
}
