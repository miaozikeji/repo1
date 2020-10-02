package com.example.demo.entity;
//
import lombok.Data;
@Data
public class User {

  private long id;
  private String phone;
  private String pwd;
  private String nickname;
  private String city;
  private String sex;
  private String birthday;
  private String introduce;

  @Override
  public String toString() {
    return "{\"User\":{"
            + "\"id\":"
            + id
            + ",\"phone\":\""
            + phone + '\"'
            + ",\"pwd\":\""
            + pwd + '\"'
            + ",\"nickname\":\""
            + nickname + '\"'
            + ",\"city\":\""
            + city + '\"'
            + ",\"sex\":\""
            + sex + '\"'
            + ",\"birthday\":\""
            + birthday + '\"'
            + ",\"introduce\":\""
            + introduce + '\"'
            + "}}";

  }
}
