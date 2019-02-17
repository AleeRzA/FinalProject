package com.example.android.finalproject.model;

import java.util.List;

public class Root {

    private List<Products> products;

    public Root(List<Products> products) {
        this.products = products;
    }

    public List<Products> getProducts() {
        return products;
    }
}
