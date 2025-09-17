package com.wanmi.sbc.system;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.businessconfig.BusinessConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.businessconfig.BusinessConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.businessconfig.BusinessConfigAddRequest;
import com.wanmi.sbc.setting.api.request.businessconfig.BusinessConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.businessconfig.BusinessConfigAddResponse;
import com.wanmi.sbc.setting.api.response.businessconfig.BusinessConfigModifyResponse;
import com.wanmi.sbc.setting.api.response.businessconfig.BusinessConfigRopResponse;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BusinessConfigController", description = "招商页设置Api")
@RestController
@Validated
@RequestMapping("/business")
public class BusinessConfigController {

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private BusinessConfigQueryProvider businessConfigQueryProvider;

    @Autowired
    private BusinessConfigSaveProvider businessConfigSaveProvider;

    /**
     * 查询招商页设置
     *
     * @return
     */
    @Operation(summary = "查询招商页设置")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public BaseResponse<BusinessConfigRopResponse> findConfig() {
        return businessConfigQueryProvider.getInfo();
    }


    /**
     * 保存招商页设置
     *
     * @param addRequest
     * @return
     */
    @Operation(summary = "保存招商页设置")
    @RequestMapping(value = "/config", method = RequestMethod.POST)
    public BaseResponse<BusinessConfigRopResponse> saveBaseConfig(@RequestBody BusinessConfigAddRequest addRequest) {
        BusinessConfigAddResponse response = businessConfigSaveProvider.add(addRequest).getContext();
        operateLogMQUtil.convertAndSend("设置", "保存招商页设置", "保存招商页设置");
        return BaseResponse.success(KsBeanUtil.convert(response.getBusinessConfigVO(),
                BusinessConfigRopResponse.class));
    }


    /**
     * 修改基本设置
     *
     * @param
     * @return
     */
    @Operation(summary = "修改基本设置")
    @RequestMapping(value = "/config", method = RequestMethod.PUT)
    public BaseResponse<BusinessConfigRopResponse> updateBaseConfig(@RequestBody BusinessConfigModifyRequest updateRopRequest) {
        if (StringUtils.isEmpty(updateRopRequest.getBusinessConfigId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BusinessConfigModifyResponse response = businessConfigSaveProvider.modify(updateRopRequest).getContext();
        operateLogMQUtil.convertAndSend("设置", "编辑招商页设置", "编辑招商页设置");
        return BaseResponse.success(KsBeanUtil.convert(response.getBusinessConfigVO(),
                BusinessConfigRopResponse.class));
    }
}
