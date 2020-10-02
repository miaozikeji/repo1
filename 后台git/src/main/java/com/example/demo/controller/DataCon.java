package com.example.demo.controller;

import com.example.demo.entity.Attention;
import com.example.demo.entity.Data;
import com.example.demo.entity.Dynamic;
import com.example.demo.mapperDao.AttentionDao;
import com.example.demo.mapperDao.DynamicDao;
import com.example.demo.mapperDao.UserDao;
import com.vdurmont.emoji.EmojiParser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.example.demo.util.Constant.FILEURL;

@CrossOrigin
@RestController
public class DataCon {
    @Autowired
    UserDao userDao;
    @Autowired
    AttentionDao attentionDao;
    @Autowired
    DynamicDao dynamicDao;

    //我的页面的头数据（关注，粉丝，动态）
    @GetMapping("/myData/{user}")
    public Data myData(@PathVariable String user) {
        Data data = new Data();
        Attention attention = new Attention();
        attention.setF(user);
        attention.setT(user);
        HashMap h = new HashMap();
        h.put("attention", attentionDao.selectAttentionByphone(attention).size());
        h.put("fans", attentionDao.selectAttentionByTo(attention).size());
        if (dynamicDao.selectDynamicByuser(user)==null){
            h.put("dynamic", 0);
        }else {
            h.put("dynamic", dynamicDao.selectDynamicByuser(user).size());
        }
        if (userDao.selectUserByPhone(user)==null){
            h.put("nickname", "");
        }else {
            h.put("nickname", userDao.selectUserByPhone(user).getNickname());
        }
        data.setData(h);
        return data;
    }
    //照片墙上传
    @RequestMapping(value = "/photowall", method = RequestMethod.POST)
    @ResponseBody
    public void insertDynamic(@Param("file") MultipartFile file,
                              @Param("user") String user) throws IOException {
        String path = FILEURL + "\\photowall\\"+user+".jpg" ;
            upload(path, file);

    }
    //文件上传
    public String upload(String path, MultipartFile file) {

        File dest = new File(path);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        if (!dest.exists()) {
            dest.mkdirs();
        }
        try {
            file.transferTo(dest); //保存文件
            return "ok";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no";
        }
    }
}
