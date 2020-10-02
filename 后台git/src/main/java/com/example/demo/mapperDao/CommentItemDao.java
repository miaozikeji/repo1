package com.example.demo.mapperDao;

import com.example.demo.entity.CommentItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentItemDao {

    //查询子评论根据评论的id
    public List<CommentItem> selectCommentItemByCommenId(int commentid);
    //添加子评论
    public void insertCommentItem(CommentItem commentItem);
    //删除子评论根据id
    public void delectCommentItemById(List<CommentItem> commentItems);
}
