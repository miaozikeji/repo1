package com.example.demo.entity;

import lombok.Data;

@Data
public class Pettype {

    private long id;
    private String type;

    @Override
    public String toString() {
        return "{\"Pettype\":{"
                + "\"id\":"
                + id
                + ",\"type\":\""
                + type + '\"'
                + "}}";

    }
}
