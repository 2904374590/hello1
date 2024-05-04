package com.flower.controller;/*
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
import com.flower.entity.Order;
import com.flower.entity.User;
import com.flower.service.AlipayService;
import com.flower.service.FlowerService;
import com.flower.service.OrderService;
import com.flower.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private PayService payService;
    @Autowired
    private FlowerService flowerService;
    //创建订单
    @RequestMapping("/pay")
    public String pay(Order order,HttpSession session){
        //创建当前订单
        order.setOnumber(alipayService.generateTradeNo());
        orderService.addOrder(order);
        //通过订单号查询该订单
        Order order1 = orderService.queryOrderByOnum(order.getOnumber());
        session.setAttribute("order",order1);
        //先支付，然后再在已购商品中添加商品
        String a = "redirect:/pay/topay?money="+order.getOprice();
        return a;
    }

    //在我的订单页面显示我的订单信息
    @RequestMapping("lists")
    private String lists(HttpSession session,Model model){
        //查询该用户的所有订单信息
        User loginUser = (User) session.getAttribute("loginUser");
        List<Order> lists = orderService.lists(loginUser.getUid());
        model.addAttribute("lists",lists);
        return "Cart/my-order";
    }

    //删除整个订单
    @RequestMapping("deleteOrder")
    public String deleteOrder(Integer oid){
        //首先删除该订单中的所有商品
        payService.deleteByOid(oid);
        //然后删除该订单
        orderService.deleteByOid(oid);
        return "redirect:/Pay/mainOrderUser";
    }

    //通过订单号搜索订单（模糊搜索）
    @RequestMapping("queryOrder")
    public String queryOrder(String onumber,Model model){
        //模糊搜索
        List<Order> orderLists = orderService.queryOrderByOnumber(onumber);
        model.addAttribute("listsList",orderLists);
        return "main/orderUser";
    }

    //立即购买到订单页面的跳转
    @RequestMapping("Con")
    public String Con(Integer fid,Integer camount,Model model,HttpSession session){
        if (camount==0){
            String msg = "请选择购买商品的数量！！！";
            model.addAttribute("msg",msg);
            return "main/index";
        }else {
            //根据fid查询该鲜花
            Flower flowerByFid = flowerService.findFlowerByFid(fid);
            session.setAttribute("flowerByFid",flowerByFid);
            model.addAttribute("flowerByFid",flowerByFid);
            double money = flowerByFid.getFprice() * camount;
            model.addAttribute("money",money);
            session.setAttribute("camount",camount);
            model.addAttribute("camount",camount);
            return "Cart/beforeOrder";
        }
    }

}
