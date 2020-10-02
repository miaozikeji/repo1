package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShopCart {

  private long id;
  private long eid;
  private String user;
  private long choice;
  private Shop shops;
  private long num;


}
