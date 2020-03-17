package com.appsinventiv.littlegarden.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsinventiv.littlegarden.Activities.ListOfProducts;
import com.appsinventiv.littlegarden.Models.Category;
import com.appsinventiv.littlegarden.Models.NewCategoryModel;
import com.appsinventiv.littlegarden.R;
import com.appsinventiv.littlegarden.Utils.AppConfig;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.easing.Circ;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    Context context;
    List<NewCategoryModel> itemList = new ArrayList<>();

    public HomeCategoryAdapter(Context context, List<NewCategoryModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setItemList(List<NewCategoryModel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_new_category_item, parent, false);
        HomeCategoryAdapter.ViewHolder viewHolder = new HomeCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewCategoryModel model = itemList.get(position);
        holder.name.setText(model.getName());
        Glide.with(context).load(model.getImg()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
        }
    }
}
