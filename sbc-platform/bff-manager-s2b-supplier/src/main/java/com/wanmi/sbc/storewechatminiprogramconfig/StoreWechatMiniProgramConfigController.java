package com.wanmi.sbc.storewechatminiprogramconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.storewechatminiprogramconfig.StoreWechatMiniProgramConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.storewechatminiprogramconfig.StoreWechatMiniProgramConfigSaveProvider;
import com.wanmi.sbc.setting.api.request.storewechatminiprogramconfig.StoreWechatMiniProgramConfigAddRequest;
import com.wanmi.sbc.setting.api.request.storewechatminiprogramconfig.StoreWechatMiniProgramConfigByStoreIdRequest;
import com.wanmi.sbc.setting.api.request.storewechatminiprogramconfig.StoreWechatMiniProgramConfigModifyRequest;
import com.wanmi.sbc.setting.api.response.storewechatminiprogramconfig.StoreWechatMiniProgramConfigAddResponse;
import com.wanmi.sbc.setting.api.response.storewechatminiprogramconfig.StoreWechatMiniProgramConfigByStoreIdResponse;
import com.wanmi.sbc.setting.api.response.storewechatminiprogramconfig.StoreWechatMiniProgramConfigModifyResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "门店微信小程序配置管理API", description =  "StoreWechatMiniProgramConfigController")
@RestController
@Validated
@RequestMapping(value = "/storewechatminiprogramconfig")
public class StoreWechatMiniProgramConfigController {

    @Autowired
    private StoreWechatMiniProgramConfigQueryProvider storeWechatMiniProgramConfigQueryProvider;

    @Autowired
    private StoreWechatMiniProgramConfigSaveProvider storeWechatMiniProgramConfigSaveProvider;

    @Autowired
    private CommonUtil commonUtil;


    @Operation(summary = "查询门店微信小程序配置")
    @GetMapping("/getConfig")
    public BaseResponse<StoreWechatMiniProgramConfigByStoreIdResponse> getConfig() {
        return storeWechatMiniProgramConfigQueryProvider.getByStoreId(StoreWechatMiniProgramConfigByStoreIdRequest.builder()
                .storeId(commonUtil.getStoreId())
                .build());
    }

    @Operation(summary = "新增门店微信小程序配置")
    @PostMapping("/add")
    public BaseResponse<StoreWechatMiniProgramConfigAddResponse> add(@RequestBody @Valid StoreWechatMiniProgramConfigAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreateTime(LocalDateTime.now());
        addReq.setStoreId(commonUtil.getStoreId());
        addReq.setCompanyInfoId(commonUtil.getCompanyInfoId());
        return storeWechatMiniProgramConfigSaveProvider.add(addReq);
    }

    @Operation(summary = "修改门店微信小程序配置")
    @PutMapping("/modify")
    public BaseResponse<StoreWechatMiniProgramConfigModifyResponse> modify(@RequestBody @Valid StoreWechatMiniProgramConfigModifyRequest modifyReq) {
        modifyReq.setUpdateTime(LocalDateTime.now());
        return storeWechatMiniProgramConfigSaveProvider.modify(modifyReq);
    }
}
