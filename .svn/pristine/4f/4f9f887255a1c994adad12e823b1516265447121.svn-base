package com.wanmi.sbc.empower.provider.impl.pay.unionb2b;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.pay.unionb2b.UnionB2BProvider;
import com.wanmi.sbc.empower.api.request.pay.unionb2b.UnionDirectRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.response.pay.RefundResponse;
import com.wanmi.sbc.empower.pay.sdk.AcpService;
import com.wanmi.sbc.empower.pay.sdk.SDKConfig;
import com.wanmi.sbc.empower.pay.service.PayDataService;
import com.wanmi.sbc.empower.pay.service.unionb2b.UnionB2bPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @program:
 * @description: 企业银联
 * @author: zhangyong
 * @create: 2021-03-09 10:15
 **/
@RestController
@Validated
@Slf4j
public class UnionB2BController implements UnionB2BProvider {

    @Autowired
    private UnionB2bPayService unionB2bPayService;

    @Autowired
    private PayDataService payDataService;

    @Override
    public BaseResponse<Map<String, String>> getUnionPayResult(@RequestBody UnionPayRequest unionPay) {
        String merId = unionPay.getApiKey();
        String orderId = unionPay.getOutTradeNo();
        String txnTime = unionPay.getTxnTime();

        Map<String, String> data = new HashMap<>();

        String encoding = "UTF-8";
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        // 版本号
        data.put("version", SDKConfig.getConfig().getVersion());
        // 字符集编码 可以使用UTF-8,GBK两种方式
        data.put("encoding", encoding);
        // 签名方法
        data.put("signMethod", SDKConfig.getConfig().getSignMethod());
        // 交易类型 00-默认
        data.put("txnType", "00");
        // 交易子类型  默认00
        data.put("txnSubType", "00");
        // 业务类型
        data.put("bizType", "000202");

        /***商户接入参数***/
        // 商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        data.put("merId", merId);
        // 接入类型，商户接入固定填0，不需修改
        data.put("accessType", "0");

        /***要调通交易以下字段必须修改***/
        // ****商户订单号，每次发交易测试需修改为被查询的交易的订单号
        data.put("orderId", orderId);
        // ****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间
        data.put("txnTime", txnTime);

        /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
        // 报文中certId,
        Map<String, String> reqData = AcpService.sign(data, encoding);
        // signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String url = SDKConfig.getConfig().getSingleQueryUrl();
        // 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
        Map<String, String> rspData = AcpService.post(reqData, url, encoding);
        // 发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;
        // 这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        // 应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
        return BaseResponse.success(rspData);
    }


    /**
     * 银联企业支付 同步回调添加交易数据
     *
     * @param resMap
     * @return
     */
    @Override
    public BaseResponse unionCallBack(@RequestBody Map<String, String> resMap) {

//        PayTradeRecord payTradeRecord = payDataService.queryByBusinessId(resMap.get("orderId"));
//        if (payTradeRecord == null) {
//            payTradeRecord = new PayTradeRecord();
//            payTradeRecord.setId(GeneratorUtils.generatePT());
//        }
        unionB2bPayService.unionCallBack(resMap, resMap.get("orderId"));
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse unionCallBackByTimeSeries(@RequestBody Map<String, String> resMap) {


        unionB2bPayService.unionCallBackByTimeSeries(resMap, resMap.get("orderId"));
        return BaseResponse.SUCCESSFUL();
    }


    @Override
    public BaseResponse<Boolean> unionCheckSign(@RequestBody Map<String, String> validateData) {
        return BaseResponse.success(AcpService.validate(validateData, "UTF-8"));
    }


    @Override
    public BaseResponse<RefundResponse> unionDirectRefund(@RequestBody @Valid UnionDirectRefundRequest request) {
        return BaseResponse.success(new RefundResponse(unionB2bPayService.unionDirectRefund(request)));
    }
}
