package com.example.codingexercise.service;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService<T> {
    protected abstract Logger getLogger();

    protected RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    protected T fetchOne(String url, Class<T> clazz) {
        getLogger().info(String.format("Request to: %s", url));

        return getRestTemplate().getForObject(url, clazz);
    }

    protected T fetchAll(String url, Class<T> clazz) {
        return fetchAll(url, clazz, new ArrayList<>());
    }

    protected T fetchAll(String url, Class<T> class1, List<ClientHttpRequestInterceptor> interceptors) {
        getLogger().info(String.format("Request to: %s", url));

        RestTemplate restTemplate = getRestTemplate();

        interceptors.forEach(interceptor -> restTemplate.getInterceptors().add(interceptor));

        ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, class1);
        if (responseEntity != null) {
            return responseEntity.getBody();
        }

        try {
            return class1.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    protected ClientHttpRequestInterceptor getBasicAuthenticationInterceptor(String user, String password) {
        return new BasicAuthenticationInterceptor(user, password);
    }
}
