package com.example.demo.mapperDao;

import com.example.demo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentDao {
    //根据动态id查询评论
    public List<Comment> selectBydynamicid(int dynamicid);
    //添加评论
    public void insertComment(Comment comment);
    //删除评论
    public void deleteComment(List<Comment> list);
}
