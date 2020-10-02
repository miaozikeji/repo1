package com.example.demo.mapperDao;

import com.example.demo.entity.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CollectionDao {
    public List<Collection> selectCollectionByuser(String user);
    public void insertCollection(Collection collection);
    public Collection slectCollection(Collection collection);
    public void delectCollection(List<Collection> list);
    public List<Collection > selectCollectionByeid(int eid);
    public List<Collection> selectCollectionbytype(Collection c);
}
