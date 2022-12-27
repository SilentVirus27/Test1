package com.example.test1;

public class CategoryModel {
    public String categoryName;
    public CategoryModel() {

    }

    public CategoryModel(String category){
        this.categoryName=category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
