package com.example.stkpush.api.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallbackMetadata {

    @SerializedName("Item")
    @Expose
    private List<Item> item = null;

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

}
