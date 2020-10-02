package com.example.demo.entity;

import lombok.Data;

import java.util.List;

@Data
public class Comment {

    private long id;
    private String text;
    private long dynamicid;
    private String userphone;
    private String releasetime;
    private List<CommentItem> commentItems;
    private String nickname;
    private Fabulous fabulous;

    @Override
    public String toString() {
        return "{\"Comment\":{"
                + "\"id\":"
                + id
                + ",\"text\":\""
                + text + '\"'
                + ",\"dynamicid\":"
                + dynamicid
                + ",\"userphone\":\""
                + userphone + '\"'
                + ",\"releasetime\":\""
                + releasetime + '\"'
                + ",\"commentItems\":"
                + commentItems
                + ",\"nickname\":\""
                + nickname + '\"'
                + ",\"fabulous\":"
                + fabulous
                + "}}";

    }
}
