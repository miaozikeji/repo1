package com.example.demo.mapperDao;

import com.example.demo.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ShopDao {

    public Shop selectShopById(int id);
    public List<Shopchoice> selectcshopchoiceByEid(int eid);
    public Shopchoice selectshopchoicebyid(int id);
    public List<Address> selectcaddressByuser(String user);
    public void updateshopaddress(Address address);
    public void insertcaddress(Address address);
    public void deletecaddress(List<Address> list);
    public void updateshopaddressis(String user);
    public List<ShopCart> selectshopcartbyuser(String user);
    public void insertcshopcart(ShopCart cart);
    public void insertcorder(Order order);
    public void insertcorderitem(Orderitem orderitem);
    public List<Order> selectorderbyuser(String user);
    public void updateshopcart(ShopCart shopCart);
    public void deleteshopcart(List<ShopCart> list);
    public void deleteshopcartbyid(List<ShopCart> list);
    public List<Orderitem> selectorderitembyeid(int eid);
    public void deleteordertbyid(List<Order> list);
    public void updatecorder(Order order);
    public List<Shopevaluate> selectShopevaluatebyeid( int eid);
    public Shopevaluate selectShopevaluate(Shopevaluate shopevaluate);
    public void insertcshopevaluate(Shopevaluate shopevaluate);
    public List<Shop> selectshopBytype(int type);
    public List<Shop> selectshopBysearchname(String search );
    public List<Shop> selectshopBysearchtext(String search);

    //积分
    public void updatecAccumulateByuse(Accumulate a);
    public void updatecAccumulateBysum(Accumulate a);
    public Accumulate selectAccumulate(Accumulate a);
    public void insertAccumulate(Accumulate a);
    public Accumulate selectAccumulatebyuser(Accumulate a);
    public void insertAccumulateitem(Accumulateitem accumulateitem);
    public List<Accumulateitem> selectAccumulateitem (String user);
}
