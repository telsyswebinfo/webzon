package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

public class OtpModel {
    @SerializedName("success")
    private Integer success;
    @SerializedName("statusCode")
    private Integer statusCode;
    @SerializedName("msg")
    private String msg;
    @SerializedName("otp")
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
