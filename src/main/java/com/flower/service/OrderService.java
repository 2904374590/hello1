package com.flower.service;/*
 * ctrl+Alt+T    代码包围
 * ctrl+shift+/   多行
 * ctrl+/         单行
 * shift+f6       重命名
 * alt+enter      百能快捷键
 * ctrl+n         搜索类
 * alt+insert     构造方法
 * ctrl+alt+v     自动生成对象
 * ctrl+H         继承树
 * alt+enter      导包，自动修正代码，解决出现的任何问题，也可以生成返回值
 * */

import com.flower.entity.Order;

import java.util.List;

public interface OrderService {
    //添加订单
    void addOrder(Order order);
    //通过订单号查询该订单
    Order queryOrderByOnum(String onumber);
    //查询该用户的所有订单信息
    List<Order> lists(Integer uid);
    //后台查询所有的订单信息
    List<Order> listsList();
    //通过oid删除该订单
    void deleteByOid(Integer oid);
    //通过订单号模糊搜索订单
    List<Order> queryOrderByOnumber(String onumber);
}
