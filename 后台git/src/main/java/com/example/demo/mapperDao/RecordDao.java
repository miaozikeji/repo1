package com.example.demo.mapperDao;

import com.example.demo.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface RecordDao {
    public List<Record> selectRecordByphone(String phone);

    public void insertRecord(Record record);

    public void updateRecord(Record record);

    public void deletecrecord(List<Record> list);

}
