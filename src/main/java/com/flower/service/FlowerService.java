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
import com.flower.entity.Flower;
import com.flower.entity.Sort;

import java.util.List;

public interface FlowerService {
    //添加鲜花分类
    void addSort(String sname);
    //查询所有分类
    List<Sort> lists();
    //通过sid查询所有鲜花
    List<Flower> findFlowerBySid(Integer sid);
    //通过sid删除该分类
    void deleteBySid(Integer sid);
    //通过sid查询该对象
    Sort findSortBySid(Integer sid);
    //修改分类名称
    void updateSname(Sort sort);
    //通过分类名查询符合条件的分类（模糊查询）
    List<Sort> queryByUsername(String sname);
    //通过鲜花编号删除鲜花
    void deleteByFid(Integer fid);
    //通过fid查询鲜花的所有信息
    Flower findFlowerByFid(Integer fid);
    //添加新的鲜花
    void addFlower(Flower flower);
    //通过鲜花名查找鲜花（模糊搜索）
    List<Flower> findFlowerByFname(String fname,Integer sid);
    //修改鲜花
    void updateFlower(Flower flower);
    //查询所有鲜花
    List<Flower> qureyFlowers(String fname);
    //无参数查询所有的鲜花
    List<Flower> queryFlower();
    //根据sid删除所有鲜花
    void deleteFlowerBySid(Integer sid);
}
