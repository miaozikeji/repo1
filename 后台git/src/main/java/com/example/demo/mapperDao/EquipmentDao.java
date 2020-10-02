package com.example.demo.mapperDao;

import com.example.demo.entity.Equipment;
import com.example.demo.entity.Sports;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component

public interface EquipmentDao {
    public void insertEquipment(Equipment equipment);
    public List<Equipment> selectEquipment(String phone);
    public void insertSports(Sports sports);
    public List<Sports> selectSport(int eid,int M);
    public Equipment selectEquipmentByid(int id);

}
