package com.appsinventiv.littlegarden.NetworkResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.appsinventiv.littlegarden.Models.Meta;
import com.appsinventiv.littlegarden.Models.Success;

public class LoginResponse {

    @SerializedName("data")
    @Expose
    private Success success;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
