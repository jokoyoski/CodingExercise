package com.example.codingexercise.controller;

;
import com.example.codingexercise.exception.BadRequestException;
import com.example.codingexercise.response.ProductPackage;
import com.example.codingexercise.response.ServerResponse;
import com.example.codingexercise.service.PackageService;
import io.micrometer.common.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;

@RestController
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/packages")
    public ServerResponse<ProductPackage> create( @Validated @RequestBody  com.example.codingexercise.request.ProductPackage newProductPackage) {
        if (StringUtils.isBlank(newProductPackage.getName())) {
            throw new BadRequestException("Package Name is required");
        }
        if (newProductPackage.getProductIds().size()<=0) {
            throw new BadRequestException("Please provide a product");
        }
        return packageService.createPackage(newProductPackage);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/packages/{id}")
    public ServerResponse<ProductPackage> get(@PathVariable String id) {

        return this.packageService.retrievePackage(id);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/packages")
    public ServerResponse<Collection<ProductPackage>> getAll() {
        return this.packageService.getPackages();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/packages/{id}")
    public ServerResponse<ProductPackage> update(@PathVariable String id, @RequestBody  com.example.codingexercise.request.ProductPackage productPackage) {
        if (StringUtils.isBlank(productPackage.getName())) {
            throw new BadRequestException("Package Name is required");
        }
        if (productPackage.getProductIds().size()<=0) {
            throw new BadRequestException("Please provide a product");
        }
        return this.packageService.updatePackage(productPackage,id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/packages/{id}")
    public ServerResponse delete(@PathVariable String id) {
        return this.packageService.deletePackage(id);
    }

}
