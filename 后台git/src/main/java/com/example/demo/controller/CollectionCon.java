package com.example.demo.controller;

import com.example.demo.entity.Collection;
import com.example.demo.entity.Dynamic;
import com.example.demo.entity.Shop;
import com.example.demo.entity.cClass;
import com.example.demo.mapperDao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.demo.util.Constant.FILEURL;

@CrossOrigin
@RestController
public class CollectionCon {
    @Autowired
    CollectionDao collectionDao;
    @Autowired
    UserDao userDao;
    @Autowired
    DynamicDao dynamicDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    ClassDao classDao;


    public List<Collection> selectCollectionByuser(@PathVariable String user) {
        return collectionDao.selectCollectionByuser(user);
    }

    @GetMapping("/insertCollection/{type}/{user}/{eid}")
    public void insertCollection(@PathVariable int type, @PathVariable int eid, @PathVariable String user) {
        Collection c = new Collection();
        c.setEid(eid);
        c.setType(type);
        c.setUser(user);
        collectionDao.insertCollection(c);
    }

    @GetMapping("/slectCollection/{type}/{user}/{eid}")
    public Collection slectCollectin(@PathVariable int type, @PathVariable int eid, @PathVariable String user) {
        Collection c = new Collection();
        c.setUser(user);
        c.setType(type);
        c.setEid(eid);
        if (collectionDao.slectCollection(c) != null) {
            c.setIs(1);
            c.setId(collectionDao.slectCollection(c).getId());
        } else {
            c.setIs(0);
        }
        c.setNum(collectionDao.selectCollectionByeid(eid).size());
        return c;
    }

    @GetMapping("/delectCollection/{id}")
    public void delectCollection(@PathVariable int id) {
        Collection c = new Collection();
        c.setId(id);
        List<Collection> list = new ArrayList<>();
        list.add(c);
        collectionDao.delectCollection(list);
    }

    @GetMapping("/selectCollectionbytype/{type}/{user}")
    public List<Collection> selectCollectionbytype(@PathVariable int type, @PathVariable String user) {
        Collection c = new Collection();
        c.setType(type);
        c.setUser(user);
        List<Collection> list = new ArrayList<>();
        if (type == 1) {
            list = collectionDao.selectCollectionbytype(c);
            for (int i = 0; i < list.size(); i++) {
                Dynamic d = dynamicDao.selectDynamicByid((int) list.get(i).getEid());
                d.setNickname(userDao.selectUserByPhone(d.getPhone()).getNickname());
                list.get(i).setData(d);
            }
        }if (type==2){
            list = collectionDao.selectCollectionbytype(c);
            for (int i = 0; i < list.size(); i++) {
                Shop s=shopDao.selectShopById((int) list.get(i).getEid());
//                    File file = new File(FILEURL + "\\shop\\" + list.get(i).getEid());
//                    File[] tempList = file.listFiles();
//                    List<String> fileNameList = new ArrayList();
//                    if (tempList==null){
//                        break;
//                    }
//                    for (int j = 0; j < tempList.length; j++) {
//                        if (tempList[j].isFile()) {
//                            fileNameList.add(tempList[j].getName());
//                        }
//                    }
//                    s.setUrl(fileNameList);
                list.get(i).setData(s);
            }
        }
        if (type == 3) {
            list = collectionDao.selectCollectionbytype(c);
            for (int i = 0; i < list.size(); i++) {
                cClass cClass=classDao.selectClassById((int) list.get(i).getEid());
                cClass.setClassitem(classDao.selectClassitemByEid((int) cClass.getId()));
               list.get(i).setData(cClass);
            }
        }
        return list;
    }

}
