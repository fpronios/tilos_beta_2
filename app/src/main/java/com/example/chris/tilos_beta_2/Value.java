package com.example.chris.tilos_beta_2;

/**
 * Created by Filippos on 9/10/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("V1")
    @Expose
    private Float v1;
    @SerializedName("V2")
    @Expose
    private Float v2;
    @SerializedName("V3")
    @Expose
    private Float v3;
    @SerializedName("V")
    @Expose
    private Float v;

    public Float getV1() {
        return v1;
    }

    public void setV1(Float v1) {
        this.v1 = v1;
    }

    public Float getV2() {
        return v2;
    }

    public void setV2(Float v2) {
        this.v2 = v2;
    }

    public Float getV3() {
        return v3;
    }

    public void setV3(Float v3) {
        this.v3 = v3;
    }

    public Float getV() {
        return v;
    }

    public void setV(Float v) {
        this.v = v;
    }

}