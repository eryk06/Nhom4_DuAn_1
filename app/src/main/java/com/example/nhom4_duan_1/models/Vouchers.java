package com.example.nhom4_duan_1.models;

public class Vouchers {
    private String Id;
    private String Name;
    private double Price;

    public Vouchers() {
    }

    public Vouchers(String id, String name, double price) {
        Id = id;
        Name = name;
        Price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
