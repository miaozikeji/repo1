package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

@Data
public class Userpettype {

  private long id;
  private String user;
  private long pettype;

  @Override
  public String toString() {
    return "{\"Userpettype\":{"
            + "\"id\":"
            + id
            + ",\"user\":\""
            + user + '\"'
            + ",\"pettype\":"
            + pettype
            + "}}";

  }
}
