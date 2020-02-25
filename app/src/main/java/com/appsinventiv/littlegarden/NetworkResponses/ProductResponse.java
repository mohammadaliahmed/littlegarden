package com.appsinventiv.littlegarden.NetworkResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.appsinventiv.littlegarden.Models.Meta;
import com.appsinventiv.littlegarden.Models.Product;

import java.util.List;

public class ProductResponse {


    @SerializedName("data")
    @Expose
    private List<Product> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
