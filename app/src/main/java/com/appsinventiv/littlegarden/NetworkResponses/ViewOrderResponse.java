package com.appsinventiv.littlegarden.NetworkResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.appsinventiv.littlegarden.Models.Meta;
import com.appsinventiv.littlegarden.Models.OrderModel;

public class ViewOrderResponse {
    @SerializedName("data")
    @Expose
    private OrderModel data;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public OrderModel getData() {
        return data;
    }

    public void setData(OrderModel data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
