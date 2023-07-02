package com.example.codingexercise.response;

import java.util.List;
import com.example.codingexercise.model.Package;
import com.example.codingexercise.model.Product;

public class ProductPackage extends Package {
    private List<Product> products;
    private String id;
    private String name;
    private String description;
    private Double price;

    private String currency;
    public ProductPackage(String name, String description, List<Product> products){
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> productIds) {
        this.products = products;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
