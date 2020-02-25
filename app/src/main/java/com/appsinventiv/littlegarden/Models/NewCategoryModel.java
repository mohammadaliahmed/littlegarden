package com.appsinventiv.littlegarden.Models;

public class NewCategoryModel {
    int img;
    String name;

    public NewCategoryModel(String name, int img) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
