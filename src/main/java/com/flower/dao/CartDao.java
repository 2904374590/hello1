package com.flower.dao;/*
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
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CartDao {
    //添加购物车
    void addCart(Cart cart);
    //通过subject查询cart
    Cart findCartBySubject(Map<String,Object> map);
    //根据uid查询该用户的商品
    List<Cart> findCartByUid(Integer uid);
    //修改商品的数量
    void updateCartAmount(Map<String,Object> map);
    //通过cid删除购物车
    void deleteByCid(Integer cid);
    //通过cid查询商品
    Cart findCartByCid(Integer i);
    //保存商品信息
    void saveCart(Map<String,Object> map);
    //显示所有的购物车
    List<Cart> queryCarts();
}
