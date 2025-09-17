package com.wanmi.sbc.communitysetting;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.communitysetting.CommunitySettingProvider;
import com.wanmi.sbc.marketing.api.provider.communitysetting.CommunitySettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingByIdRequest;
import com.wanmi.sbc.marketing.api.request.communitysetting.CommunitySettingModifyRequest;
import com.wanmi.sbc.marketing.api.response.communitysetting.CommunitySettingByIdResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "社区团购商家设置表管理API", description =  "CommunitySettingController")
@RestController
@RequestMapping(value = "/communitySetting")
public class CommunitySettingController {

    @Autowired
    private CommunitySettingQueryProvider communitySettingQueryProvider;

    @Autowired
    private CommunitySettingProvider communitySettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Operation(summary = "根据id查询社区团购商家设置表")
    @GetMapping
    public BaseResponse<CommunitySettingByIdResponse> getById() {
        Long storeId = commonUtil.getStoreId();
        CommunitySettingByIdRequest idReq = new CommunitySettingByIdRequest();
        idReq.setStoreId(storeId);
        return communitySettingQueryProvider.getById(idReq);
    }

    @MultiSubmit
    @Operation(summary = "修改社区团购商家设置表")
    @PutMapping
    public BaseResponse modify(@RequestBody @Valid CommunitySettingModifyRequest modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreId());
        communitySettingProvider.modify(modifyReq);
        operateLogMqUtil.convertAndSend("应用", "修改社区团购商家设置表", "修改社区团购商家设置表内容：".concat(JSON.toJSONString(modifyReq)));
        return BaseResponse.SUCCESSFUL();
    }
}
