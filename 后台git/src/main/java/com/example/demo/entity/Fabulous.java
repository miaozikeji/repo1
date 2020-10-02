package com.example.demo.entity;

import lombok.Data;

@Data
public class Fabulous {

    private long id;
    private long type;
    private String user;
    private long eid;
    private int num;
    private int is;

    @Override
    public String toString() {
        return "{\"Fabulous\":{"
                + "\"id\":"
                + id
                + ",\"type\":"
                + type
                + ",\"user\":\""
                + user + '\"'
                + ",\"eid\":"
                + eid
                + ",\"num\":"
                + num
                + ",\"is\":"
                + is
                + "}}";

    }
}
