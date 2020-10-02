package com.example.demo.entity;

import lombok.Data;

@Data
public class Msg {

  private long id;
  private String fromUserId;
  private String toUserId;
  private long type;
  private String contentText;
  private String time;

  @Override
  public String toString() {
    return "{\"Msg\":{"
            + "\"id\":"
            + id
            + ",\"fromUserId\":\""
            + fromUserId + '\"'
            + ",\"toUserId\":\""
            + toUserId + '\"'
            + ",\"type\":"
            + type
            + ",\"contentText\":\""
            + contentText + '\"'
            + ",\"time\":\""
            + time + '\"'
            + "}}";

  }
}
