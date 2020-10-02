package com.example.demo.mapperDao;

import com.example.demo.entity.Barrage;
import com.example.demo.entity.Classitem;
import com.example.demo.entity.cClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ClassDao {


    public cClass selectClassById(int id);

    public List<cClass> selectClassByType(String type);

    public List<Classitem> selectClassitemByEid(int eid);

    public List<Barrage> selectBarrage(int  eid);

    public void insertBarrage(Barrage attention);

    public List<cClass> searchclass(String text);
    public List<Classitem> searchclassitem(String text);


}
