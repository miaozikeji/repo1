package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Barrage;
import com.example.demo.entity.Classitem;
import com.example.demo.entity.Fabulous;
import com.example.demo.entity.cClass;
import com.example.demo.mapperDao.ClassDao;
import com.example.demo.mapperDao.FabulousDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
public class ClassCon {
    @Autowired
    ClassDao classDao;
    @Autowired
    FabulousDao fabulousDao;

    @GetMapping("/selectClassById/{id}")
    public HashMap selectClassById(@PathVariable int id) {
        cClass classes = classDao.selectClassById(id);
        List<Classitem> classitems = classDao.selectClassitemByEid((int) classes.getId());
        HashMap list = new HashMap();
        list.put("class", classes);
        list.put("classitem", classitems);
        return list;
    }

    @GetMapping("/selectClassByType/{type}")
    public List selectClassByType(@PathVariable String type) {
        List list = classDao.selectClassByType(type);
        return list;
    }

    @RequestMapping(value = "/insertBarrage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void insertComment(@RequestBody JSONObject jsonObject) throws IOException {
        Barrage barrage = new Barrage();
        barrage.setEid(jsonObject.getInteger("eid"));
        barrage.setText(jsonObject.getString("text"));
        barrage.setShijian(jsonObject.getInteger("shijian"));
        barrage.setSize(jsonObject.getString("size"));
        barrage.setColor(jsonObject.getString("color"));
        classDao.insertBarrage(barrage);
    }

    @GetMapping("/selectBarrage/{eid}")
    public List<Barrage> selectBarrage(@PathVariable int eid) {
        return classDao.selectBarrage(eid);
    }

    @GetMapping("/selectFabulousByTypeEid/{id}")
    public int selectFabulousByTypeEid(@PathVariable int id) {
        Fabulous f = new Fabulous();
        f.setEid(id);
        f.setType(3);
        return fabulousDao.selectFabulousByTypeEid(f).size();
    }

    @GetMapping("/searchclass/{text}")
    public List searchclass(@PathVariable String text) {
        List<cClass> list = classDao.searchclass(text);
        List<Classitem> list2 = classDao.searchclassitem(text);
        for (int i = 0; i < list2.size(); i++) {
            list.add(classDao.selectClassById((int) list2.get(i).getEid()));
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setClassitem(classDao.selectClassitemByEid((int) list.get(i).getId()));
        }
        return list;
    }


}
