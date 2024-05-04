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

import com.flower.entity.*;
import com.flower.service.CartService;
import com.flower.service.OrderService;
import com.flower.service.PayService;
import com.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/Pay")
public class PayController {
    @Autowired
    private PayService payService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    //此时订单已经生成，需要做的工作就是将已购商品添加到此订单中
    @RequestMapping("add")
    public String add(HttpSession session){
        Order order = (Order) session.getAttribute("order");
        //获取信息
        Integer camount = (Integer) session.getAttribute("camount");
        System.out.println(camount);
        //如果camount存在的话，那么就是立即支付，如果camount不存在的话就是从购物车进行支付
        if (camount!=null){
            System.out.println("直接支付");
            //获取从立即购买里面的鲜花信息
            Flower flowerByFid = (Flower) session.getAttribute("flowerByFid");
            Pay pay1 = new Pay();
            pay1.setOid(order.getOid());
            pay1.setCid(0);
            pay1.setOstatus("未发货");
            pay1.setPrice(flowerByFid.getFprice());
            pay1.setAmount(camount);
            pay1.setMoney(camount*flowerByFid.getFprice());
            pay1.setSubject(flowerByFid.getFname());
            pay1.setImage(flowerByFid.getFimage());
            payService.addPay(pay1);
            //最后删除session
            session.removeAttribute("camount");
        }else {
            //这里是购物车进行支付
            System.out.println("购物车支付");
            int[] array = (int[]) session.getAttribute("array");
            Pay pay = new Pay();
            for (int i = 0; i < array.length; i++) {
                //每个已购商品都要进行赋值
                //根据cid查询购物车中选中的商品,然后将购物车中的信息赋值给订单中商品的信息
                Cart cartByCid = cartService.findCartByCid(array[i]);
                pay.setOid(order.getOid());
                pay.setCid(cartByCid.getCid());
                pay.setPrice(cartByCid.getFprice());//单价
                pay.setAmount(cartByCid.getCamount());//数量
                pay.setMoney(cartByCid.getMoney());//总价
                pay.setSubject(cartByCid.getSubject());//名称
                pay.setImage(cartByCid.getFimage());//照片
                pay.setOstatus("未发货");
                payService.addPay(pay);
                //通过cid删除购物车中所选中的鲜花
                cartService.deteleByCid(cartByCid.getCid());

            }
        }
        return "redirect:/Flower/home";
    }

    //在我的订单页面中打开某一订单的详细信息
    @RequestMapping("/open")
    public String open(Integer oid, Model model){
        //根据oid查询该订单下的所有信息，不需要对uid进行分类，因为在我的订单中的前提条件已经是根据uid查询
        List<Pay> pays = payService.findPayByOid(oid);
        model.addAttribute("pays",pays);
        return "Cart/deatils-order";
    }

    //修改订单中商品的状态
    @RequestMapping("updatePays")
    public String updatePays(Integer pid,Integer oid,String name){
        //退货
        if (name.equals("退货")){
            String ostatus = "退货申请中";
            payService.updatePays(pid,ostatus);
        }else if (name.equals("确认收货")){
            String ostatus = "已收货";
            payService.updatePays(pid,ostatus);
        }
        String a = "redirect:/Pay/open?oid="+oid;
        return a;
    }
    /*后台界面==================================*/
    @RequestMapping("mainOrderUser")
    public String mainOrderUser(Model model){
        //将所有的订单显示在该页面中
        List<Order> listsList = orderService.listsList();
        model.addAttribute("listsList",listsList);
        return "main/orderUser";
    }

    //点击订单之后进入该用户的订单页面中
    @RequestMapping("/Lists")
    public String Lists(Integer oid, Model model){
        //根据oid查询该订单下的所有信息
        List<Pay> pays = payService.findPayByOid(oid);
        model.addAttribute("pays",pays);
        return "main/orderList";
    }
    //删除商品
    @RequestMapping("deleteByPid")
    public String deleteByPid(Integer pid){
        //通过pid查询oid
        Integer oid = payService.findOrderByPid(pid);
        //通过pid删除商品
        payService.deleteByPid(pid);
        String a = "redirect:/Pay/Lists?oid="+oid;
        return a;
    }

    //后台进行发货处理
    @RequestMapping("start")
    public String start(Integer pid,String name){
        if (name.equals("发货")){
            String ostatus = "已发货";
            payService.updatePays(pid,ostatus);
        }else if (name.equals("同意")){
            String ostatus = "已退货";
            payService.updatePays(pid,ostatus);
        }
        //通过pid查询oid
        Integer oid = payService.findOrderByPid(pid);
        String a = "redirect:/Pay/Lists?oid="+oid;
        return a;
    }

}
