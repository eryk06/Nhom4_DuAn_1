package com.example.nhom4_duan_1.models;

public class Bills {
    private String Id;
    private String User;
    private String Time;
    private String Product;
    private String Voucher;
    private String Price;
    private String Amount;

    public Bills() {
    }

    public Bills(String id, String user, String time, String product, String voucher, String price, String amount) {
        Id = id;
        User = user;
        Time = time;
        Product = product;
        Voucher = voucher;
        Price = price;
        Amount = amount;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}

