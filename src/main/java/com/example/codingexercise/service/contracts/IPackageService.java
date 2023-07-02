package com.example.codingexercise.service.contracts;

import com.example.codingexercise.response.ProductPackage;
import com.example.codingexercise.response.ServerResponse;

import java.util.Collection;
import java.util.List;

public interface IPackageService {
    public ServerResponse<ProductPackage> createPackage(com.example.codingexercise.request.ProductPackage productPackage);
    public ServerResponse<ProductPackage> updatePackage(com.example.codingexercise.request.ProductPackage productPackage, String id);
    public ServerResponse<Collection<ProductPackage>> getPackages();
    public ServerResponse deletePackage(String id);
    public ServerResponse<ProductPackage>  retrievePackage(String id);
}
