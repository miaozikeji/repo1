package com.example.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * 动态实体类
 */
@Data
public class Dynamic {
    private long id;
    private String phone;
    private String text;
    private long type;
    private long browse;
    private int pettypeid;
    private String city;
    private List<String> url;
    private String nickname;
    private int fabulous;
    private String releasetime;

    @Override
    public String toString() {
        return "{\"Dynamic\":{"
                + "\"id\":"
                + id
                + ",\"phone\":\""
                + phone + '\"'
                + ",\"text\":\""
                + text + '\"'
                + ",\"type\":"
                + type
                + ",\"browse\":"
                + browse
                + ",\"pettypeid\":"
                + pettypeid
                + ",\"city\":\""
                + city + '\"'
                + ",\"url\":"
                + url
                + ",\"nickname\":\""
                + nickname + '\"'
                + ",\"fabulous\":"
                + fabulous
                + ",\"releasetime\":\""
                + releasetime + '\"'
                + "}}";

    }
}
