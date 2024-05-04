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

import com.flower.dao.FlowerDao;
import com.flower.entity.Flower;
import com.flower.entity.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FlowerServiceImpl implements FlowerService{
    @Autowired
    private FlowerDao flowerDao;
    //添加鲜花分类
    @Override
    public void addSort(String sname) {
        Sort sortDB = flowerDao.findBySname(sname);
        if(!ObjectUtils.isEmpty(sortDB)) throw new RuntimeException("当前鲜花类别已被注册！");
        flowerDao.addSort(sname);
    }
    //查询所有分类
    @Override
    public List<Sort> lists() {
        return flowerDao.lists();
    }
    //通过sid查询所有鲜花
    @Override
    public List<Flower> findFlowerBySid(Integer sid) {
        return flowerDao.findFlowerBySid(sid);
    }
    //通过sid删除该分类
    @Override
    public void deleteBySid(Integer sid) {
        flowerDao.deleteBySid(sid);
    }
    //通过sid查询该对象
    @Override
    public Sort findSortBySid(Integer sid) {
        return flowerDao.findSortBySid(sid);
    }
    //修改分类名称
    @Override
    public void updateSname(Sort sort) {
        //如果修改分类名称的话，需要把所有的分类下的鲜花分类名都修改！！！
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sid",sort.getSid());
        map.put("sname",sort.getSname());
        flowerDao.updateFLowerSname(map);
        //判断是否已经存在该分类，如果存在该分类那就返回一个提示
        Sort sortList = flowerDao.findBySname(sort.getSname());
        if(!ObjectUtils.isEmpty(sortList)) throw new RuntimeException("已有当前类别!!！");
        flowerDao.updateSname(sort);
    }
    //搜索框
    @Override
    public List<Sort> queryByUsername(String sname) {
        return flowerDao.queryByUsername(sname);
    }
    //通过鲜花名删除该鲜花
    @Override
    public void deleteByFid(Integer fid) {
        flowerDao.deleteByFid(fid);
    }
    //通过fid查询鲜花的所有信息
    @Override
    public Flower findFlowerByFid(Integer fid) {
        return flowerDao.findFlowerByFid(fid);
    }
    //添加新的鲜花
    @Override
    public void addFlower(Flower flower) {
        //确保鲜花名不重复，所以要写一个方法；通过鲜花名查找sort----->Map
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sid",flower.getSid());
        map.put("fname",flower.getFname());
        Flower flowerDB = flowerDao.findFlowerByFname(map);
        if(!ObjectUtils.isEmpty(flowerDB)) throw new RuntimeException("当前已有该鲜花");
        //添加鲜花
        flowerDao.addFlower(flower);
    }
    //通过鲜花名查询鲜花（模糊搜索）
    @Override
    public List<Flower> findFlowerByFname(String fname,Integer sid) {
        //首先通过fname获取sid，这个fname要在sort这个分类下
        //那么你怎么确定是哪个sort？
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sid",sid);
        map.put("fname",fname);
        return flowerDao.queryFlowerByFname(map);
    }
    //修改鲜花
    @Override
    public void updateFlower(Flower flower) {
        flowerDao.updateFlower(flower);
    }
    //查询所有鲜花
    @Override
    public List<Flower> qureyFlowers(String fname) {
        return flowerDao.qureyFlowers(fname);
    }
    //无参数查询所有的鲜花
    @Override
    public List<Flower> queryFlower() {
        return flowerDao.queryFlower();
    }
    //通过sid删除所有鲜花
    @Override
    public void deleteFlowerBySid(Integer sid) {
        flowerDao.deleteFlowerBySid(sid);
    }


}
