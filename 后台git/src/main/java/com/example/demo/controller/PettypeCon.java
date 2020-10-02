package com.example.demo.controller;

import com.example.demo.entity.Pettype;
import com.example.demo.mapperDao.PettypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 话题
 */
@CrossOrigin
@RestController
public class PettypeCon {
    @Autowired
    PettypeDao pettypeDao;

    @GetMapping("/selectpettype")
    public List<Pettype> selectpettype(){
        return pettypeDao.selectPettype();
    }
}
