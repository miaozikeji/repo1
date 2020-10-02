package com.example.demo.mapperDao;

import com.example.demo.entity.Userpettype;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserpettypeDao {
    public List<Userpettype> selectUserpettype(String user);
}
