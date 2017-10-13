package com.example.chris.tilos_beta_2;

/**
 * Created by Filippos on 9/10/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("V1")
    @Expose
    private Integer v1;
    @SerializedName("V2")
    @Expose
    private Integer v2;
    @SerializedName("V3")
    @Expose
    private Integer v3;
    @SerializedName("V")
    @Expose
    private Integer v;

    public Integer getV1() {
        return v1;
    }

    public void setV1(Integer v1) {
        this.v1 = v1;
    }

    public Integer getV2() {
        return v2;
    }

    public void setV2(Integer v2) {
        this.v2 = v2;
    }

    public Integer getV3() {
        return v3;
    }

    public void setV3(Integer v3) {
        this.v3 = v3;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}