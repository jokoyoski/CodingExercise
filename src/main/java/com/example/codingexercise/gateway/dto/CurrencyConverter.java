package com.example.codingexercise.gateway.dto;

import java.util.Date;
import java.util.Map;

public class CurrencyConverter {
    private Double amount;
    private String base;
    private Date  date;

    private String message;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map getRates() {
        return rates;
    }

    public void setRates(Map rates) {
        this.rates = rates;
    }

    private Map rates;
}
