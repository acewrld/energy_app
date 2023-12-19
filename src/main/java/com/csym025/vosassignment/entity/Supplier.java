package com.csym025.vosassignment.entity;

public class Supplier {
    private String supplierName;
    private static Supplier supplierObject;

    public static Supplier getSupplierObject() {
        if (supplierObject == null) {
            supplierObject = new Supplier();
        }
        return supplierObject;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
