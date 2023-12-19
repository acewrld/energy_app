package com.csym025.vosassignment.entity;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id;
    private String fullName;
    private String email;
    private String mobileNo;
    private String postCode;
    private String meterNo;

    public Customer(){}

    public Customer(int id, String fullName, String email, String mobileNo, String postCode, String meterNo) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.mobileNo = mobileNo;
        this.postCode = postCode;
        this.meterNo = meterNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }
}
