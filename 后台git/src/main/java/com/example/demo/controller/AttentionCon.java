package com.example.demo.controller;

import com.example.demo.entity.Attention;
import com.example.demo.entity.Data;
import com.example.demo.entity.User;
import com.example.demo.mapperDao.AttentionDao;
import com.example.demo.mapperDao.UserDao;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class AttentionCon {

    @Autowired
    AttentionDao attentionDao;
    @Autowired
    UserDao userDao;

    //查询两个用户之间是否存在关注
    @GetMapping("/selectAttention/{f}/{t}")
    public int selectAttentionByphone(@PathVariable("f") String f, @PathVariable("t") String t) {
        Attention attention = new Attention();
        attention.setF(f);
        attention.setT(t);
        if (attentionDao.selectAttention(attention) != null) {
            return 1;
        } else {
            return 0;
        }
    }

    //添加关注
    @GetMapping("/insertAttention/{f}/{t}")
    public void insertAttention(@PathVariable("f") String f, @PathVariable("t") String t) {
        Attention attention = new Attention();
        attention.setF(f);
        attention.setT(t);
        attentionDao.insertAttention(attention);
    }

    @GetMapping("/deleteAttention/{f}/{t}")
    public void deleteAttention(@PathVariable("f") String f, @PathVariable("t") String t) {
        Attention attention = new Attention();
        attention.setF(f);
        attention.setT(t);
        List<Attention> attentionList = new ArrayList<>();
        attentionList.add(attention);
        attentionDao.deleteAttention(attentionList);
    }

    @GetMapping("/selectAttentionByuser/{user}")
    public List<User> selectAttentionByuser(@PathVariable String user) {
        Attention attention = new Attention();
        attention.setF(user);
        List<Attention> attentions = attentionDao.selectAttentionByphone(attention);
        List<User> users = new ArrayList<>();
        for (int i = 0; i < attentions.size(); i++) {
            User u=userDao.selectUserByPhone(attentions.get(i).getT());
            u.setPwd("");
            users.add(u);
        }
        return  users;
    }

    @GetMapping("/selectFansByuser/{user}")
    public List<User> selectFansByuser(@PathVariable String user) {
        Attention attention = new Attention();
        attention.setT(user);
        List<Attention> attentions = attentionDao.selectAttentionByTo(attention);
        List<User> users = new ArrayList<>();
        for (int i = 0; i < attentions.size(); i++) {
            User u=userDao.selectUserByPhone(attentions.get(i).getF());
            u.setPwd("");
            users.add(u);
        }
        return  users;
    }
}
