package com.example.nhom4_duan_1.managers;

import com.example.nhom4_duan_1.models.Products;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Manager {

    private List<Products> list;
    private List<Products> listTemp;

    public Manager(List<Products> list) {
        this.list = list;
    }


    public List<Products> onSortName_AtoZ(){
        listTemp = list;
        Collections.sort(listTemp, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return listTemp;
    }

    public List<Products> onSortPrice_ASC(){
        listTemp = list;
        Collections.sort(listTemp, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return o1.getPrice() > o2.getPrice() ? 1 : -1;
            }
        });
        return listTemp;
    }

    public List<Products> onSortPrice_DESC(){
        listTemp = list;
        Collections.sort(listTemp, new Comparator<Products>() {
            @Override
            public int compare(Products o1, Products o2) {
                return o1.getPrice() > o2.getPrice() ? -1 : 1;
            }
        });
        return listTemp;
    }

    public List<Products> onSearch(String a){
        String nameSeach = a.trim();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().contains(nameSeach)) {
                listTemp.add(list.get(i));
            }
        }
        return listTemp;
    }

}
