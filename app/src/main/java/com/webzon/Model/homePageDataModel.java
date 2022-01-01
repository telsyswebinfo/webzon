package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class homePageDataModel {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("banner")
    private ArrayList<Banner> banner = null;
    @SerializedName("order")
    private Integer order;
    @SerializedName("view")
    private Integer view;
    @SerializedName("product")
    private Integer product;
    @SerializedName("sale")
    private Integer sale;

    public homePageDataModel(Integer status, String message, ArrayList<Banner> banner, Integer order, Integer view, Integer product, Integer sale) {
        this.status = status;
        this.message = message;
        this.banner = banner;
        this.order = order;
        this.view = view;
        this.product = product;
        this.sale = sale;
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

    public ArrayList<Banner> getBanner() {
        return banner;
    }

    public void setBanner(ArrayList<Banner> banner) {
        this.banner = banner;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public class Banner {
        @SerializedName("id")
        private Integer id;
        @SerializedName("title")
        private String title;
        @SerializedName("description")
        private String description;
        @SerializedName("slug")
        private String slug;
        @SerializedName("photo")
        private String photo;

        public Banner(Integer id, String title, String description, String slug, String photo) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.slug = slug;
            this.photo = photo;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
