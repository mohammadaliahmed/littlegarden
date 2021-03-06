package com.appsinventiv.littlegarden.NetworkResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.appsinventiv.littlegarden.Models.Category;
import com.appsinventiv.littlegarden.Models.Meta;

import java.util.List;

public class CategoryResponse {


    @SerializedName("data")
    @Expose
    private List<Category> data = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
