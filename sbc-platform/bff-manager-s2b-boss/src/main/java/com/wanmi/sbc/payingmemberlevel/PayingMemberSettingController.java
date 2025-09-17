package com.wanmi.sbc.payingmemberlevel;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelStatusRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.request.systemconfig.PayingMemberModifyRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.PayingMemberSettingResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @author
 * @className PayingMemberController
 * @description
 * @date 2022/5/13 9:50 AM
 **/
@Tag(name = "PayingMemberSettingController", description = "S2B 平台端-付费会员设置API")
@RestController
@Validated
@RequestMapping("/payingMember")
public class PayingMemberSettingController {

    @Autowired
    private SystemConfigSaveProvider systemConfigSaveProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    @Autowired
    private PayingMemberLevelProvider payingMemberLevelProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Operation(summary = "编辑付费会员设置")
    @GlobalTransactional
    @PutMapping("/setting")
    public BaseResponse modifyPayMemberSetting(@Valid @RequestBody PayingMemberModifyRequest payingMemberModifyRequest) {
        if (NumberUtils.INTEGER_ONE.equals(payingMemberModifyRequest.getEnable())) {
            Long total = payingMemberLevelQueryProvider.countLevels(PayingMemberLevelQueryRequest.builder().delFlag(DeleteFlag.NO).build()).getContext();
            if (total == 0) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010147);
            }
        }
        systemConfigSaveProvider.modifyPayingMemberSetting(payingMemberModifyRequest);
        //同步等级状态
        Integer levelState = NumberUtils.INTEGER_ZERO.equals(payingMemberModifyRequest.getEnable())
                ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
                payingMemberLevelProvider.modifyStatus(PayingMemberLevelStatusRequest.builder().status(levelState).build());
        operateLogMQUtil.convertAndSend("设置", "修改付费会员设置", "修改付费会员设置");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询付费会员设置")
    @GetMapping("/setting")
    public BaseResponse<PayingMemberSettingResponse> getPayMemberSetting() {
        ConfigQueryRequest request = new ConfigQueryRequest();
        request.setConfigType(ConfigType.PAYING_MEMBER.toString());
        request.setDelFlag(DeleteFlag.NO.toValue());
        ConfigVO config = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext().getConfig();
        PayingMemberSettingResponse res = JSONObject.parseObject(config.getContext(), PayingMemberSettingResponse.class);
        return BaseResponse.success(res);
    }
}
