package ru.roznov.servlets_2.objects.products;

public class Product {
    private int id;
    private ProductEnum name;

    public Product(int id, ProductEnum name) {
        this.id = id;
        this.name = name;
    }

    public Product() {
        this.id = 0;
        this.name = ProductEnum.NULL_PRODUCT;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductEnum getName() {
        return this.name;
    }

    public void setName(ProductEnum name) {
        this.name = name;
    }
}
