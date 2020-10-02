package com.example.demo.mapperDao;

import com.example.demo.entity.Dynamic;
import com.example.demo.entity.Pettype;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PettypeDao {
    public List<Pettype> selectPettype();

}
