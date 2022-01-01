package com.webzon.Model;

import java.io.Serializable;

public class Cricketer implements Serializable {

    public String txt_size;
    public String txt_price;
    public String txt_dPrice;

    public Cricketer() {

    }

    public Cricketer(String txt_size, String txt_price, String txt_dPrice) {
        this.txt_size = txt_size;
        this.txt_price = txt_price;
        this.txt_dPrice = txt_dPrice;
    }

    public String getTxt_size() {
        return txt_size;
    }

    public void setTxt_size(String txt_size) {
        this.txt_size = txt_size;
    }

    public String getTxt_price() {
        return txt_price;
    }

    public void setTxt_price(String txt_price) {
        this.txt_price = txt_price;
    }

    public String getTxt_dPrice() {
        return txt_dPrice;
    }

    public void setTxt_dPrice(String txt_dPrice) {
        this.txt_dPrice = txt_dPrice;
    }
}
