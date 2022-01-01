package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class verifyUserModel {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("business")
    private String business;
    @SerializedName("products")
    private String products;

   /* @SerializedName("data")
    private Data data;
    @SerializedName("banner")
    private ArrayList<Banner> banner = null;
    @SerializedName("order")
    private Integer order;
    @SerializedName("view")
    private Integer view;
    @SerializedName("product")
    private Integer product;
    @SerializedName("sale")
    private Integer sale;*/

    public verifyUserModel(Integer status, String message, String business, String products) {
        this.status = status;
        this.message = message;
        this.business = business;
        this.products = products;
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

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
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



    public class Data {
        @SerializedName("id")
        private Integer id;
        @SerializedName("photo")
        private String photo;
        @SerializedName("referral_code")
        private String referralCode;
        @SerializedName("business")
        private Business business;
        @SerializedName("product")
        private Product product;

        public Data(Integer id, String photo, String referralCode, Business business, Product product) {
            this.id = id;
            this.photo = photo;
            this.referralCode = referralCode;
            this.business = business;
            this.product = product;
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

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
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
                @SerializedName("photo")
                private String photo;

                public Category(Integer id, String title, String photo) {
                    this.id = id;
                    this.title = title;
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

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }
            }
        }

        public class Product {
            @SerializedName("current_page")
            private Integer currentPage;
            @SerializedName("data")
            private ArrayList<Datum> data = null;
            @SerializedName("first_page_url")
            private String firstPageUrl;
            @SerializedName("from")
            private Integer from;
            @SerializedName("last_page")
            private Integer lastPage;
            @SerializedName("last_page_url")
            private String lastPageUrl;
            @SerializedName("next_page_url")
            private Object nextPageUrl;
            @SerializedName("path")
            private String path;
            @SerializedName("per_page")
            private Integer perPage;
            @SerializedName("prev_page_url")
            private Object prevPageUrl;
            @SerializedName("to")
            private Integer to;
            @SerializedName("total")
            private Integer total;

            public Product(Integer currentPage, ArrayList<Datum> data, String firstPageUrl, Integer from, Integer lastPage, String lastPageUrl, Object nextPageUrl, String path, Integer perPage, Object prevPageUrl, Integer to, Integer total) {
                this.currentPage = currentPage;
                this.data = data;
                this.firstPageUrl = firstPageUrl;
                this.from = from;
                this.lastPage = lastPage;
                this.lastPageUrl = lastPageUrl;
                this.nextPageUrl = nextPageUrl;
                this.path = path;
                this.perPage = perPage;
                this.prevPageUrl = prevPageUrl;
                this.to = to;
                this.total = total;
            }

            public Integer getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(Integer currentPage) {
                this.currentPage = currentPage;
            }

            public ArrayList<Datum> getData() {
                return data;
            }

            public void setData(ArrayList<Datum> data) {
                this.data = data;
            }

            public String getFirstPageUrl() {
                return firstPageUrl;
            }

            public void setFirstPageUrl(String firstPageUrl) {
                this.firstPageUrl = firstPageUrl;
            }

            public Integer getFrom() {
                return from;
            }

            public void setFrom(Integer from) {
                this.from = from;
            }

            public Integer getLastPage() {
                return lastPage;
            }

            public void setLastPage(Integer lastPage) {
                this.lastPage = lastPage;
            }

            public String getLastPageUrl() {
                return lastPageUrl;
            }

            public void setLastPageUrl(String lastPageUrl) {
                this.lastPageUrl = lastPageUrl;
            }

            public Object getNextPageUrl() {
                return nextPageUrl;
            }

            public void setNextPageUrl(Object nextPageUrl) {
                this.nextPageUrl = nextPageUrl;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public Integer getPerPage() {
                return perPage;
            }

            public void setPerPage(Integer perPage) {
                this.perPage = perPage;
            }

            public Object getPrevPageUrl() {
                return prevPageUrl;
            }

            public void setPrevPageUrl(Object prevPageUrl) {
                this.prevPageUrl = prevPageUrl;
            }

            public Integer getTo() {
                return to;
            }

            public void setTo(Integer to) {
                this.to = to;
            }

            public Integer getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }

            public class Datum {
                @SerializedName("title")
                private String title;
                @SerializedName("slug")
                private String slug;
                @SerializedName("summary")
                private String summary;
                @SerializedName("description")
                private String description;
                @SerializedName("stock")
                private Integer stock;
                @SerializedName("size")
                private String size;
                @SerializedName("condition")
                private String condition;
                @SerializedName("price")
                private Integer price;
                @SerializedName("discount")
                private Integer discount;
                @SerializedName("cat_id")
                private Integer catId;
                @SerializedName("brand_id")
                private Object brandId;

                public Datum(String title, String slug, String summary, String description, Integer stock, String size, String condition, Integer price, Integer discount, Integer catId, Object brandId) {
                    this.title = title;
                    this.slug = slug;
                    this.summary = summary;
                    this.description = description;
                    this.stock = stock;
                    this.size = size;
                    this.condition = condition;
                    this.price = price;
                    this.discount = discount;
                    this.catId = catId;
                    this.brandId = brandId;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getSlug() {
                    return slug;
                }

                public void setSlug(String slug) {
                    this.slug = slug;
                }

                public String getSummary() {
                    return summary;
                }

                public void setSummary(String summary) {
                    this.summary = summary;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public Integer getStock() {
                    return stock;
                }

                public void setStock(Integer stock) {
                    this.stock = stock;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getCondition() {
                    return condition;
                }

                public void setCondition(String condition) {
                    this.condition = condition;
                }

                public Integer getPrice() {
                    return price;
                }

                public void setPrice(Integer price) {
                    this.price = price;
                }

                public Integer getDiscount() {
                    return discount;
                }

                public void setDiscount(Integer discount) {
                    this.discount = discount;
                }

                public Integer getCatId() {
                    return catId;
                }

                public void setCatId(Integer catId) {
                    this.catId = catId;
                }

                public Object getBrandId() {
                    return brandId;
                }

                public void setBrandId(Object brandId) {
                    this.brandId = brandId;
                }
            }
        }
    }
}
