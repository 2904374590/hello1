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


import com.flower.entity.User;

import java.util.List;

public interface UserService {
    //查询所有用户
    List<User> lists();
    //用户注册
    void register(User user);
    //用户登录
    User loginByUserName(String name);
    //根据id查询用户
    User findById(Integer id);
    //根据用户名查询用户(模糊查询)
    List<User> queryByUsername(String uname);
    //更新用户
    void update(User user);
    //删除用户
    void delete(Integer uid);

}
