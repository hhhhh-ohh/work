package com.wanmi.sbc.dada;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.dada.request.DaDaAccountRequest;
import com.wanmi.sbc.dada.response.DaDaAccountResponse;
import com.wanmi.sbc.empower.api.provider.logisticssetting.LogisticsSettingProvider;
import com.wanmi.sbc.empower.api.provider.logisticssetting.LogisticsSettingQueryProvider;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingAddRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingByLogisticsTypeRequest;
import com.wanmi.sbc.empower.api.request.logisticssetting.LogisticsSettingModifyRequest;
import com.wanmi.sbc.empower.bean.enums.LogisticsType;
import com.wanmi.sbc.empower.bean.vo.LogisticsSettingVO;
import com.wanmi.sbc.util.CommonUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author EDZ
 * @className DaDaConfigController
 * @description 达达同城配送配置
 * @date 2021/6/30 13:37
 **/
@Tag(name = "DaDaConfigController", description = "达达同城配送配置")
@RestController
@Validated
@RequestMapping("/dada/config")
public class DaDaConfigController {

    @Autowired
    private LogisticsSettingQueryProvider logisticsSettingQueryProvider;

    @Autowired
    private LogisticsSettingProvider logisticsSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * @description 达达同城配送账户信息添加
     * @author  EDZ
     * @date 2021/6/30 14:24
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/accountSave")
    public BaseResponse samecityAccountAdd(@RequestBody DaDaAccountRequest request) {
        LogisticsType logisticsType = LogisticsType.DADA;
        try {
            LogisticsSettingVO logisticsSettingVO
                    = logisticsSettingQueryProvider.getByLogisticsType(
                    LogisticsSettingByLogisticsTypeRequest.builder().logisticsType(logisticsType).build()
            ).getContext().getLogisticsSettingVO();
            LogisticsSettingModifyRequest logisticsSettingModifyRequest = KsBeanUtil.convert(logisticsSettingVO, LogisticsSettingModifyRequest.class);
            logisticsSettingModifyRequest.setCustomerKey(request.getAppKey());
            logisticsSettingModifyRequest.setDeliveryKey(request.getAppSecret());
            logisticsSettingModifyRequest.setEnableStatus(EnableStatus.fromValue(request.getStatus()));
            logisticsSettingModifyRequest.setShopNo(request.getShopNo());
            logisticsSettingModifyRequest.setCallbackUrl(request.getCallbackUrl());
            logisticsSettingProvider.modify(logisticsSettingModifyRequest);
        }catch (SbcRuntimeException e) {
            String customerId = commonUtil.getOperatorId();
            logisticsSettingProvider.add(LogisticsSettingAddRequest.builder()
                    .logisticsType(LogisticsType.DADA)
                    .customerKey(request.getAppKey())
                    .deliveryKey(request.getAppSecret())
                    .enableStatus(EnableStatus.fromValue(request.getStatus()))
                    .realTimeStatus(DefaultFlag.YES)
                    .subscribeStatus(DefaultFlag.YES)
                    .callbackUrl(request.getCallbackUrl())
                    .shopNo(request.getShopNo())
                    .createPerson(customerId)
                    .updatePerson(customerId)
                    .delFlag(DeleteFlag.NO)
                    .build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 达达同城配送账户信息查询
     * @author  EDZ
     * @date 2021/6/30 14:34
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.setting.o2o.api.response.DaDaAccountResponse>
     **/
    @GetMapping("/queryAccountInfo")
    public BaseResponse<DaDaAccountResponse> queryAccountInfo() {
        LogisticsType logisticsType = LogisticsType.DADA;
        LogisticsSettingVO logisticsSettingVO
                = logisticsSettingQueryProvider.getByLogisticsType(
                LogisticsSettingByLogisticsTypeRequest.builder().logisticsType(logisticsType).build()
        ).getContext().getLogisticsSettingVO();
        return BaseResponse.success(DaDaAccountResponse.builder()
                .appKey(logisticsSettingVO.getCustomerKey())
                .appSecret(logisticsSettingVO.getDeliveryKey())
                .status(logisticsSettingVO.getEnableStatus().toValue())
                .callbackUrl(logisticsSettingVO.getCallbackUrl())
                .shopNo(logisticsSettingVO.getShopNo())
                .build());
    }
}

