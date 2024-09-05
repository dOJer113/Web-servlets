package ru.roznov.servlets_2.model.dao.DAOinterfeices;

import ru.roznov.servlets_2.objects.products.Product;

import java.util.List;

public interface ProductDAO {
    List<Product> getProducts();
}
