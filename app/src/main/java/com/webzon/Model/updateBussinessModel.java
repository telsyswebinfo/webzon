package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

public class updateBussinessModel {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public updateBussinessModel(Integer status, String message, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("id")
        private Integer id;
        @SerializedName("photo")
        private String photo;
        @SerializedName("referral_code")
        private String referralCode;
        @SerializedName("business")
        private Business business;

        public Data(Integer id, String photo, String referralCode, Business business) {
            this.id = id;
            this.photo = photo;
            this.referralCode = referralCode;
            this.business = business;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getReferralCode() {
            return referralCode;
        }

        public void setReferralCode(String referralCode) {
            this.referralCode = referralCode;
        }

        public Business getBusiness() {
            return business;
        }

        public void setBusiness(Business business) {
            this.business = business;
        }

        public class Business {
            @SerializedName("id")
            private Integer id;
            @SerializedName("user_id")
            private Integer userId;
            @SerializedName("name")
            private String name;
            @SerializedName("category_id")
            private Integer categoryId;
            @SerializedName("address")
            private String address;
            @SerializedName("slug")
            private String slug;
            @SerializedName("store")
            private String store;
            @SerializedName("category")
            private Category category;

            public Business(Integer id, Integer userId, String name, Integer categoryId, String address, String slug, String store, Category category) {
                this.id = id;
                this.userId = userId;
                this.name = name;
                this.categoryId = categoryId;
                this.address = address;
                this.slug = slug;
                this.store = store;
                this.category = category;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Integer categoryId) {
                this.categoryId = categoryId;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }

            public String getStore() {
                return store;
            }

            public void setStore(String store) {
                this.store = store;
            }

            public Category getCategory() {
                return category;
            }

            public void setCategory(Category category) {
                this.category = category;
            }

            public class Category {
                @SerializedName("id")
                private Integer id;
                @SerializedName("title")
                private String title;

                public Category(Integer id, String title) {
                    this.id = id;
                    this.title = title;
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
            }
        }

        }
}
