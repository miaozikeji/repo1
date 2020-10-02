package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Record;
import com.example.demo.mapperDao.RecordDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
public class RecordCon {
    @Autowired
    RecordDao recordDao;

    @RequestMapping(value = "/insertRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void insertRecord(@RequestBody JSONObject jsonObject) {
        Record record = new Record();
        record.setContent(jsonObject.getString("content"));
        record.setPhone(jsonObject.getString("phone"));
        record.setText(jsonObject.getString("text"));
        record.setTimestamp(jsonObject.getString("timestamp"));
        recordDao.insertRecord(record);
    }

    @GetMapping("/selectRecordByphone/{phone}")
    public List selectRecordByphone(@PathVariable String phone) throws ParseException {
        List<Record> list = recordDao.selectRecordByphone(phone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = sdf.parse(list.get(0).getTimestamp());
        Collections.sort(list, new Comparator<Record>() {
            @SneakyThrows
            @Override
            public int compare(Record r1, Record r2) {
                Date date = sdf.parse(r1.getTimestamp());
                Date date2 = sdf.parse(r2.getTimestamp());
                int compareTo = date.compareTo(date2);

                if (compareTo ==1) {
                    return 1;
                } else if (compareTo==-1 ) {
                    return -1;
                }
                return 0;
            }
        });
        return list;
    }

    @RequestMapping(value = "/updateRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void updateRecord(@RequestBody JSONObject jsonObject) {
        Record record = new Record();
        record.setContent(jsonObject.getString("content"));
        record.setId(jsonObject.getInteger("id"));
        record.setText(jsonObject.getString("text"));
        record.setTimestamp(jsonObject.getString("timestamp"));
        System.out.println(record);
        recordDao.updateRecord(record);
    }

    @GetMapping("/deletecrecord/{id}")
    public void deletecrecord(@PathVariable int id) {
        Record r = new Record();
        r.setId(id);
        List list = new ArrayList();
        list.add(r);
        recordDao.deletecrecord(list);
    }
}
