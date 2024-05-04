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

import com.flower.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    //查询所有员工信息
    List<User> lists();
    //通过用户名查找用户
    User findByUserName(String name);
    //增加用户信息
    void save(User user);
    //根据id查询用户
    User findById(Integer id);
    //更新用户
    void update(User user);
    //删除用户
    void delete(Integer uid);
    //通过用户名查找用户(模糊查询)
    List<User> findByUsername(String uname);
}
