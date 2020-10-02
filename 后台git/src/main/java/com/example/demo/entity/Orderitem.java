package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Orderitem {

  private long id;
  private long shopid;
  private long choicesid;
  private long orderid;
  private long num;
  private Shop shops;
  private Shopchoice shopchoices;


}
