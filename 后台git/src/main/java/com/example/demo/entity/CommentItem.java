package com.example.demo.entity;

import lombok.Data;

@Data
public class CommentItem {

    private long id;
    private long commentid;
    private String userphone;
    private String text;
    private String nickname;

    @Override
    public String toString() {
        return "{\"CommentItem\":{"
                + "\"id\":"
                + id
                + ",\"commentid\":"
                + commentid
                + ",\"userphone\":\""
                + userphone + '\"'
                + ",\"text\":\""
                + text + '\"'
                + ",\"nickname\":\""
                + nickname + '\"'
                + "}}";

    }
}
