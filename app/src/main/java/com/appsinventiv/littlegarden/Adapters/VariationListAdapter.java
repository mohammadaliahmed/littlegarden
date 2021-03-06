package com.appsinventiv.littlegarden.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsinventiv.littlegarden.Models.Extra;
import com.appsinventiv.littlegarden.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VariationListAdapter extends RecyclerView.Adapter<VariationListAdapter.ViewHolder> {
    Context context;
    List<Extra> itemList = new ArrayList<>();
    HashMap<Integer, Integer> selectedList = new HashMap<>();

    ExtrasCallback callback;

    public VariationListAdapter(Context context, List<Extra> itemList, ExtrasCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    public void setItemList(List<Extra> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.variation_item_layout, parent, false);
        VariationListAdapter.ViewHolder viewHolder = new VariationListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Extra extra = itemList.get(position);
        if (selectedList.containsKey(position)) {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.red_corners_bold));
        } else {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.grey_corners));

        }

        holder.name.setText(extra.getName());
        holder.price.setText("$" + extra.getPrice());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedList.containsKey(position)) {
                    selectedList.remove(position);
                    callback.onRemove(extra);
                    notifyDataSetChanged();
                } else {
                    selectedList.put(position, position);
                    callback.onSelect(extra);
                    notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
        }
    }

    public interface ExtrasCallback {
        public void onSelect(Extra extra);

        public void onRemove(Extra extra);
    }
}
