package com.example.demo.entity;

import lombok.Data;

@Data
public class Attention {
    private int id;
    private String t;
    private String f;

    @Override
    public String toString() {
        return "{\"Attention\":{"
                + "\"id\":"
                + id
                + ",\"t\":\""
                + t + '\"'
                + ",\"f\":\""
                + f + '\"'
                + "}}";

    }
}
