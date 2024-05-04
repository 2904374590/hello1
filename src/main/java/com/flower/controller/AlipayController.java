package com.flower.controller;
/*
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

import com.alipay.easysdk.factory.Factory;
import com.flower.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/pay")
public class AlipayController {

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/topay")
    @ResponseBody
    public String toPay(@RequestParam(defaultValue = "楠木花市商品中心")String subject, BigDecimal money) throws Exception {
        String form = alipayService.toPay(subject, money);
        return form;
    }
}
