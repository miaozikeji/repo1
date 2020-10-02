package com.example.demo.mapperDao;

import com.example.demo.entity.Attention;
import com.example.demo.entity.Dynamic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface DynamicDao {
    //添加动态
    public void insertDynamic(Dynamic dynamic);
    //遍历所有动态
    public List<Dynamic> selectDynamic();
    //查询动态根据电话号码
    public List<Dynamic> selectDynamicByiphon(String phone, int limit);
    //查询动态根据id
    public Dynamic selectDynamicByid(int id);
    public void insertbrowse(int browse, int id);
    public List<Dynamic> selectDynamicBytype(int pettypeid);
    public List<Dynamic> selectDynamicByLocal(String city);
    public List<Dynamic> selectDynamicByuser(String phone);
    public void delectDynamic(List<Dynamic> list);

}
