package com.appsinventiv.littlegarden.NetworkResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.appsinventiv.littlegarden.Models.Meta;
import com.appsinventiv.littlegarden.Models.Table;

import java.util.List;

public class MakeReservationResponse {
    @SerializedName("data")
    @Expose
    private List<Table> tables = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
