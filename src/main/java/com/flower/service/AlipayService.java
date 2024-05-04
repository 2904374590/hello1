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

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class AlipayService {

    /**
     * 生成支付表单
     * @param subject
     * @param money
     * @return
     * @throws Exception
     */
    public String toPay(String subject, BigDecimal money) throws Exception {
        // 最后一个参数是支付完成之后跳转到的界面, 一般为项目的首页
        AlipayTradePagePayResponse pay = Factory.Payment.Page().pay(subject, this.generateTradeNo(),
                //String.valueOf(money), "http://localhost:9999/Pay/add");
                String.valueOf(money), "http://localhost:9999/Pay/add");
        System.out.println(generateTradeNo());
        String payForm = null;
        if (ResponseChecker.success(pay)) {
            payForm = pay.body;
        }
        return payForm;
    }

    /**
     * 通过时间生成外部订单号 out_trade_no
     * @return
     */
    public String generateTradeNo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String tradeNo = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        return tradeNo;
    }
}
