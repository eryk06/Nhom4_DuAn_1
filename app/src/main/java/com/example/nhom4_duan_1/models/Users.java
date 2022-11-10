package com.example.nhom4_duan_1.models;

public class Users {
    private String Id;
    private String Pass;
    private String Name;
    private String Phone;
    private String Address;

    public Users(String id, String pass, String name, String phone, String address) {
        Id = id;
        Pass = pass;
        Name = name;
        Phone = phone;
        Address = address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


}
