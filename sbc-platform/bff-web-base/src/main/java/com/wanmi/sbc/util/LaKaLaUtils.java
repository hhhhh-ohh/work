package com.wanmi.sbc.util;

import com.alibaba.fastjson.JSONObject;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author
 * @className LaKaLaUtils
 * @description
 * @date 2023/8/3 15:40
 **/
@Slf4j
@Service
public class LaKaLaUtils {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取支付开关状态
     *
     * @return true 开启
     */
    public Boolean getGatewayOpen() {
        PayGatewayVO payGatewayVO = JSONObject.parseObject(redisUtil.getString(RedisKeyConstant.LAKALA_PAY_SETTING), PayGatewayVO.class);
        PayGatewayVO payGatewayVO2 = JSONObject.parseObject(redisUtil.getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING), PayGatewayVO.class);

        if (Objects.nonNull(payGatewayVO) && IsOpen.YES.equals(payGatewayVO.getIsOpen())) {
            return Boolean.TRUE;
        }
        if (Objects.nonNull(payGatewayVO2) && IsOpen.YES.equals(payGatewayVO2.getIsOpen())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 获取支付开关状态
     *
     * @return true 开启
     */
    public PayGatewayVO getPayGatewayVO() {
        PayGatewayVO payGatewayVO = JSONObject.parseObject(redisUtil.getString(RedisKeyConstant.LAKALA_PAY_SETTING), PayGatewayVO.class);
        PayGatewayVO payGatewayVO2 = JSONObject.parseObject(redisUtil.getString(RedisKeyConstant.LAKALA_CASHER_PAY_SETTING), PayGatewayVO.class);
        if (Objects.nonNull(payGatewayVO) && IsOpen.YES.equals(payGatewayVO.getIsOpen())) {
            return payGatewayVO;
        }
        if (Objects.nonNull(payGatewayVO2) && IsOpen.YES.equals(payGatewayVO2.getIsOpen())) {
            return payGatewayVO2;
        }
        return payGatewayVO2;
    }
}
