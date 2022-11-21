package com.example.nhom4_duan_1.models;

public class Cart {
    private String Id;
    private String Id_Product;
    private int Amount;
    private Double Total;

    public Cart() {
    }

    public Cart(String id, String id_Product, int amount, Double total) {
        Id = id;
        Id_Product = id_Product;
        Amount = amount;
        Total = total;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId_Product() {
        return Id_Product;
    }

    public void setId_Product(String id_Product) {
        Id_Product = id_Product;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "Id='" + Id + '\'' +
                ", Id_Product='" + Id_Product + '\'' +
                ", Amount=" + Amount +
                ", Total=" + Total +
                '}';
    }
}
