package com.example.demo.controller;

import com.example.demo.entity.Equipment;
import com.example.demo.entity.Sports;
import com.example.demo.mapperDao.EquipmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class EquipmentCon {
    @Autowired
    EquipmentDao equipmentDao;

    @GetMapping("/insertEquipment/{type}/{phone}/{name}")
    public void insertEquipment(@PathVariable int type, @PathVariable String phone, @PathVariable String name) {
        Equipment equipment = new Equipment();
        equipment.setPhone(phone);
        equipment.setName(name);
        equipment.setType(type);
        equipmentDao.insertEquipment(equipment);
    }

    @GetMapping("/selectEquipment/{phone}")
    public List<Equipment> selectEquipment(@PathVariable  String phone) {
        List<Equipment> list = equipmentDao.selectEquipment(phone);
        return list;
    }

    @GetMapping("/insertSports/{eid}/{num}")
    public void insertSports(@PathVariable int eid, @PathVariable int num) {
        Sports sports = new Sports();
        sports.setEid(eid);
        Calendar c = Calendar.getInstance();
        sports.setY(c.get(Calendar.YEAR));
        sports.setM(c.get(Calendar.MONTH)+1);
        for (int i=0;i<31;i++){
            int x = (int)(Math.random()*(5000-1000)+1000+1);
            sports.setNum(x);
            sports.setD(i+1);
            equipmentDao.insertSports(sports);
        }
    }

    @GetMapping("/selectSport/{eid}/{m}")
    public List<Object> selectSport(@PathVariable int eid, @PathVariable int m) {
        List<Sports> list = equipmentDao.selectSport(eid, m);
        if (m == 1) {
            m = 12;
        }else {
            m--;
        }
        List<Sports> list2 = equipmentDao.selectSport(eid, m);
        List<Object> list3 = new ArrayList<>();

        for (int i = 0; i < 31; i++) {
            HashMap h = new HashMap();
            h.put("日期", i + 1 + "/31");
            int index = -1;
            int index2 = -1;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getD() == i + 1) {
                    index=j;
                    break;
                }
            }
            for (int j = 0; j < list2.size(); j++) {
                if (list2.get(j).getD() == i + 1) {
                    index2=j;
                    break;
                }
            }
            if (index!=-1){
                h.put("本月数据", list.get(index).getNum());
            }else {
                h.put("本月数据","");
            }
            if (index2!=-1){
                h.put("上月数据", list2.get(index2).getNum());
            }else {
                h.put("上月数据","");
            }
            list3.add(h);
        }

        return list3;
    }
}

