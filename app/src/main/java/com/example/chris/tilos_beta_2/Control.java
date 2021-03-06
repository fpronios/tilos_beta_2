package com.example.chris.tilos_beta_2;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Control {

    @SerializedName("total_rows")
    @Expose
    private Integer totalRows;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("rows")
    @Expose
    private List<cRow> rows = null;

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<cRow> getRows() {
        return rows;
    }

    public void setRows(List<cRow> rows) {
        this.rows = rows;
    }

}