package com.example.nhom4_duan_1.models;

public class Types {
    private String Id;
    private String Name;

    public Types() {
    }

    public Types(String id, String name) {
        Id = id;
        Name = name;
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
}
