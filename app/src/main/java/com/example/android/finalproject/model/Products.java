package com.example.android.finalproject.model;

import java.util.List;

public class Products {

        private List<Image> images;

        private String name;

        private int id;

        private String price;

        public Products(List<Image> images, String name, String price, int id) {
            this.images = images;
            this.name = name;
            this.price = price;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public List<Image> getImages() {
                return images;
            }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

}
