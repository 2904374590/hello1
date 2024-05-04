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

import com.flower.entity.Cart;

import java.util.List;

public interface CartService {
    //添加鲜花
    void addCart(Cart cart);
    //根据uid查询该用户的所有商品
    List<Cart> queryCart(Integer uid);
    //通过cid删除购物车
    void deteleByCid(Integer cid);
    //通过cid查询商品
    Cart findCartByCid(Integer i);
    //保存商品信息
    void saveCart(Cart cart);
    //显示所有的购物车
    List<Cart> queryCarts();
}
