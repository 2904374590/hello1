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
import com.flower.entity.Pay;

import java.util.List;

public interface PayService {
    //增加商品
    void addPay(Pay pay);
    //根据oid查询该订单下的所有商品信息
    List<Pay> findPayByOid(Integer oid);
    //修改商品的状态
    void updatePays(Integer pid, String ostatus);
    //通过pid删除商品
    void deleteByPid(Integer pid);
    //通过pid查询当前的订单号
    Integer findOrderByPid(Integer pid);
    //通过oid查询当前的商品
    void deleteByOid(Integer oid);
}
