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

import com.flower.dao.CartDao;
import com.flower.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CartServiceImpl implements CartService{
    @Autowired
    private CartDao cartDao;
    //添加购物车
    @Override
    public void addCart(Cart cart) {
        //注意，这里通过subject和 uid查询购物，如果有，那么不允许购买
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid",cart.getUid());
        map.put("subject",cart.getSubject());
        Cart cartDB = cartDao.findCartBySubject(map);
        //原来的商品数量
        if(!ObjectUtils.isEmpty(cartDB)){
            //到这一行就代表有相同的商品，直接修改商品的数量，如果原有商品卖了10件，那么添加购物车2件之后，数据库中
            //显示的就是12件，
            //需要的功能:获取原来的商品数量和新的商品数量，添加，然后setcamount
            //新的商品数量
            int oldAmount = cartDB.getCamount();
            int newAmount = cart.getCamount();
            int Amount = oldAmount+newAmount;
            //修改商品的数量
            map.put("subject",cart.getSubject());
            map.put("camount",Amount);

            cartDao.updateCartAmount(map);
        } else {
            cartDao.addCart(cart);
        }
    }
    //根据uid查询该用户的所有商品
    @Override
    public List<Cart> queryCart(Integer uid) {
        return cartDao.findCartByUid(uid);
    }
    //通过cid删除购物车
    @Override
    public void deteleByCid(Integer cid) {
            cartDao.deleteByCid(cid);
    }
    //通过cid查询商品
    @Override
    public Cart findCartByCid(Integer i) {
        return cartDao.findCartByCid(i);
    }
    //保存商品信息
    @Override
    public void saveCart(Cart cart) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("camount",cart.getCamount());
        map.put("money",cart.getMoney());
        map.put("cid",cart.getCid());
        cartDao.saveCart(map);
    }
    //显示所有的购物车
    @Override
    public List<Cart> queryCarts() {
        return cartDao.queryCarts();
    }

}
