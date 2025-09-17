package com.wanmi.sbc.customer.provider.impl.growthvalue;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>客户成长值明细表服务接口实现</p>
 *
 * @author yang
 * @since 2019/2/22
 */
@RestController
@Validated
public class CustomerGrowthValueController implements CustomerGrowthValueProvider {

    @Autowired
    private MqSendProvider mqSendProvider;

    /**
     * 新增客户成长值明细
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse increaseGrowthValue(@RequestBody @Valid CustomerGrowthValueAddRequest request) {
        MqSendDelayDTO mqSendDelayDTO = new MqSendDelayDTO();
        mqSendDelayDTO.setTopic(ProducerTopic.INCREASE_GROWTH_VALUE_NEW);
        mqSendDelayDTO.setData(JSONObject.toJSONString(request));
        mqSendDelayDTO.setDelayTime(6000L);
        mqSendProvider.sendDelay(mqSendDelayDTO);
        return BaseResponse.SUCCESSFUL();
    }
}
