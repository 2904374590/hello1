package com.flower.entity;/*
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer oid;
    private Integer uid;
    //订单号
    private String onumber;
    //备注
    private String oremark;
    //订单提交时间
    private String otime;
    //总价钱
    private Double oprice;
    //收货地址
    private String oaddress;
    //电话
    private String ophone;
    //姓名
    private String oname;
    //订单内容
    private String orderName;
}
