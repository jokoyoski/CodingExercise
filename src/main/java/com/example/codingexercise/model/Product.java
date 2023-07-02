package com.example.codingexercise.model;

public class Product {
    private String id;
    private String name;
    private Double usdPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUsdPrice() {
        return usdPrice;
    }

    public void setUsdPrice(Double usdPrice) {
        this.usdPrice = usdPrice;
    }
}
