package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.example.demo.mapperDao.CommentDao;
import com.example.demo.mapperDao.CommentItemDao;
import com.example.demo.mapperDao.FabulousDao;
import com.example.demo.mapperDao.UserDao;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
public class CommentCon {

    @Autowired
    CommentDao commentDao;
    @Autowired
    UserDao userDao;
    @Autowired
    CommentItemDao commentItemDao;
    @Autowired
    FabulousDao fabulousDao;

    //根据动态id查询评论(热度)
    @GetMapping("/selectCommentBydynamicidHeat/{id}/{phone}")
    public List<Comment> selectBydynamicidHeat(@PathVariable int id,@PathVariable String phone) {
        List<Comment> list = commentDao.selectBydynamicid(id);
        for (int i = 0; i < list.size(); i++) {
            User user = userDao.selectUserByPhone(list.get(i).getUserphone());
            list.get(i).setNickname(user.getNickname());
            list.get(i).setNickname(user.getNickname());
            list.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(list.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
            List <CommentItem> commentItems=commentItemDao.selectCommentItemByCommenId((int) list.get(i).getId());
            for(int j=0;j<commentItems.size();j++){
                commentItems.get(j).setNickname(userDao.selectUserByPhone( commentItems.get(j).getUserphone()).getNickname());
                commentItems.get(j).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(commentItems.get(j).getText(), EmojiParser.FitzpatrickAction.PARSE)));
            }
            list.get(i).setCommentItems(commentItems);
            Fabulous fabulous=new Fabulous();
            fabulous.setEid(list.get(i).getId());
            fabulous.setType(2);
            fabulous.setUser(phone);
            if (fabulousDao.selectFabulous(fabulous)!=null){
                fabulous.setIs(1);
                fabulous.setId(fabulousDao.selectFabulous(fabulous).getId());
            }else {
                fabulous.setIs(0);
            }
            fabulous.setNum(fabulousDao.selectFabulousByTypeEid(fabulous).size());
            list.get(i).setFabulous(fabulous);
        }
        Collections.sort(list, new Comparator<Comment>() {
            public int compare(Comment arg0, Comment arg1) {
                if (arg0.getFabulous().getNum() > arg1.getFabulous().getNum()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        return list;
    }

    //根据动态id查询评论(时间)
    @GetMapping("/selectCommentBydynamicidTime/{id}/{phone}")
    public List<Comment> selectBydynamicidTime(@PathVariable int id,@PathVariable String phone) {
        List<Comment> list = commentDao.selectBydynamicid(id);
        for (int i = 0; i < list.size(); i++) {
            User user = userDao.selectUserByPhone(list.get(i).getUserphone());
            list.get(i).setNickname(user.getNickname());
            list.get(i).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(list.get(i).getText(), EmojiParser.FitzpatrickAction.PARSE)));
            List <CommentItem> commentItems=commentItemDao.selectCommentItemByCommenId((int) list.get(i).getId());
            for(int j=0;j<commentItems.size();j++){
                commentItems.get(j).setNickname(userDao.selectUserByPhone( commentItems.get(j).getUserphone()).getNickname());
                commentItems.get(j).setText(EmojiParser.parseToUnicode(EmojiParser.parseToAliases(commentItems.get(j).getText(), EmojiParser.FitzpatrickAction.PARSE)));
            }
            list.get(i).setCommentItems(commentItems);
            Fabulous fabulous=new Fabulous();
            fabulous.setEid(list.get(i).getId());
            fabulous.setType(2);
            fabulous.setUser(phone);
            if (fabulousDao.selectFabulous(fabulous)!=null){
                fabulous.setIs(1);
                fabulous.setId(fabulousDao.selectFabulous(fabulous).getId());
            }else {
                fabulous.setIs(0);
            }
            fabulous.setNum(fabulousDao.selectFabulousByTypeEid(fabulous).size());
            list.get(i).setFabulous(fabulous);
        }
        return list;
    }

    //添加评论
    @RequestMapping(value = "/insertComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void insertComment(@RequestBody JSONObject jsonObject) throws IOException {
        Comment comment = new Comment();
        comment.setDynamicid(jsonObject.getLong("dynamicid"));
        comment.setText(EmojiParser.parseToAliases(jsonObject.getString("text"), EmojiParser.FitzpatrickAction.PARSE));
        comment.setUserphone(jsonObject.getString("user"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis());
        comment.setReleasetime(formatter.format(date));
        commentDao.insertComment(comment);
    }

    //删除评论根据id
    @GetMapping("/deleteComment/{id}")
    public void deleteComment(@PathVariable int id) {
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId(id);
        comments.add(comment);
        commentDao.deleteComment(comments);
    }

    //添加子评论
    @RequestMapping(value = "/insertCommentItem", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void insertCommentItem(@RequestBody JSONObject jsonObject)  {
        CommentItem commentItem=new CommentItem();
        commentItem.setText(jsonObject.getString("text"));
        commentItem.setUserphone(jsonObject.getString("userphone"));
        commentItem.setCommentid(jsonObject.getInteger("commentid"));
        commentItemDao.insertCommentItem(commentItem);
    }

    //删除子评论根据id
    @GetMapping("/delectCommentItemById/{id}")
    public void delectCommentItemById(@PathVariable int id){
        CommentItem c=new CommentItem();
        c.setId(id);
        List<CommentItem> list=new ArrayList<>();
        list.add(c);
        commentItemDao.delectCommentItemById(list);
    }
}
