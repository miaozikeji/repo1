package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Shop {

  private long id;
  private String name;
  private double cold;
  private double cnew;
  private String text;
  private long type;
  private String highlights;
  private List<String> url;
  private List<Shopchoice> shopchoices;



}
