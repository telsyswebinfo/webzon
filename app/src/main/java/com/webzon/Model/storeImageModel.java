package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

public class storeImageModel {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private String data;

    public storeImageModel(Integer status, String message, String data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
