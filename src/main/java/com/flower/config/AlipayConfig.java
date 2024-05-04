package com.flower.config;
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

import com.flower.utils.FileUtils;
import org.springframework.stereotype.Component;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;

@Component
public class AlipayConfig {

    // 1. 设置参数（全局只需设置一次）
    static {
        Factory.setOptions(getOptions());
    }

    private static Config getOptions() {
        Config config = new Config();

        config.protocol = "https";
       // 沙箱环境修改为 openapi.alipaydev.com
       config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        //  config.gatewayHost = "openapi-sandbox.dl.alipaydev.com/gateway.do";


        config.signType = "RSA2";

        config.appId = "9021000136660300";

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = FileUtils.readFileOfTxt("C:\\Users\\X\\Documents\\支付宝开放平台密钥工具\\密钥20240430185154\\应用私钥RSA2048-敏感数据，请妥善保管.txt");

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的-支付宝公钥-字符串即可
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmViCsQp7dIuLZJXJQI+TsTcTvgfROO1RrfOSWMvNNFR6UQJeZELJV55OXJ4rUvEdCdn6vRCAkyzPVaHRl4uwDK7QD92WvgKx+XERg7/iVxzGVJnRMwIV3nUQ/qEHvQbaa439PsV6/q6mnpxYk/6SdiwIjtzsbEBLxXZtL2Zib3PCFtK3wPIUW7HruVjnJ8Ob/9eSugaw3vY/FMZVW9ZDivrC7BDOw1eg6rRmR/XXMHVhwopqT48LbuQQVYh3S34V9LmUR9cQzoRAGSRJwhld8/xATTtzg8AM4VKnwE/Qa+pBMeZDXr8KMf82Me6NOzQHuOy4TrIrEORRXL0bd8j6lwIDAQAB";
        return config;
    }
}

