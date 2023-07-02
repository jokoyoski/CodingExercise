package com.example.codingexercise.response;

public class ServerResponse<T> {

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public T getValues() {
        return values;
    }

    public void setValues(T values) {
        this.values = values;
    }

    private String Description;
    private T values;
}
