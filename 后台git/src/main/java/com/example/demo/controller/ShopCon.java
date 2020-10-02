package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.example.demo.mapperDao.ShopDao;
import com.example.demo.mapperDao.UserDao;
import com.vdurmont.emoji.EmojiParser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.demo.util.Constant.FILEURL;

@CrossOrigin
@RestController
public class ShopCon {

    @Autowired
    ShopDao shopdao;
    @Autowired
    UserDao userDao;

    @GetMapping("/selectShopById/{id}")
    public Shop selectShopById(@PathVariable int id) {
        File file = new File(FILEURL + "\\Shop\\" + id);
        File[] tempList = file.listFiles();
        List<String> fileNameList = new ArrayList();
        for (int j = 0; j < tempList.length; j++) {
            if (tempList[j].isFile()) {
                fileNameList.add(tempList[j].getName());
            }
        }
        Shop shop = shopdao.selectShopById(id);
        shop.setUrl(fileNameList);
        shop.setShopchoices(shopdao.selectcshopchoiceByEid(id));
        return shop;
    }

    @GetMapping("/selectcaddressByuser/{user}")
    public List<Address> selectcaddressByuser(@PathVariable String user) {
        return shopdao.selectcaddressByuser(user);
    }

    @GetMapping("/updateshopaddress/{id}/{add}/{is}/{addphone}/{name}")
    public void updateshopaddress(@PathVariable int id, @PathVariable String add, @PathVariable int is,
                                  @PathVariable String addphone, @PathVariable String name) {
        Address address = new Address();
        address.setId(id);
        address.setAddress(add);
        address.setAddis(is);
        address.setAddphone(addphone);
        address.setAddname(name);
        shopdao.updateshopaddress(address);
    }

    @GetMapping("/insertcaddress/{user}/{add}/{is}/{addphone}/{name}")
    public void insertcaddress(@PathVariable String user, @PathVariable String add, @PathVariable int is,
                               @PathVariable String addphone, @PathVariable String name) {
        Address address = new Address();
        address.setAddress(add);
        address.setAddis(is);
        address.setAddphone(addphone);
        address.setUser(user);
        address.setAddname(name);
        if (is == 1) {
            shopdao.updateshopaddressis(user);
        }
        shopdao.insertcaddress(address);
    }

    @GetMapping("/deletecaddress/{id}")
    public void deletecaddress(@PathVariable int id) {
        List<Address> list = new ArrayList<>();
        Address a = new Address();
        a.setId(id);
        list.add(a);
        shopdao.deletecaddress(list);
    }

    @GetMapping("/selectshopcartbyuser/{user}")
    public List<ShopCart> selectshopcartbyuser(@PathVariable String user) {
        List<ShopCart> list = shopdao.selectshopcartbyuser(user);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setShops(selectShopById((int) list.get(i).getEid()));
        }
        return list;
    }

    @GetMapping("/insertcshopcart/{eid}/{user}/{choice}/{num}")
    public void insertcshopcart(@PathVariable int eid, @PathVariable String user, @PathVariable int choice, @PathVariable int num) {
        ShopCart s = new ShopCart();
        s.setChoice(choice);
        s.setEid(eid);
        s.setUser(user);
        s.setNum(num);
        shopdao.insertcshopcart(s);
    }

    @GetMapping("/selectorderbyuser/{user}")
    public List<Order> selectorderbyuser(@PathVariable String user) {
        List<Order> list = shopdao.selectorderbyuser(user);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOrderitems(shopdao.selectorderitembyeid((int) list.get(i).getId()));
            for (int j = 0; j < list.get(i).getOrderitems().size(); j++) {
                for (int x = 0; x < shopdao.selectcshopchoiceByEid((int) list.get(i).getOrderitems().get(j).getShopid()).size(); x++) {
                    if (shopdao.selectcshopchoiceByEid((int) list.get(i).getOrderitems().get(j).getShopid()).get(x).getId() == list.get(i).getOrderitems().get(j).getChoicesid()) {
                        list.get(i).getOrderitems().get(j).setShopchoices(shopdao.selectshopchoicebyid((int) list.get(i).getOrderitems().get(j).getChoicesid()));
                    }
                }
                list.get(i).getOrderitems().get(j).setShops(selectShopById((int) list.get(i).getOrderitems().get(j).getShopid()));
            }
        }
        return list;
    }

    @GetMapping("/insertcorder/{add}/{state}/{user}/{pay}/{paynum}")
    public int insertcorder(@PathVariable int add, @PathVariable int state,
                            @PathVariable String user, @PathVariable String pay,
                            @PathVariable double paynum
    ) {
        Order order = new Order();
        order.setState(state);
        order.setAdd(add);
        order.setPay(pay);
        order.setUser(user);
        order.setPaynum(paynum);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Calendar c = Calendar.getInstance();
        order.setTime(df.format(c.getTime()));
        shopdao.insertcorder(order);
        return (int) shopdao.selectorderbyuser(user).get(0).getId();
    }

    @GetMapping("/insertcorderitem/{shopid}/{choicesid}/{orderid}/{num}")
    public void insertcorderitem(
            @PathVariable int shopid, @PathVariable int choicesid,
            @PathVariable int orderid, @PathVariable int num) {
        Orderitem orderitem = new Orderitem();
        orderitem.setShopid(shopid);
        orderitem.setChoicesid(choicesid);
        orderitem.setOrderid(orderid);
        orderitem.setNum(num);
        shopdao.insertcorderitem(orderitem);
    }

    @GetMapping("/deleteshopcart/{user}")
    public void deleteshopcart(@PathVariable String user) {
        List<ShopCart> list = new ArrayList<>();
        ShopCart s = new ShopCart();
        s.setUser(user);
        list.add(s);
        shopdao.deleteshopcart(list);
    }

    @GetMapping("/updateshopcart/{id}/{num}")
    public void updateshopcart(@PathVariable int id, @PathVariable int num) {
        ShopCart shopCart = new ShopCart();
        shopCart.setId(id);
        shopCart.setNum(num);
        shopdao.updateshopcart(shopCart);
    }

    @GetMapping("/deleteshopcartbyid/{id}")
    public void deleteshopcartbyid(@PathVariable int id) {
        List<ShopCart> list = new ArrayList<>();
        ShopCart s = new ShopCart();
        s.setId(id);
        list.add(s);
        shopdao.deleteshopcartbyid(list);
    }

    @GetMapping("/deleteordertbyid/{id}")
    public void deleteordertbyid(@PathVariable int id) {
        Order order = new Order();
        order.setId(id);
        List<Order> list = new ArrayList<>();
        list.add(order);
        shopdao.deleteordertbyid(list);
    }

    @GetMapping("/updatecorder/{id}/{state}")
    public void updatecorder(@PathVariable int id, @PathVariable int state) {
        Order o = new Order();
        o.setId(id);
        o.setState(state);
        shopdao.updatecorder(o);
    }

    @GetMapping("/selectShopevaluatebyeid/{eid}")
    public List<Shopevaluate> selectShopevaluatebyeid(@PathVariable int eid) {
        List<Shopevaluate> list = shopdao.selectShopevaluatebyeid(eid);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setUsers(userDao.selectUserByPhone(list.get(i).getUser()));
            File file = new File(FILEURL + "\\shopevaluate\\" + list.get(i).getId());
            File[] tempList = file.listFiles();
            List<String> fileNameList = new ArrayList();
            if (tempList == null) {
                break;
            }
            for (int j = 0; j < tempList.length; j++) {
                if (tempList[j].isFile()) {
                    fileNameList.add(tempList[j].getName());
                }
            }

            list.get(i).setUrl(fileNameList);
        }
        return list;
    }

    //添加评论
    @RequestMapping(value = "/insertcshopevaluate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int insertComment(@RequestBody JSONObject jsonObject) throws IOException {
        Shopevaluate shopevaluate = new Shopevaluate();
        shopevaluate.setEid(jsonObject.getInteger("eid"));
        shopevaluate.setFraction(jsonObject.getInteger("fraction"));
        shopevaluate.setText(jsonObject.getString("text"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Calendar c = Calendar.getInstance();
        shopevaluate.setTime(df.format(c.getTime()));
        shopevaluate.setUser(jsonObject.getString("user"));
        shopdao.insertcshopevaluate(shopevaluate);
        return (int) shopdao.selectShopevaluate(shopevaluate).getId();
    }

    //图片动态的文件上传
    @RequestMapping(value = "/insertshopevaluatefile", method = RequestMethod.POST)
    @ResponseBody
    public String insertDynamic2file(@Param("file") MultipartFile file, @Param("id") int id) throws IOException {

        String path = FILEURL + "\\shopevaluate\\" + id;
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

    @GetMapping("/selectshopBytype/{type}")
    public List<Shop> selectshopBytype(@PathVariable int type) {
        List<Shop> list = shopdao.selectshopBytype(type);
        return list;
    }

    @GetMapping("/selectshopBysearch/{search}")
    public List<Shop> selectshopBysearch(@PathVariable String search) {
        List<Shop> list = shopdao.selectshopBysearchname(search);
        List<Shop> list2 = shopdao.selectshopBysearchtext(search);
        for (int i = 0; i < list.size(); i++) {
            list2.add(list.get(i));
        }
        return list2;
    }


    //积分
    @GetMapping("/updatecAccumulateByuse/{user}/{num}")
    public void updatecAccumulateByuse(@PathVariable String user, @PathVariable int num) {
        Accumulate a = new Accumulate();
        a.setPhone(user);
        if (shopdao.selectAccumulatebyuser(a) == null) {
            a.setCsum(0);
            a.setCuse(0);
            shopdao.insertAccumulate(a);
        } else {
            a.setCuse(shopdao.selectAccumulate(a).getCuse() + num);
            shopdao.updatecAccumulateByuse(a);
        }
    }

    @GetMapping("/updatecAccumulateBysum/{user}/{num}")
    public void updatecAccumulateBysum(@PathVariable String user, @PathVariable int num) {
        Accumulate a = new Accumulate();
        a.setPhone(user);
        if (shopdao.selectAccumulatebyuser(a) == null) {
            a.setCsum(0);
            a.setCuse(0);
            shopdao.insertAccumulate(a);
        }
        a.setCsum(shopdao.selectAccumulate(a).getCsum() + num);
        shopdao.updatecAccumulateBysum(a);
    }

    @GetMapping("/selectAccumulate/{user}")
    public Accumulate selectAccumulate(@PathVariable String user) {
        Accumulate a = new Accumulate();
        a.setPhone(user);
        if (shopdao.selectAccumulatebyuser(a) == null) {
            a.setCsum(0);
            a.setCuse(0);
            shopdao.insertAccumulate(a);
        }
        return shopdao.selectAccumulate(a);
    }

    @GetMapping("/selectAccumulateitem/{user}")
    public List<Accumulateitem> selectAccumulateitem(@PathVariable String user) {
        List<Accumulateitem> list = shopdao.selectAccumulateitem(user);
        for (int i = 0; i < list.size(); i++) {
            List<Orderitem> orderitems=new ArrayList<>();
            if (list.get(i).getType() == 1) {
                list.get(i).setObjects(shopdao.selectorderitembyeid((int) list.get(i).getEid()));
            }
        }
        return list;
    }

    @GetMapping("/insertAccumulateitem/{type}/{eid}/{num}/{phone}")
    public void insertAccumulateitem(@PathVariable int type, @PathVariable int eid, @PathVariable int num, @PathVariable String phone) {
        Accumulateitem accumulateitem = new Accumulateitem();
        accumulateitem.setEid(eid);
        accumulateitem.setNum(num);
        accumulateitem.setPhone(phone);
        accumulateitem.setType(type);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        accumulateitem.setTime(df2.format(new Date()));
        shopdao.insertAccumulateitem(accumulateitem);
    }
}
