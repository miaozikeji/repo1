package com.example.demo.mapperDao;

import com.example.demo.entity.Msg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MsgDao {
    public  void insertMsg(Msg msg);
    public Msg selectMsgid(Msg msg);
    public Msg selectMsgbyid(int id);
    public List<Msg> selectBytofrom(Msg msg);
}
