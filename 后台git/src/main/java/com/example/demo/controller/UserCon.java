package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.mapperDao.UserDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.demo.util.Constant.FILEURL;

/**
 * 用户操作
 */
@CrossOrigin
@RestController
public class UserCon {
    @Autowired
    UserDao userDao;

    @GetMapping("/selectusers")
    public List<User> selectusers(){
        return userDao.selectusers();
    }

    //照片墙上传
    @RequestMapping(value = "/userPhoto/{user}", method = RequestMethod.POST)
    @ResponseBody
    public void userPhoto(@Param("file") MultipartFile file,
                              @Param("user") String user) throws IOException {
        String path = FILEURL + "\\headportrait\\"+user+".jpg" ;
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

    @RequestMapping(value = "/updateuser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void updateuser(@RequestBody JSONObject jsonObject) throws IOException {

        User user=new User();
        user.setBirthday(jsonObject.getString("birthday"));
        user.setCity(jsonObject.getString("city"));
        user.setIntroduce(jsonObject.getString("introduce"));
        user.setNickname(jsonObject.getString("nickname"));
        user.setSex(jsonObject.getString("sex"));
        user.setPhone(jsonObject.getString("user"));
        userDao.updateuser(user);
    }
    @GetMapping("/selectByphone/{user}")
    public User selectByphone(@PathVariable String user){
        User u=userDao.selectUserByPhone(user);
        u.setPwd("");
        return u;
    }
}
