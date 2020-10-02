package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Equipment;
import com.example.demo.entity.Location;
import com.example.demo.mapperDao.EquipmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

import static com.example.demo.websocket.WebSocketServer.sendInfo;

@CrossOrigin
@RestController
public class LocationCon {
@Autowired
    EquipmentDao equipmentDao;
    @GetMapping("/Location/{id}/{lng}/{lat}")
    public void Location(@PathVariable int id, @PathVariable double lng, @PathVariable double lat) throws IOException {
        Equipment equipment= equipmentDao.selectEquipmentByid(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("toUserId", equipment.getPhone());
        jsonObject.put("fromUserId", id);
        jsonObject.put("lat", lat);
        jsonObject.put("lng", lng);
        jsonObject.put("name",equipment.getName());
        sendInfo(jsonObject.toJSONString(),equipment.getPhone());
    }
}
