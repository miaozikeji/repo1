package com.example.demo.mapperDao;

import com.example.demo.entity.Fabulous;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FabulousDao {
    //查询点赞根据类型、用户、实体id
    public Fabulous selectFabulous (Fabulous fabulous);
    //取消点赞
    public void delectFabulous(List<Fabulous> list);
    //添加点赞
    public void insertFabulous(Fabulous fabulous);
    //查询点赞根据实体id,type
    public List<Fabulous> selectFabulousByTypeEid(Fabulous fabulous);
}
