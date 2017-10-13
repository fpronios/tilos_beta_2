package com.example.chris.tilos_beta_2;

/**
 * Created by chris on 10/3/2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private List<Value> value = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

}