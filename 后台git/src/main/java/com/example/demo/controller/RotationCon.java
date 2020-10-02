package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static com.example.demo.util.Constant.FILEURL;

/**
 * 这个类用来处理轮播图的图片资源
 */
@CrossOrigin
@RestController
public class RotationCon {

    @GetMapping("/rotationConList")
    public List rotationConList(){
        File file = new File(FILEURL+"\\Rotation");
        File[] tempList = file.listFiles();
        List<String> fileNameList=new ArrayList();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                fileNameList.add(tempList[i].getName());
            }
        }
        return fileNameList;
    }

    @GetMapping("/rotationConshopList")
    public List rotationConshopList(){
        File file = new File(FILEURL+"\\Rotation\\shop");
        File[] tempList = file.listFiles();
        List<String> fileNameList=new ArrayList();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                fileNameList.add(tempList[i].getName());
            }
        }
        return fileNameList;
    }
}
