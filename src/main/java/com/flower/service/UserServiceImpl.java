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

import com.flower.dao.UserDao;
import com.flower.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    //查询所有用户
    @Override
    public List<User> lists() {
        return userDao.lists();
    }
    //注册用户
    @Override
    public void register(User user) {
        //注册用户，将前端传来的user进行注册,但是你要判断一下，如果有用户名相同的，那就不能添加
        //那么就要在dao声明一个方法，通过用户名查询用户，如果查询不到，那么就可以注册用户，如果可以查询到，那么不可以注册，返回提醒！
        User userDB = userDao.findByUserName(user.getUname());
        if(!ObjectUtils.isEmpty(userDB)) throw new RuntimeException("当前用户已被注册！");
        //明文加密
        /*String newPassword = DigestUtils.md5DigestAsHex(user.getUpwd().getBytes(StandardCharsets.UTF_8));
        user.setUpwd(newPassword);*/
        //此时走到这块就说明用户没被注册！就需要在dao写一个save方法，将user存入数据库中！逻辑一定要清晰
        userDao.save(user);
    }
    //用户登录
    @Override
    public User loginByUserName(String name) {
        return userDao.findByUserName(name);
    }
    //根据id查询用户
    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }
    //通过用户名查询用户（模糊查询）
    @Override
    public List<User> queryByUsername(String uname) {
        return userDao.findByUsername(uname);
    }
    //更新用户
    @Override
    public void update(User user) {
        userDao.update(user);
    }
    //删除用户
    @Override
    public void delete(Integer uid) {
        userDao.delete(uid);
    }
}
