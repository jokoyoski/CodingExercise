package com.example.codingexercise.request;

import java.util.List;
import com.example.codingexercise.model.Package;



public class ProductPackage extends Package {

    public ProductPackage(String id, String name, String description, List<String> productIds ){
        this.name = name;
        this.description = description;
        this.productIds = productIds;
    }
    public ProductPackage(){
    }
    private List<String> productIds;

    private String name;
    private String description;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    private String currency;
    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
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



}
