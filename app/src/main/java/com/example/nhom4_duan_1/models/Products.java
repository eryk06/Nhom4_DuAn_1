package com.example.nhom4_duan_1.models;

public class Products {
    private String Id;
    private String Name;
    private String Image;
    private String Type;
    private double Price;

    public Products() {
    }

    public Products(String id, String name, String image, String type, double price) {
        Id = id;
        Name = name;
        Image = image;
        Type = type;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "Products{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", Type='" + Type + '\'' +
                ", Price=" + Price +
                '}';
    }
}
