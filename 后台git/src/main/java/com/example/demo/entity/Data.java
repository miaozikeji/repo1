package com.example.demo.entity;
@lombok.Data
public class Data {
    private Object data;

    @Override
    public String toString() {
        return "{\"Data\":{"
                + "\"data\":"
                + data
                + "}}";

    }
}
