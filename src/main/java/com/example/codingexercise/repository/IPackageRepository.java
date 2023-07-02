package com.example.codingexercise.repository;

import com.example.codingexercise.response.ProductPackage;

import java.util.Collection;
import java.util.List;

public interface IPackageRepository {
    public ProductPackage createPackage(com.example.codingexercise.response.ProductPackage productPackage);
    public ProductPackage updatePackage(com.example.codingexercise.response.ProductPackage productPackage, String id);
    public Collection<ProductPackage> getPackages();
    public void deletePackage(String id);
    public ProductPackage retrievePackage(String id);
}
