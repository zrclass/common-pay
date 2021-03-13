package org.zrclass.pay.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zrclass.pay.alipay.config.AlipayTemplate;
import org.zrclass.pay.alipay.vo.PayVo;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 *  支付测试类，实际开发中在自己的项目中引入common-pay模块调用template即可
 *
 * @author zhourui
 * */
@RestController
@RequestMapping(value = "/pay")
@AllArgsConstructor
public class PayController {

    @Autowired
    private AlipayTemplate alipayTemplate;


    /**
     *  下单支付
     * */
    @GetMapping(value = "/alipay" , produces = {"text/html;charset=UTF-8"})
    public Object pay () throws Exception {
        //这个接口其实应该是post方式的，但是我这里图方便，直接以get方式访问，
        //且返回格式是text/html，这样前端页面就能直接显示支付宝返回的html片段
        //真实场景下由业务端直接引入此模块，返回code、msg、data那种格式的标准结构，让前端拿到data里的
        //html片段之后自行加载
        PayVo payVo = new PayVo();
        payVo.setSubject("测试商品");
        payVo.setTotal_amount(new BigDecimal("1"));
        payVo.setBody("测试商品");
        payVo.setOut_trade_no(System.currentTimeMillis()+"");

        return alipayTemplate.pay(payVo);
    }

    /**
     *  支付成功的回调
     * */
    @PostMapping(value = "/alipay/fallback")
    public Object fallback (HttpServletRequest request) {
        Map map = request.getParameterMap();
        System.out.println("进入了回调");
        return null;
    }
}
