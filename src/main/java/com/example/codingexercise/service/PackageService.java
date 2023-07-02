package com.example.codingexercise.service;

import com.example.codingexercise.exception.BadRequestException;
import com.example.codingexercise.gateway.CurrencyConverterGateway;
import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.model.Product;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.response.ProductPackage;
import com.example.codingexercise.response.ServerResponse;
import com.example.codingexercise.service.contracts.IPackageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import static java.util.stream.Collectors.toList;

@Component
public class PackageService implements IPackageService {
    private ProductServiceGateway productServiceGateway;
    private CurrencyConverterGateway currencyConverterGateway;

    private PackageRepository packageRepository;
    public PackageService(ProductServiceGateway productServiceGateway, CurrencyConverterGateway currencyConverterGateway, PackageRepository packageRepository){
        this.productServiceGateway = productServiceGateway;
        this.currencyConverterGateway = currencyConverterGateway;
        this.packageRepository = packageRepository;
    }
    @Override
    public ServerResponse<ProductPackage> createPackage(com.example.codingexercise.request.ProductPackage productPackage) {
        ProductPackage packageRecord = this.UpsertPackage(productPackage,null);
        ServerResponse<ProductPackage> serverResponse = new ServerResponse<ProductPackage>();
        serverResponse.setValues(packageRecord);
        serverResponse.setDescription("Package Added Successfully");
        return serverResponse;
    }

    @Override
    public ServerResponse<ProductPackage> updatePackage(com.example.codingexercise.request.ProductPackage productPackage, String id) {
        ProductPackage packageRecord = this.UpsertPackage(productPackage,id);
        ServerResponse<ProductPackage> serverResponse = new ServerResponse<ProductPackage>();
        serverResponse.setValues(packageRecord);
        serverResponse.setDescription("Package Updated Successfully");
        return serverResponse;
    }

    @Override
    public ServerResponse<Collection<ProductPackage>> getPackages() {
        ServerResponse<Collection<ProductPackage>> serverResponse = new ServerResponse<Collection<ProductPackage>>();
        serverResponse.setValues(this.packageRepository.getPackages());
        serverResponse.setDescription("Packages Retrieved Successfully");
        return serverResponse;
    }

    @Override
    public ServerResponse deletePackage(String id) {
        ServerResponse<?> serverResponse = new ServerResponse<>();
        serverResponse.setDescription("Package deleted successfully");
        this.packageRepository.deletePackage(id);
        return serverResponse;
    }

    @Override
    public ServerResponse<ProductPackage> retrievePackage(String id) {
        ProductPackage packageRecord = this.packageRepository.retrievePackage(id);
        ServerResponse<ProductPackage> serverResponse = new ServerResponse<ProductPackage>();
        serverResponse.setValues(packageRecord);
        serverResponse.setDescription("Package Retrieved Successfully");
        return serverResponse;
    }

    private ProductPackage UpsertPackage(com.example.codingexercise.request.ProductPackage productPackageRequest, String id){

        if(productPackageRequest.getCurrency() == null){
            productPackageRequest.setCurrency("USD"); // use USD if currency is not passed
        }
        String currencyCode = this.getCurrency(productPackageRequest.getCurrency()); // checking the validity of the currency code before calling our api
        if(currencyCode == null)
        {
            throw new BadRequestException("Invalid currency code passed!");
        }
        // Asynchronous execution
        List<CompletableFuture<Product>> allFutures = new ArrayList<>();
        for (int i = 0; i < productPackageRequest.getProductIds().size(); i++) {
            try{
                allFutures.add(this.productServiceGateway.getProduct(productPackageRequest.getProductIds().get(i)));
            }catch(HttpClientErrorException ex){
                if(ex instanceof HttpStatusCodeException) {
                   if(((HttpStatusCodeException) ex).getStatusCode().equals(HttpStatus.NOT_FOUND)){
                       throw new BadRequestException(String.format("Invalid ProductId %s was provided", productPackageRequest.getProductIds().get(i)));
                   }
                }

            }

        }
        long start = System.currentTimeMillis();
        Double totalPrice = 0.0;

        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();
        long end = System.currentTimeMillis();
        System.out.printf("The future operation took %s ms%n", end - start);

        List<Product> products = new ArrayList<Product>();

        for (int i = 0; i < productPackageRequest.getProductIds().size(); i++) {
            Product product = null;
            try {
                product = allFutures.get(i).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            if(product.getName() == null){
                throw new BadRequestException(String.format("Product with id %s does not exist", productPackageRequest.getProductIds().get(i)));
            }
            totalPrice += product.getUsdPrice(); // we need to get the total price of all the products in package
            products.add(product);
        }

        ProductPackage productPackageRecord = new ProductPackage(productPackageRequest.getName(), productPackageRequest.getDescription(),products);
        if(productPackageRequest.getCurrency() != "USD") // there is no point calling our converter if the currency we converting to is USD because out product price has usdPrice
        {
            var currencyDetails = this.currencyConverterGateway.getCurrency(totalPrice,"USD", productPackageRequest.getCurrency());
            if(currencyDetails.getRates() != null)
            {
                Double amount =(double)currencyDetails.getRates().get(productPackageRequest.getCurrency());
                productPackageRecord.setPrice(amount);

            }
        }
        productPackageRecord.setCurrency(productPackageRequest.getCurrency());

        if(id == null)
        {
            productPackageRecord.setId(UUID.randomUUID().toString());
           return this.packageRepository.createPackage(productPackageRecord);
        }
        productPackageRecord.setId(id);
         return this.packageRepository.updatePackage(productPackageRecord, id);
    }
    private String getCurrency(String currencyCode){
        HashMap<String, String> currencyMap = new HashMap<String, String>(); //for now only few currency are supported by our service. this impl can be modified
        currencyMap.put("AUD","Australian Dollar");
        currencyMap.put("BGN","Bulgarian Lev");
        currencyMap.put("BRL","Brazilian Real");
        currencyMap.put("CAD","Canadian Dollar");
        currencyMap.put("CHF","Swiss Franc");
        currencyMap.put("CNY","Chinese Renminbi Yuan");
        currencyMap.put("CZK","Czech Koruna");
        currencyMap.put("DKK","Danish Krone");
        currencyMap.put("USD","United States Dollar");
        currencyMap.put("EUR","Euro");
        currencyMap.put("GBP","British Pound");
        return currencyMap.get(currencyCode);

    }
}
