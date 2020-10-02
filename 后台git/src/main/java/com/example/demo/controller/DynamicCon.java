package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.example.demo.mapperDao.*;
import com.vdurmont.emoji.EmojiParser;
import org.apache.ibatis.annotations.Param;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.example.demo.util.Constant.FILEURL;

/**
 * 动态操作
 */
@CrossOrigin
@RestController
public class DynamicCon {
    @Autowired
    DynamicDao dynamicDao;
    @Autowired
    AttentionDao attentionDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FabulousDao fabulousDao;
    @Autowired
    UserpettypeDao userpettypeDao;

    //实现视频文件上传

    @RequestMapping(value = "/insertDynamic", method = RequestMethod.POST)
    @ResponseBody
    public void insertDynamic(@Param("filefm") MultipartFile filefm, @Param("filesp") MultipartFile filesp,
                              @Param("text") String text, @Param("user") String user, @Param("type") int type
            , @Param("pettypeid") int pettypeid, @Param("city") String city) throws IOException {

        Dynamic dynamic = new Dynamic();
        dynamic.setPhone(user);
        dynamic.setType(type);
        dynamic.setBrowse(0);
        dynamic.setCity(city);
        dynamic.setPettypeid(pettypeid);
        dynamic.setText(EmojiParser.parseToAliases(text, EmojiParser.FitzpatrickAction.PARSE));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis());
        dynamic.setReleasetime(formatter.format(date));
        dynamicDao.insertDynamic(dynamic);
        String path = FILEURL + "\\Dynamic\\" + dynamicDao.selectDynamic().get(0).getId();
        if (filefm != null) {
            upload(path, filefm, 0);
            upload(path, filesp, 0);
        } else {
            upload(path, filesp, 1);

        }
    }

    //图片动态的参数上传
    @RequestMapping(value = "/insertDynamic2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int insertDynamic2(@RequestBody JSONObject jsonObject) throws IOException {

        Dynamic dynamic = new Dynamic();
        dynamic.setPhone(jsonObject.getString("user"));
        dynamic.setType(jsonObject.getInteger("type"));
        dynamic.setBrowse(0);
        dynamic.setCity(jsonObject.getString("city"));
        dynamic.setPettypeid(jsonObject.getInteger("pettypeid"));
        dynamic.setText(EmojiParser.parseToAliases(jsonObject.getString("text"), EmojiParser.FitzpatrickAction.PARSE));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis());
        dynamic.setReleasetime(formatter.format(date));
        dynamicDao.insertDynamic(dynamic);
        return (int) dynamicDao.selectDynamic().get(0).getId();
    }

    //图片动态的文件上传
    @RequestMapping(value = "/insertDynamic2file", method = RequestMethod.POST)
    @ResponseBody
    public String insertDynamic2file(@Param("file") MultipartFile file, @Param("id") int id) throws IOException {

        String path = FILEURL + "\\Dynamic\\" + id;
        File dest = new File(path, file.getOriginalFilename());
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

    //文件上传
    public String upload(String path, MultipartFile file, int index) {
        //获取最后一个.的位置
        int lastIndexOf = file.getOriginalFilename().lastIndexOf(".");
        //获取文件的后缀名 .jpg
        String suffix = file.getOriginalFilename().substring(lastIndexOf);
        String filename = "";
        if (suffix.equals(".mp4")) {
            filename = 1 + suffix;
        } else {
            filename = 2 + suffix;
        }
        File dest = new File(path, filename);
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        if (!dest.exists()) {
            dest.mkdirs();
        }
        try {
            file.transferTo(dest); //保存文件
            //视频文件保存完成之后创建封面文件
            if (index == 1) {
                try {
                    fetchFrame(path + "\\1.mp4", path + "\\2.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

    //查询所有动态
    @RequestMapping(value = "/selectDynamic", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<Dynamic> selectDynamic() {
        List<Dynamic> list = dynamicDao.selectDynamic();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(list.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
            File file = new File(FILEURL + "\\Dynamic\\" + list.get(i).getId());
            File[] tempList = file.listFiles();
            List<String> fileNameList = new ArrayList();
            for (int j = 0; j < tempList.length; j++) {
                if (tempList[j].isFile()) {
                    fileNameList.add(tempList[j].getName());
                }
            }
            list.get(i).setUrl(fileNameList);
        }

        return list;
    }


    //视频封面
    public void fetchFrame(String videofile, String framefile)
            throws Exception {
        long start = System.currentTimeMillis();
        File targetFile = new File(framefile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 5) && (f.image != null)) {
                break;
            }
            i++;
        }
        opencv_core.IplImage img = f.image;
        int owidth = img.width();
        int oheight = img.height();
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        //ff.flush();
        ff.stop();
    }

    //查询动态根据id
    @GetMapping("/selectDynamicByid/{id}")
    public Dynamic selectDynamicByid(@PathVariable int id) {
        Dynamic dynamic = dynamicDao.selectDynamicByid(id);
        dynamic.setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(dynamic.getText(), EmojiParser.FitzpatrickAction.PARSE)));
        File file = new File(FILEURL + "\\Dynamic\\" + dynamic.getId());
        File[] tempList = file.listFiles();
        List<String> fileNameList = new ArrayList();
        for (int k = 0; k < tempList.length; k++) {
            if (tempList[k].isFile()) {
                fileNameList.add(tempList[k].getName());
            }
        }
        dynamic.setUrl(fileNameList);
        dynamic.setNickname(userDao.selectUserByPhone(dynamic.getPhone()).getNickname());
        return dynamic;
    }

    //增加浏览
    @GetMapping("/insertbrowse/{id}")
    public void insertbrowse(@PathVariable int id) {
        dynamicDao.insertbrowse((int) dynamicDao.selectDynamicByid(id).getBrowse() + 1, id);
    }

    //查询关注的动态
    @GetMapping("/selectDynamicByAttention/{f}/{limit}")
    public List<Dynamic> selectDynamicByAttention(@PathVariable String f, @PathVariable int limit) {
        Attention attention = new Attention();
        attention.setF(f);
        List<Attention> attentionList = attentionDao.selectAttentionByphone(attention);
        List<Dynamic> dynamicList = new ArrayList<>();
        for (int i = 0; i < attentionList.size(); i++) {
            List<Dynamic> list = dynamicDao.selectDynamicByiphon(attentionList.get(i).getT(), limit);
            for (int j = 0; j < list.size(); j++) {
                File file = new File(FILEURL + "\\Dynamic\\" + list.get(j).getId());
                File[] tempList = file.listFiles();
                List<String> fileNameList = new ArrayList();
                for (int k = 0; k < tempList.length; k++) {
                    if (tempList[k].isFile()) {
                        fileNameList.add(tempList[k].getName());
                    }
                }
                list.get(j).setUrl(fileNameList);
                Fabulous fabulous = new Fabulous();
                fabulous.setType(1);
                fabulous.setEid(list.get(j).getId());
                fabulous.setUser(list.get(j).getPhone());
                list.get(j).setFabulous(fabulousDao.selectFabulousByTypeEid(fabulous).size());
                dynamicList.add(list.get(j));
            }
        }
        for (int i = 0; i < dynamicList.size(); i++) {
            dynamicList.get(i).setNickname(userDao.selectUserByPhone(dynamicList.get(i).getPhone()).getNickname());
            dynamicList.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(dynamicList.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
        }
        Collections.sort(dynamicList, new Comparator<Dynamic>() {
            public int compare(Dynamic arg0, Dynamic arg1) {
                if (arg0.getId() > arg1.getId()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return dynamicList;
    }

    //查询推荐的动态
    @GetMapping("/selectDynamicByRecommend/{user}")
    public List<Dynamic> selectDynamicByRecommend(@PathVariable String user) {
        List<Userpettype> userpettypes = userpettypeDao.selectUserpettype(user);
        List<Dynamic> dynamicList = new ArrayList<>();
        for (int i = 0; i < userpettypes.size(); i++) {
            List<Dynamic> list = dynamicDao.selectDynamicBytype((int) userpettypes.get(i).getId());
            for (int j = 0; j < list.size(); j++) {
                File file = new File(FILEURL + "\\Dynamic\\" + list.get(j).getId());
                File[] tempList = file.listFiles();
                List<String> fileNameList = new ArrayList();
                for (int k = 0; k < tempList.length; k++) {
                    if (tempList[k].isFile()) {
                        fileNameList.add(tempList[k].getName());
                    }
                }
                list.get(j).setUrl(fileNameList);
                Fabulous fabulous = new Fabulous();
                fabulous.setType(1);
                fabulous.setEid(list.get(j).getId());
                fabulous.setUser(list.get(j).getPhone());
                list.get(j).setFabulous(fabulousDao.selectFabulousByTypeEid(fabulous).size());
                dynamicList.add(list.get(j));
            }
        }
        for (int i = 0; i < dynamicList.size(); i++) {
            dynamicList.get(i).setNickname(userDao.selectUserByPhone(dynamicList.get(i).getPhone()).getNickname());
            dynamicList.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(dynamicList.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
        }
        Collections.sort(dynamicList, new Comparator<Dynamic>() {
            public int compare(Dynamic arg0, Dynamic arg1) {
                if (arg0.getId() > arg1.getId()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return dynamicList;
    }

    //查询热门的动态
    @GetMapping("/selectDynamicByHot")
    public List<Dynamic> selectDynamicByHot() {
        List<Dynamic> dynamicList = dynamicDao.selectDynamic();
        for (int i = 0; i < dynamicList.size(); i++) {
            Fabulous fabulous = new Fabulous();
            fabulous.setEid(dynamicList.get(i).getId());
            fabulous.setType(dynamicList.get(i).getType());
            dynamicList.get(i).setFabulous(fabulousDao.selectFabulousByTypeEid(fabulous).size());
        }
        for (int j = 0; j < dynamicList.size(); j++) {
            File file = new File(FILEURL + "\\Dynamic\\" + dynamicList.get(j).getId());
            File[] tempList = file.listFiles();
            List<String> fileNameList = new ArrayList();
            for (int k = 0; k < tempList.length; k++) {
                if (tempList[k].isFile()) {
                    fileNameList.add(tempList[k].getName());
                }
            }
            dynamicList.get(j).setUrl(fileNameList);
        }
        for (int i = 0; i < dynamicList.size(); i++) {
            dynamicList.get(i).setNickname(userDao.selectUserByPhone(dynamicList.get(i).getPhone()).getNickname());
            dynamicList.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(dynamicList.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
        }
        Collections.sort(dynamicList, new Comparator<Dynamic>() {
            public int compare(Dynamic arg0, Dynamic arg1) {
                if ((arg0.getFabulous()) >= (arg1.getFabulous())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return dynamicList;
    }

    //查询本地动态
    @GetMapping("/selectDynamicByLocal/{user}")
    public List<Dynamic> selectDynamicByLocal(@PathVariable String user) {
        List<Dynamic> dynamicList = dynamicDao.selectDynamicByLocal(userDao.selectUserByPhone(user).getCity());
        for (int i = 0; i < dynamicList.size(); i++) {
            Fabulous fabulous = new Fabulous();
            fabulous.setEid(dynamicList.get(i).getId());
            fabulous.setType(dynamicList.get(i).getType());
            dynamicList.get(i).setFabulous(fabulousDao.selectFabulousByTypeEid(fabulous).size());
        }
        for (int j = 0; j < dynamicList.size(); j++) {
            File file = new File(FILEURL + "\\Dynamic\\" + dynamicList.get(j).getId());
            File[] tempList = file.listFiles();
            List<String> fileNameList = new ArrayList();
            for (int k = 0; k < tempList.length; k++) {
                if (tempList[k].isFile()) {
                    fileNameList.add(tempList[k].getName());
                }
            }
            dynamicList.get(j).setUrl(fileNameList);
        }
        for (int i = 0; i < dynamicList.size(); i++) {
            dynamicList.get(i).setNickname(userDao.selectUserByPhone(dynamicList.get(i).getPhone()).getNickname());
            dynamicList.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(dynamicList.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
        }
        Collections.sort(dynamicList, new Comparator<Dynamic>() {
            public int compare(Dynamic arg0, Dynamic arg1) {
                if ((arg0.getId()) >= (arg1.getId())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return dynamicList;
    }

    //查询我的动态
    @GetMapping("/selectDynamicByiphon/{user}")
    public List<Dynamic> selectDynamicByiphon(@PathVariable String user) {
        List<Dynamic> dynamicList = dynamicDao.selectDynamicByuser(user);
        for (int j = 0; j < dynamicList.size(); j++) {
            File file = new File(FILEURL + "\\Dynamic\\" + dynamicList.get(j).getId());
            File[] tempList = file.listFiles();
            List<String> fileNameList = new ArrayList();
            for (int k = 0; k < tempList.length; k++) {
                if (tempList[k].isFile()) {
                    fileNameList.add(tempList[k].getName());
                }
            }
            dynamicList.get(j).setUrl(fileNameList);
        }
        for (int i = 0; i < dynamicList.size(); i++) {
            dynamicList.get(i).setNickname(userDao.selectUserByPhone(dynamicList.get(i).getPhone()).getNickname());
            dynamicList.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(dynamicList.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
        }
        Collections.sort(dynamicList, new Comparator<Dynamic>() {
            public int compare(Dynamic arg0, Dynamic arg1) {
                if ((arg0.getId()) >= (arg1.getId())) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return dynamicList;
    }

    @GetMapping("/delectDynamic/{id}")
    public void delectDynamic(@PathVariable int id) {
        Dynamic dynamic=new Dynamic();
        dynamic.setId(id);
        List<Dynamic>list=new ArrayList<>();
        list.add(dynamic);
        dynamicDao.delectDynamic(list);
    }


}
