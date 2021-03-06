package com.appsinventiv.littlegarden.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuModel {

    @SerializedName("id")
    @Expose
    private Integer id;



    @SerializedName("u_id")
    @Expose
    private String uId;
    @SerializedName("product")
    @Expose
    private String productt;
    @SerializedName("extra")
    @Expose
    private String extra;
    @SerializedName("sub_total")
    @Expose
    private Object subTotal;
    @SerializedName("total")
    @Expose
    private Object total;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("pro")
    @Expose
    private Product product;
    @SerializedName("variation")
    @Expose
    private Extra variation;
    @SerializedName("item_quantity")
    @Expose
    private int quantity;



    public Extra getVariation() {
        return variation;
    }

    public void setVariation(Extra variation) {
        this.variation = variation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getProductt() {
        return productt;
    }

    public void setProductt(String productt) {
        this.productt = productt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Object getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Object subTotal) {
        this.subTotal = subTotal;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
