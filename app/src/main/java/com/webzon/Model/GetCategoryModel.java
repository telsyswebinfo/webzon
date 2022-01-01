package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCategoryModel {
    @SerializedName("success")
    private Integer success;
    @SerializedName("statusCode")
    private Integer statusCode;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private ArrayList<Data> data = null;

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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("image")
        private String image;
        @SerializedName("status")
        private String status;
        @SerializedName("created")
        private String created;
        @SerializedName("modified")
        private Object modified;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public Object getModified() {
            return modified;
        }

        public void setModified(Object modified) {
            this.modified = modified;
        }
    }
}
