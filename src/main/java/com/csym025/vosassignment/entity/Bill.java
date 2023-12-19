package com.csym025.vosassignment.entity;

import java.io.Serializable;

public class Bill implements Serializable {
    private String type;
    private int custId;
    private String custName;
    private String period;
    private int openRead;
    private int closeRead;
    private double ratePerUit;
    private int kwh;
    private double total;

    public Bill() {}

    public Bill(String type, int custId, String custName, String period, int openRead, int closeRead, double ratePerUit, int kwh, double total) {
        this.type = type;
        this.custId = custId;
        this.custName = custName;
        this.period = period;
        this.openRead = openRead;
        this.closeRead = closeRead;
        this.ratePerUit = ratePerUit;
        this.kwh = kwh;
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public int getOpenRead() {
        return openRead;
    }

    public void setOpenRead(int openRead) {
        this.openRead = openRead;
    }

    public int getCloseRead() {
        return closeRead;
    }

    public void setCloseRead(int closeRead) {
        this.closeRead = closeRead;
    }

    public double getRatePerUit() {
        return ratePerUit;
    }

    public void setRatePerUit(double ratePerUit) {
        this.ratePerUit = ratePerUit;
    }

    public int getKwh() {
        return kwh;
    }

    public void setKwh(int kwh) {
        this.kwh = kwh;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
