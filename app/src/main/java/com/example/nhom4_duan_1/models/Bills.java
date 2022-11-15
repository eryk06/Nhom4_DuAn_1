package com.example.nhom4_duan_1.models;

public class Bills {
    private String Id;
    private String User;
    private String Product;
    private String Voucher;

    public Bills() {
    }

    public Bills(String id, String user, String product, String voucher) {
        Id = id;
        User = user;
        Product = product;
        Voucher = voucher;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getVoucher() {
        return Voucher;
    }

    public void setVoucher(String voucher) {
        Voucher = voucher;
    }
}

