package com.flower;

import com.flower.dao.CartDao;
import com.flower.dao.FlowerDao;
import com.flower.entity.Cart;
import com.flower.entity.Flower;
import com.flower.service.PayService;
import com.flower.service.UserService;
import com.flower.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class FlowerApplicationTests {
@Autowired
private PayService payService;
    @Test
    void contextLoads(){
        Integer orderByPid = payService.findOrderByPid(5);
        System.out.println(orderByPid);
    }

}
