package com.webzon.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class unitListModel {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public unitListModel(Integer status, String message, Data data) {
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

        public Data(Integer currentPage, ArrayList<Datum> data, String firstPageUrl, Integer from, Integer lastPage, String lastPageUrl, Object nextPageUrl, String path, Integer perPage, Object prevPageUrl, Integer to, Integer total) {
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
            private Integer id;
            @SerializedName("unit")
            private String unit;
            @SerializedName("fullname")
            private String fullname;

            public Datum(Integer id, String unit, String fullname) {
                this.id = id;
                this.unit = unit;
                this.fullname = fullname;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }
        }
    }
}
