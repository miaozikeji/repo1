package com.example.demo.controller;

import com.example.demo.entity.Fabulous;
import com.example.demo.mapperDao.FabulousDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class FabulousCon {
    @Autowired
    FabulousDao fabulousDao;
    @GetMapping("/selectFabulous/{type}/{user}/{eid}")
    public Fabulous selectFabulous(@PathVariable int type,@PathVariable String user,@PathVariable int eid){
        Fabulous f=new Fabulous();
        f.setEid(eid);
        f.setType(type);
        f.setUser(user);
        if (fabulousDao.selectFabulous(f)!=null){
            f.setIs(1);
            f.setId(fabulousDao.selectFabulous(f).getId());
        }else {
            f.setIs(0);
        }
        f.setNum(fabulousDao.selectFabulousByTypeEid(f).size());
        return f;
    }

    @GetMapping("/insertFabulous/{type}/{user}/{eid}")
    public void insertFabulous(@PathVariable int type,@PathVariable String user,@PathVariable int eid){
        Fabulous f=new Fabulous();
        f.setEid(eid);
        f.setType(type);
        f.setUser(user);
        fabulousDao.insertFabulous(f);
    }

    @GetMapping("/delectFabulous/{id}")
    public void delectFabulous(@PathVariable int id){
        Fabulous f=new Fabulous();
        f.setId(id);
        List<Fabulous> list=new ArrayList<>();
        list.add(f);
        fabulousDao.delectFabulous(list);
    }
}
