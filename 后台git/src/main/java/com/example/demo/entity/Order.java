package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Order {

  private long id;

  private long state;
  private String user;
  private String pay;
  private long add;
  private double paynum;
  private String time;
  private List<Orderitem>orderitems;

}
