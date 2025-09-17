package com.wanmi.sbc.empower.provider.impl.pay.unioncloud;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.pay.unioncloud.UnionCloudPayProvider;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.pay.service.unioncloud.UnionCloudPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: shiyuan-service
 * @description: 银联支付相关接口
 * @author: zhangyong
 * @create: 2021-03-09 10:15
 **/
@RestController
@Validated
@Slf4j
public class UnionCloudPayController implements UnionCloudPayProvider {

    @Autowired
    private UnionCloudPayServiceImpl unionCloudPayServiceImpl;

    /***
     * 银联加密公钥更新查询接口
     *
     * @date 14:46 2021/3/15
     * @author zhangyong
     * @return   {@link BaseResponse < Map< String, String>>}
     */
    @Override
    public BaseResponse<Map<String, String>> getUpdateEncryptCer(@RequestBody UnionPayRequest unionPay) {
        return BaseResponse.success(this.unionCloudPayServiceImpl.getUpdateEncryptCer(unionPay));
    }

    @Override
    public BaseResponse<String> getTn(@RequestBody BasePayRequest basePayRequest) {
        return BaseResponse.success(unionCloudPayServiceImpl.getTn(basePayRequest));
    }

}
