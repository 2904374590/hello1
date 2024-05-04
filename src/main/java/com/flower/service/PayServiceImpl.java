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

import com.flower.dao.PayDao;
import com.flower.entity.Order;
import com.flower.entity.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PayServiceImpl implements PayService{
    @Autowired
    private PayDao payDao;
    //增加商品
    @Override
    public void addPay(Pay pay) {
        payDao.addPay(pay);
    }
    //根据oid查询该订单下的所有商品信息
    @Override
    public List<Pay> findPayByOid(Integer oid) {
        return payDao.findPayByOid(oid);
    }
    //修改商品的状态
    @Override
    public void updatePays(Integer pid,String ostatus) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pid",pid);
        map.put("ostatus",ostatus);
        payDao.updatePays(map);
    }
    //通过pid删除商品
    @Override
    public void deleteByPid(Integer pid) {
        payDao.deleteByPid(pid);
    }
    //通过pid查询当前的订单
    @Override
    public Integer findOrderByPid(Integer pid) {
        return payDao.findOrderByPid(pid);
    }
    //通过oid查询当前的商品
    @Override
    public void deleteByOid(Integer oid) {
        payDao.deleteByOid(oid);
    }
}
