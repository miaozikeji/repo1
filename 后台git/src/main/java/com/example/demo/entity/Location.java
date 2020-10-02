package com.example.demo.entity;

import lombok.Data;

@Data
public class Location {
    private int id;
    private double lng;
    private double lat;

    @Override
    public String toString() {
        return "{\"Location\":{"
                + "\"id\":"
                + id
                + ",\"lng\":"
                + lng
                + ",\"lat\":"
                + lat
                + "}}";

    }
}
