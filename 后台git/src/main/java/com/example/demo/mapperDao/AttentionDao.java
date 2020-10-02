package com.example.demo.mapperDao;

import com.example.demo.entity.Attention;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface AttentionDao {
    //查询用户关注的所有人
    public List<Attention> selectAttentionByphone(Attention attention);
    public Attention selectAttention(Attention attention);
    public void insertAttention(Attention attention);
    public void deleteAttention(List<Attention> list);
    public List<Attention> selectAttentionByTo(Attention attention);

}
