package com.example.nhom4_duan_1.models;

public class UserOnline {
    private String id;

    public UserOnline(String id) {
        this.id = id;
    }

    public UserOnline() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserOnline{" +
                "id='" + id + '\'' +
                '}';
    }
}
