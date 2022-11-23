package com.example.nhom4_duan_1.managers;

import com.example.nhom4_duan_1.models.Products;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Manager {

    private List<Products> list;
    private List<String> listTemp;

    public Manager(List<Products> list) {
        this.list = list;
    }


    public List<Products> onSortName_AtoZ(){
        Collections.sort(list, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return list;
    }

    public List<Products> onSortName_ZtoA(){
        Collections.sort(list, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return o2.getName().compareTo(o1.getName());
            }
        });
        return list;
    }

    public List<Products> onSortPrice_ASC(){
        Collections.sort(list, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return o1.getPrice() > o2.getPrice() ? 1 : -1;
            }
        });
        return list;
    }

    public List<Products> onSortPrice_DESC(){
        Collections.sort(list, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return o1.getPrice() > o2.getPrice() ? -1 : 1;
            }
        });
        return list;
    }

    public List<String> onSearch(String a){
        String nameSeach = a.trim();
        listTemp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().contains(nameSeach)) {

                System.out.println("danh sach manager" + list.get(i));
                listTemp.add(list.get(i).getId());
            }
        }
        return listTemp;
    }

}
