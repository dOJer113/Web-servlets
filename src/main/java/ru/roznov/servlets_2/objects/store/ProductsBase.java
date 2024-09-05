package ru.roznov.servlets_2.objects.store;

import ru.roznov.servlets_2.objects.products.Product;
import ru.roznov.servlets_2.objects.products.ProductEnum;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProductsBase {
    //todo сделать заполнение из таблицы бд продкуты, для этого написать новый дао
    private static List<Product> products = new CopyOnWriteArrayList<>();

    private ProductsBase() {
    }

    public static int getIdByProductName(ProductEnum productName) {
        return ProductsBase.products.stream().filter(product -> product.getName() == productName).findFirst().orElse(new Product()).getId();
    }

    public static List<Product> getProductsList() {
        return ProductsBase.products;
    }

    public static ProductEnum getProductNameById(int id) {
        return ProductsBase.products.stream().filter(product -> product.getId() == id).findFirst().orElse(new Product()).getName();
    }

    public static void setProductsFromList(List<Product> productsFromList) {
        ProductsBase.products = productsFromList;
    }

}
