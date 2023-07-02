package com.example.codingexercise.repository;

import com.example.codingexercise.exception.BadRequestException;
import com.example.codingexercise.exception.DuplicateException;
import com.example.codingexercise.response.ProductPackage;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PackageRepository implements  IPackageRepository {

    private final HashMap<String, ProductPackage> productPackageDb = new HashMap<String, ProductPackage>();

    @Override
    public ProductPackage createPackage(ProductPackage productPackage) {
        productPackageDb.put(productPackage.getId(),productPackage);
        return productPackage;
    }

    @Override
    public ProductPackage updatePackage(ProductPackage productPackage, String id) {
       ProductPackage packageRecord = productPackageDb.get(id);
       if(packageRecord == null){
           throw new BadRequestException("Package with this Id does not exist");
       }
        productPackageDb.put(id,productPackage);
        return productPackage;
    }

    @Override
    public Collection<ProductPackage> getPackages() {
        Collection<ProductPackage> values = productPackageDb.values();
        return values;
    }

    @Override
    public void deletePackage(String id) {
        ProductPackage packageRecord = productPackageDb.get(id);
        if(packageRecord == null){
            throw new BadRequestException("Package with this Id does not exist");
        }
        productPackageDb.remove(id);
    }

    @Override
    public ProductPackage retrievePackage(String id) {
        ProductPackage packageRecord = productPackageDb.get(id);
        if(packageRecord == null){
            throw new BadRequestException("Package with this Id does not exist");
        }
        return packageRecord;
    }

}
