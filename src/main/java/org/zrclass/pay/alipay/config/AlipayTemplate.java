package org.zrclass.pay.alipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.zrclass.pay.alipay.vo.PayVo;

/**
 * @author zhourui 20114535
 * @version 1.0
 * @date 2021/3/13 14:01
 */
@ConfigurationProperties("alipay")
@Component
public class AlipayTemplate {
    //应用id
    private String appId;

    //私钥
    private String privateKey;

    //公钥
    private String publicKey;

    //支付宝网关
    private String gateway;

    //支付成功后的接口回调地址，不是回调的友好页面，不要弄混了
    private String notifyUrl;

    //支付成功后要跳转的页面
    private String returnUrl;

    public  String pay(PayVo vo)  throws Exception{
        Factory.setOptions(getOptions());
        //调用sdk，发起支付
        AlipayTradePagePayResponse response = Factory.Payment
                //选择网页支付平台
                .Page()
                //调用支付方法，设置订单名称、我们自己系统中的订单号、金额、回调页面
                .pay(vo.getSubject() , vo.getOut_trade_no(), vo.getTotal_amount().toString() , returnUrl);

        //这里的response.vody，就是一个可以直接加载的html片段，
        // 这里为了简单我直接返回这个片段，前端直接
        return response.body;
    }


    private Config getOptions() {
        //这里省略了一些不必要的配置，可参考文档的说明

        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = this.gateway;
        config.signType = "RSA2";

        config.appId = this.appId;

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = this.privateKey;

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = this.publicKey;

        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = notifyUrl;

        return config;
    }
}




