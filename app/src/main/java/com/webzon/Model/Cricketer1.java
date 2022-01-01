package com.webzon.Model;

import java.io.Serializable;

public class Cricketer1 implements Serializable {

    public String Cname;


    public Cricketer1() {

    }

    public Cricketer1(String cname) {
        Cname = cname;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }
}
