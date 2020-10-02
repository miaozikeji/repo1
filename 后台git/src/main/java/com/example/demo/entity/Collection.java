package com.example.demo.entity;

import lombok.Data;

@Data
public class Collection {

    private long id;
    private long type;
    private long eid;
    private String user;
    private int is;
    private int num;
    private Object data;

    @Override
    public String toString() {
        return "{\"Collection\":{"
                + "\"id\":"
                + id
                + ",\"type\":"
                + type
                + ",\"eid\":"
                + eid
                + ",\"user\":\""
                + user + '\"'
                + ",\"is\":"
                + is
                + ",\"num\":"
                + num
                + ",\"data\":"
                + data
                + "}}";

    }
}
