package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Msg;
import com.example.demo.entity.Shopevaluate;
import com.example.demo.mapperDao.MsgDao;
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
import static com.example.demo.websocket.WebSocketServer.sendInfo;

@CrossOrigin
@RestController
public class MsgCon {
    @Autowired
    MsgDao msgDao;

    //添加评论
    @RequestMapping(value = "/insertMsg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int insertMsg(@RequestBody JSONObject jsonObject) throws IOException {
        Msg msg = new Msg();
        msg.setContentText(jsonObject.getString("contentText"));
        msg.setToUserId(jsonObject.getString("toUserId"));
        msg.setFromUserId(jsonObject.getString("fromUserId"));
        msg.setType(jsonObject.getInteger("type"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Calendar c = Calendar.getInstance();
        msg.setTime(df.format(c.getTime()));
        msgDao.insertMsg(msg);
        if (msg.getType() == 0) {
            sendInfo(msg.toString(), msg.getToUserId() + "");
            return 0;
        } else {
            return (int) msgDao.selectMsgid(msg).getId();
        }
    }

    @GetMapping("/selectmsgBytofrom/{toUserId}/{fromUserId}")
    public List<Msg> selectmsgBytofrom(@PathVariable String toUserId, @PathVariable String fromUserId) {
        Msg m = new Msg();
        m.setFromUserId(fromUserId);
        m.setToUserId(toUserId);
        List<Msg> list = msgDao.selectBytofrom(m);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() != 0) {
                File file = new File(FILEURL + "\\msg\\" + list.get(i).getId());
                File[] tempList = file.listFiles();
                List<String> fileNameList = new ArrayList();
                if (tempList == null) {
                    break;
                }
                for (int j = 0; j < tempList.length; j++) {
                    if (tempList[j].isFile()&&tempList[j].getName().indexOf(".mp4")!=-1&&list.get(i).getType()==2) {
                        fileNameList.add(tempList[j].getName());
                    }else if (list.get(i).getType()==1){
                        fileNameList.add(tempList[j].getName());
                    }
                }
                if (fileNameList.size()>0){
                    list.get(i).setContentText(fileNameList.get(0));

                }
            }
        }
        m.setFromUserId(toUserId);
        m.setToUserId(fromUserId);
        List<Msg> list2 = msgDao.selectBytofrom(m);
        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).getType() != 0) {
                File file = new File(FILEURL + "\\msg\\" + list2.get(i).getId());
                File[] tempList = file.listFiles();
                List<String> fileNameList = new ArrayList();
                if (tempList == null) {
                    break;
                }
                for (int j = 0; j < tempList.length; j++) {
                    if (tempList[j].isFile()&&tempList[j].getName().indexOf(".mp4")!=-1&&list2.get(i).getType()==2) {
                        fileNameList.add(tempList[j].getName());
                    }else if (list2.get(i).getType()==1){
                        fileNameList.add(tempList[j].getName());
                    }
                }
                if (fileNameList.size()>0){
                    list2.get(i).setContentText(fileNameList.get(0));
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            list2.add(list.get(i));
        }
        Collections.sort(list2, new Comparator<Msg>() {
            @Override
            public int compare(Msg o1, Msg o2) {
                int s1 = (int) o1.getId();
                int s2 = (int) o2.getId();
                if (s1 > s2) {
                    return 1;
                } else if (s1 < s2) {
                    return -1;
                }
                return 0;
            }
        });
        return list2;
    }

    //图片动态的文件上传
    @RequestMapping(value = "/insertMsgFile", method = RequestMethod.POST)
    @ResponseBody
    public String insertMsgFile(@Param("file") MultipartFile file, @Param("id") int id) throws IOException {

        String path = FILEURL + "\\msg\\" + id;
        File dest = new File(path, file.getOriginalFilename());
        if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        if (!dest.exists()) {
            dest.mkdirs();
        }
        try {
            file.transferTo(dest); //保存文件
            int intIndex = file.getOriginalFilename().indexOf(".mp4");
            if (intIndex!=-1){
                fetchFrame(path+"\\"+file.getOriginalFilename(),path+"\\0.jpg");
            }
            sendInfo("1", msgDao.selectMsgbyid(id).getToUserId() + "");
            return "ok";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "no";
        }
    }

    public  void fetchFrame(String videofile, String framefile)
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
}
