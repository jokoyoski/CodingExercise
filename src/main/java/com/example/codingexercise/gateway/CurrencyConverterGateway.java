package com.example.codingexercise.gateway;

import com.example.codingexercise.gateway.dto.CurrencyConverter;
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
public class CurrencyConverterGateway extends AbstractService<CurrencyConverter> {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterGateway.class);
    private final RestTemplate restTemplate;
    Environment environment;

    public CurrencyConverterGateway(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment=environment;
    }

    public CurrencyConverter getCurrency(Double amount, String from, String to) {
        final String url = String.format("%s/latest?amount=%s&from=%s&to=%s",this.environment.getProperty("currency-converter-service"),amount,from,to);
        return super.fetchAll(url, CurrencyConverter.class);
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
