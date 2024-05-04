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


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    //Shiro-Filter-Factory-Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/Flower/herb","authc");
        filterMap.put("/Flower/con","authc");
        filterMap.put("/Flower/queryFlowers","authc");
        filterMap.put("/Cart/carts","authc");
        filterMap.put("/Order/lists","authc");
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录的请求,例如草本那个导航栏，如果用户没有登录，就默认到login这个登录页面
        bean.setLoginUrl("/user/login");
        //未授权页面，未经用户授权就访问noauth这个页面
        // bean.setUnauthorizedUrl("/user/noauth");
        return bean;
    }

    //Default-WebSecurity-Manager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    //创建realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

}

