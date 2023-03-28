package com.example.familyeducation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familyeducation.Bean.IndexGridviewIcon;
import com.example.familyeducation.R;
import com.to.aboomy.pager2banner.Banner;
import com.to.aboomy.pager2banner.IndicatorView;

import java.util.ArrayList;
import java.util.List;

public class Banner_RecycleViewA extends RecyclerView.Adapter<Banner_RecycleViewA.BaseViewHolder>{

    private Context context;
    private List<IndexGridviewIcon> imgIds;

    public Banner_RecycleViewA(Context context,List<IndexGridviewIcon> bannerData) {
        this.context = context;
        this.imgIds = bannerData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imgIds == null?0:imgIds.size();
    }

    @NonNull
    @Override
    public Banner_RecycleViewA.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bv_index_banner_item, parent, false);
        return new Banner_RecycleViewA.BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Banner_RecycleViewA.BaseViewHolder holder, int position) {
        final IndexGridviewIcon good = imgIds.get(position);
        holder.img_icon.setImageResource(good.getiId());
        holder.banner_title.setText(good.getiName()+ "");
    }

    //创建一个viewholder,用来复用对象
    class BaseViewHolder extends RecyclerView.ViewHolder{
        ImageView img_icon;
        TextView banner_title;
        public BaseViewHolder(View itemView) {
            super(itemView);
            img_icon = itemView.findViewById(R.id.img_icon);
            banner_title = itemView.findViewById(R.id.banner_title);
        }
    }
}
