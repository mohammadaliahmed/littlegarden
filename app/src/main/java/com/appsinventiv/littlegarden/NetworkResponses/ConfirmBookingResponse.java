package com.appsinventiv.littlegarden.NetworkResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.appsinventiv.littlegarden.Models.ConfirmBookingModel;
import com.appsinventiv.littlegarden.Models.Meta;

public class ConfirmBookingResponse {
    @SerializedName("data")
    @Expose
    private ConfirmBookingModel confirmBookingModel;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public ConfirmBookingModel getConfirmBookingModel() {
        return confirmBookingModel;
    }

    public void setConfirmBookingModel(ConfirmBookingModel confirmBookingModel) {
        this.confirmBookingModel = confirmBookingModel;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
