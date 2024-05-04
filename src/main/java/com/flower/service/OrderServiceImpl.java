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

import com.flower.dao.OrderDao;
import com.flower.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;
    //添加订单
    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }
    //通过订单号查询该订单
    @Override
    public Order queryOrderByOnum(String onumber) {
        return orderDao.queryOrderByOnum(onumber);
    }
    //查询该用户的所有订单信息
    @Override
    public List<Order> lists(Integer uid) {
        return orderDao.lists(uid);
    }
    //后台查询所有的订单信息
    @Override
    public List<Order> listsList() {
        return orderDao.listsList();
    }
    //通过oid删除该订单
    @Override
    public void deleteByOid(Integer oid) {
        orderDao.deleteByOid(oid);
    }
    //通过订单号模糊搜索订单
    @Override
    public List<Order> queryOrderByOnumber(String onumber) {
        return orderDao.queryOrderByOnumber(onumber);
    }
}
