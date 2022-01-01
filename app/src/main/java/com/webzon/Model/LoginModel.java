package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("status")
    private Integer status;
    @SerializedName("timer")
    private Integer timer;
    @SerializedName("message")
    private String message;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("verify_type")
    private String verifyType;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimer() {
        return timer;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }
}
