package com.webzon.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class productListModel {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("category")
    @Expose
    private Category category;

    public productListModel(Integer status, String message, Object data, Product product, Category category) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.product = product;
        this.category = category;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public class Product {

        @SerializedName("current_page")
        @Expose
        private Integer currentPage;
        @SerializedName("data")
        @Expose
        private ArrayList<Datum> data = null;
        @SerializedName("first_page_url")
        @Expose
        private String firstPageUrl;
        @SerializedName("from")
        @Expose
        private Integer from;
        @SerializedName("last_page")
        @Expose
        private Integer lastPage;
        @SerializedName("last_page_url")
        @Expose
        private String lastPageUrl;
        @SerializedName("next_page_url")
        @Expose
        private Object nextPageUrl;
        @SerializedName("path")
        @Expose
        private String path;
        @SerializedName("per_page")
        @Expose
        private Integer perPage;
        @SerializedName("prev_page_url")
        @Expose
        private Object prevPageUrl;
        @SerializedName("to")
        @Expose
        private Integer to;
        @SerializedName("total")
        @Expose
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

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("slug")
            @Expose
            private String slug;
            @SerializedName("description")
            @Expose
            private Object description;
            @SerializedName("size")
            @Expose
            private ArrayList<Size> size = null;
            @SerializedName("price")
            @Expose
            private Integer price;
            @SerializedName("discount")
            @Expose
            private Integer discount;
            @SerializedName("cat_id")
            @Expose
            private Integer catId;
            @SerializedName("photo")
            @Expose
            private ArrayList<Photo> photo = null;
            @SerializedName("variation")
            @Expose
            private ArrayList<Object> variation = null;

            public Datum(String title, String slug, Object description, ArrayList<Size> size, Integer price, Integer discount, Integer catId, ArrayList<Photo> photo, ArrayList<Object> variation) {
                this.title = title;
                this.slug = slug;
                this.description = description;
                this.size = size;
                this.price = price;
                this.discount = discount;
                this.catId = catId;
                this.photo = photo;
                this.variation = variation;
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

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public ArrayList<Size> getSize() {
                return size;
            }

            public void setSize(ArrayList<Size> size) {
                this.size = size;
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

            public ArrayList<Photo> getPhoto() {
                return photo;
            }

            public void setPhoto(ArrayList<Photo> photo) {
                this.photo = photo;
            }

            public ArrayList<Object> getVariation() {
                return variation;
            }

            public void setVariation(ArrayList<Object> variation) {
                this.variation = variation;
            }

            public class Photo {

                @SerializedName("image")
                @Expose
                private String image;

                public Photo(String image) {
                    this.image = image;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }
            }
            public class Size {

                @SerializedName("color")
                @Expose
                private Object color;

                public Size(Object color) {
                    this.color = color;
                }

                public Object getColor() {
                    return color;
                }

                public void setColor(Object color) {
                    this.color = color;
                }
            }


        }
    }
    public class Category {

        @SerializedName("current_page")
        @Expose
        private Integer currentPage;
        @SerializedName("data")
        @Expose
        private ArrayList<Datum__1> data = null;
        @SerializedName("first_page_url")
        @Expose
        private String firstPageUrl;
        @SerializedName("from")
        @Expose
        private Integer from;
        @SerializedName("last_page")
        @Expose
        private Integer lastPage;
        @SerializedName("last_page_url")
        @Expose
        private String lastPageUrl;
        @SerializedName("next_page_url")
        @Expose
        private Object nextPageUrl;
        @SerializedName("path")
        @Expose
        private String path;
        @SerializedName("per_page")
        @Expose
        private Integer perPage;
        @SerializedName("prev_page_url")
        @Expose
        private Object prevPageUrl;
        @SerializedName("to")
        @Expose
        private Integer to;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Category(Integer currentPage, ArrayList<Datum__1> data, String firstPageUrl, Integer from, Integer lastPage, String lastPageUrl, Object nextPageUrl, String path, Integer perPage, Object prevPageUrl, Integer to, Integer total) {
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

        public ArrayList<Datum__1> getData() {
            return data;
        }

        public void setData(ArrayList<Datum__1> data) {
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

        public class Datum__1 {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("photo")
            @Expose
            private String photo;
            @SerializedName("status")
            @Expose
            private String status;

            public Datum__1(Integer id, String title, String photo, String status) {
                this.id = id;
                this.title = title;
                this.photo = photo;
                this.status = status;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }

}
