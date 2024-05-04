package com.flower.config;/*
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

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("register").setViewName("regist");
        registry.addViewController("index").setViewName("homePage");
        registry.addViewController("revise").setViewName("revise");
        /*也就是说，如果写了这一行代码，就不用协议俄国controller来进行跳转，可以在前端
        * 直接进行页面的跳转，比如@{/login}，就代表直接跳转到login页面，否则还要写一个
        * login的controller来进行页面跳转，如果跳转到login这个页面的话，-*/
        //前面是自己设置的路径，后面是真实的路径
        registry.addViewController("login").setViewName("login");
        registry.addViewController("herb_01").setViewName("herb_01");
        registry.addViewController("cart").setViewName("Cart/cart");
        registry.addViewController("before").setViewName("Cart/before-order");
        registry.addViewController("order").setViewName("Cart/deatils-order");
        //以下是管理员界面
        registry.addViewController("logMain").setViewName("main/logmain");
        registry.addViewController("main").setViewName("main/main");
        registry.addViewController("flower").setViewName("main/FlowerInformation");
        registry.addViewController("sort").setViewName("main/SortClassification");
        registry.addViewController("addSort").setViewName("main/addSort");
        registry.addViewController("updateSort").setViewName("main/updateSort");
        registry.addViewController("addFlower").setViewName("main/addFlower");
    }
}
