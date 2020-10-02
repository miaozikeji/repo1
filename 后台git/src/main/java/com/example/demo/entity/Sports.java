package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Sports {

    private long id;
    private long eid;
    private long Y;
    private long M;
    private long D;
    private long num;
    private List<Object> data;
}
