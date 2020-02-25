package com.appsinventiv.littlegarden.NetworkResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.appsinventiv.littlegarden.Models.Meta;
import com.appsinventiv.littlegarden.Models.OrderModel;

import java.util.List;

public class ListOfOrdersResponse {
    @SerializedName("data")
    @Expose
    private List<OrderModel> orders = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
