package com.example.codingexercise;


import com.example.codingexercise.request.ProductPackage;
import com.example.codingexercise.response.ServerResponse;
import com.example.codingexercise.service.PackageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PackageControllerTests {

    private final PackageService packageService;

    @Autowired
    PackageControllerTests(PackageService packageService) {
        this.packageService = packageService;
    }

    @Test
    void createPackage() {
        ProductPackage  productPackageRequest = new ProductPackage();
        productPackageRequest.setName("Package 1");
        productPackageRequest.setDescription("My first Package");
        productPackageRequest.setProductIds(List.of("VqKb4tyj9V6i"));
        // remember here that we didnt set the currency
		ServerResponse<com.example.codingexercise.response.ProductPackage> createdPackage = this.packageService.createPackage(productPackageRequest);
        assertEquals("Package Added Successfully", createdPackage.getDescription());
        assertNotNull(createdPackage.getDescription());

        assertEquals("Package 1", createdPackage.getValues().getName());
        assertEquals("USD", createdPackage.getValues().getCurrency()); // validate that USD was used as default
        assertEquals(1, createdPackage.getValues().getProducts().size());

        ServerResponse<com.example.codingexercise.response.ProductPackage> productPackage = this.packageService.retrievePackage(createdPackage.getValues().getId());
        assertNotNull(productPackage.getDescription(), "Packages Retrieved Successfully");

        ServerResponse<Collection<com.example.codingexercise.response.ProductPackage>> packageResponse = this.packageService.getPackages();
        assertEquals(1, packageResponse.getValues().size()); // the item in the db should  be 1

        // delete the record by id
        ServerResponse<Collection<com.example.codingexercise.response.ProductPackage>> response = this.packageService.deletePackage(productPackage.getValues().getId());
        assertEquals(response.getDescription(), "Package deleted successfully");

        // the item has been deleted , now lets try to check for the item again
        packageResponse = this.packageService.getPackages();
        assertEquals(0, packageResponse.getValues().size()); // the item in the db should  be 1

    }
    @Test
    void updatePackage() {
        ProductPackage  productPackageRequest = new ProductPackage();
        productPackageRequest.setName("Package 2");
        productPackageRequest.setCurrency("EUR");
        productPackageRequest.setDescription("My second Package");
        productPackageRequest.setProductIds(List.of("VqKb4tyj9V6i"));
        ServerResponse<com.example.codingexercise.response.ProductPackage> createdPackage = this.packageService.createPackage(productPackageRequest);
        assertEquals("Package Added Successfully", createdPackage.getDescription() );
        assertNotNull(createdPackage.getDescription() );
        assertEquals("Package 2", createdPackage.getValues().getName());
        assertEquals(1, createdPackage.getValues().getProducts().size());
        productPackageRequest.setCurrency("USD"); // we updated the currency to USD
        ServerResponse<com.example.codingexercise.response.ProductPackage> productPackage = this.packageService.updatePackage(productPackageRequest,createdPackage.getValues().getId());
        assertNotNull(productPackage.getDescription(), "Package Updated Successfully");
        assertEquals("USD", productPackage.getValues().getCurrency()); //validating the new currency
    }



}
