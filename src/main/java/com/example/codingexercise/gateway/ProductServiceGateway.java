package com.example.codingexercise.gateway;
import com.example.codingexercise.model.Product;
import com.example.codingexercise.service.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductServiceGateway extends AbstractService<Product> {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterGateway.class);
    private final RestTemplate restTemplate;
    Environment environment;

    public ProductServiceGateway(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment=environment;
    }

    public CompletableFuture<Product> getProduct(String id) {
        final String url = String.format("%s/api/v1/products/%s",this.environment.getProperty("product-service"),id);
        final List<ClientHttpRequestInterceptor> interceptors = Collections.singletonList(
                this.getBasicAuthenticationInterceptor(this.environment.getProperty("product-service-user"),this.environment.getProperty("product-service-password"))
        );
        return CompletableFuture.completedFuture(super.fetchAll(url, Product.class, interceptors));
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
