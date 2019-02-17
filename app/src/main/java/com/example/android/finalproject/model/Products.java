package com.example.android.finalproject.model;

import java.util.List;

public class Products {

        private List<Image> images;

        private String name;

        private String price;

        public Products(List<Image> images, String name, String price) {
            this.images = images;
            this.name = name;
            this.price = price;
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
