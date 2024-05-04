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

import com.flower.entity.Flower;
import com.flower.entity.Sort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface FlowerDao {
    //添加鲜花分类
    void addSort(String sname);
    //查询所有分类
    List<Sort> lists();
    //根据sid查询所有鲜花信息
    List<Flower> findFlowerBySid(Integer sid);
    //通过sid删除该分类
    void deleteBySid(Integer sid);
    //通过sid查询该分类
    Sort findSortBySid(Integer sid);
    //修改分类名称
    void updateSname(Sort sort);
    //通过sname查询分类
    Sort findBySname(String sname);
    //分类名搜索框
    List<Sort> queryByUsername(String sname);
    //通过鲜花名删除该鲜花
    void deleteByFid(Integer fid);
    //通过fid查询鲜花的所有信息
    Flower findFlowerByFid(Integer fid);
    //通过鲜花名查找鲜花和分组sid
    Flower findFlowerByFname(Map<String,Object> map);
    //添加鲜花
    void addFlower(Flower flower);
    //搜索框（通过鲜花名查询鲜花） 模糊搜素
    List<Flower> queryFlowerByFname(Map<String,Object> map);
    //修改鲜花
    void updateFlower(Flower flower);
    //修改分类名的同时也要修改鲜花名的所有分类
    void updateFLowerSname(Map<String,Object> map);
    //查询所有鲜花
    List<Flower> qureyFlowers(String fname);
    //无参数查询所有的鲜花
    List<Flower> queryFlower();
    //根据sid删除所有鲜花
    void deleteFlowerBySid(Integer sid);
}
