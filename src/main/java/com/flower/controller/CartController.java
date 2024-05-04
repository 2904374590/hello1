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

import com.flower.dao.CartDao;
import com.flower.entity.Cart;
import com.flower.entity.User;
import com.flower.service.CartService;
import com.flower.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("Cart")
public class CartController {
    @Autowired
    private CartService cartService;
    //删除购物车(前台)
    @RequestMapping("/deleteByCid")
    public String deteleByCid(Integer cid){
        cartService.deteleByCid(cid);
        return "redirect:/Cart/carts";
    }
    //显示购物车（后台）
    @RequestMapping("queryCarts")
    public String queryCarts(Model model){
        //显示所有的购物车
        List<Cart> cartLists = cartService.queryCarts();
        model.addAttribute("cartLists",cartLists);
        return "main/cartLists";
    }
    //删除购物车
    @RequestMapping("deleteCartByCid")
    public String deleteCartByCid(Integer cid){
        cartService.deteleByCid(cid);
        return "redirect:/Cart/queryCarts";
    }
    //加入购物车
    @RequestMapping("addCart")
    public String addCart(Cart cart, Model model){
            //将前端传来的参数添加到数据库中
        System.out.println();
        if (cart.getCamount()==0){
            String msg = "请选择加入购物车中的数量!!!";
            model.addAttribute("msg",msg);
            return "main/index";
        }else {
            model.addAttribute("carts",cart);
            cartService.addCart(cart);
            String c = "redirect:/Flower/con?fid="+cart.getFid();
            return c;
        }
    }
    //在购物车页面展示鲜花的信息
    @RequestMapping("carts")
    public String carts(HttpSession session,Model model){
        //根据uid查询商品，查询该用户所有购买的商品
        User loginUser = (User) session.getAttribute("loginUser");
        //根据uid查询商品
        List<Cart> cartList = cartService.queryCart(loginUser.getUid());
        model.addAttribute("cartList",cartList);
        return "Cart/cart";
    }
    //购物车到订单页面的跳转
    @RequestMapping("con")
    public String con(HttpServletRequest request, Model model, Double money, String num, HttpSession session){
        if (num.equals("0")){
            String msg = "您所选择的数量不能为0！！！";
            model.addAttribute("msg",msg);
            return "main/index";
        }else {

                List<Cart> list = new ArrayList<Cart>();
                //长度5 下标从0开始 到4
                //以逗号分割，得出的数据存到 result 里面
                String[] nu = num.split(",");
                String array[] = request.getParameterValues("coffee");
            for (String s : array) {
                System.out.println(s+"ok");
            }
                int[] array1 = Arrays.asList(nu).stream().mapToInt(Integer::parseInt).toArray();
                int[] array2 = Arrays.asList(array).stream().mapToInt(Integer::parseInt).toArray();
                session.setAttribute("array",array2);
                for (int i = 0; i < array.length; i++) {
                    //查找到当前对应的商品
                    Cart cartByCid = cartService.findCartByCid(array2[i]);
                    //在购物车界面修改该商品的数量和钱数=数量x单价
                    cartByCid.setCamount(array1[i]);
                    cartByCid.setMoney(array1[i]*cartByCid.getFprice());
                    //保存该商品现在的值
                    cartService.saveCart(cartByCid);
                    list.add(cartByCid);
                }
                //所有商品的总钱数
                model.addAttribute("money",money);
                model.addAttribute("list",list);
                return "Cart/before-order";
            }
    }
}
