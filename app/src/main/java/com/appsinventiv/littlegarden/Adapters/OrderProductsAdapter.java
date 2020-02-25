package com.appsinventiv.littlegarden.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsinventiv.littlegarden.Models.DetailModel;
import com.appsinventiv.littlegarden.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderProductsAdapter extends RecyclerView.Adapter<OrderProductsAdapter.ViewHolder> {
    Context context;
    List<DetailModel> itemList = new ArrayList<>();


    public OrderProductsAdapter(Context context, List<DetailModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setItemList(List<DetailModel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_product_item_layout, parent, false);
        OrderProductsAdapter.ViewHolder viewHolder = new OrderProductsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailModel model = itemList.get(position);

        holder.serial.setText((position + 1) + "");
        holder.name.setText("" + model.getProduct().getName());
        holder.qty.setText("" + model.getQty());
        holder.price.setText("$" + model.getProduct().getPrice());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serial, name, qty, price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serial = itemView.findViewById(R.id.serial);
            name = itemView.findViewById(R.id.name);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price);
        }
    }


}
