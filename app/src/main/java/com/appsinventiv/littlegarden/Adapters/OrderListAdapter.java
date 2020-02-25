package com.appsinventiv.littlegarden.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsinventiv.littlegarden.Activities.ViewOrderDetails;
import com.appsinventiv.littlegarden.Models.OrderModel;
import com.appsinventiv.littlegarden.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    Context context;
    List<OrderModel> itemList = new ArrayList<>();

    public OrderListAdapter(Context context, List<OrderModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setItemList(List<OrderModel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        OrderListAdapter.ViewHolder viewHolder = new OrderListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final OrderModel orderModel = itemList.get(position);
        holder.serial.setText((position + 1) + ") ");
        String[] date = orderModel.getCreatedAt().split(" ");

        String paymntStatus = "";
        if (orderModel.getPaymentStatus() != null) {
            if (orderModel.getPaymentStatus().equalsIgnoreCase("1")) {
                paymntStatus = "Delivered";
            }
        } else {
            paymntStatus = "Null";
        }
        holder.orderId.setText("Order Id: " + orderModel.getId() + "\nOrder date: " + (date[1] + "\n" + date[0])
                + "\nStatus: " + paymntStatus
        );

        if (orderModel.getPaymentMethod() != null) {
            holder.paidViaLayout.setVisibility(View.VISIBLE);
        } else {
            holder.paidViaLayout.setVisibility(View.GONE);
        }

        holder.productInfo.setText("Products: " + orderModel.getProducts() + "\nTotal: $" + String.format("%.2f", Double.parseDouble(orderModel.getTotal())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ViewOrderDetails.class);
                i.putExtra("orderId", orderModel.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, date, productInfo, serial;
        ImageView paidVia;
        LinearLayout paidViaLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.orderId);
            productInfo = itemView.findViewById(R.id.productInfo);
            serial = itemView.findViewById(R.id.serial);
            paidVia = itemView.findViewById(R.id.paidVia);
            paidViaLayout = itemView.findViewById(R.id.paidViaLayout);
        }
    }
}
