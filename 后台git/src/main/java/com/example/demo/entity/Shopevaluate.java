package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Shopevaluate {

  private long id;
  private String user;
  private String time;
  private long fraction;
  private String text;
  private long eid;
  private User  users;
  private List<String> url;



}
