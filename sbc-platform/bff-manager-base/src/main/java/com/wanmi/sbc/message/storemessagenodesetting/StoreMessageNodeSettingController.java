package com.wanmi.sbc.message.storemessagenodesetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingProvider;
import com.wanmi.sbc.setting.api.provider.storemessagenodesetting.StoreMessageNodeSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingListRequest;
import com.wanmi.sbc.setting.api.request.storemessagenodesetting.StoreMessageNodeSettingModifyStatusRequest;
import com.wanmi.sbc.setting.api.response.storemessagenodesetting.StoreMessageNodeSettingListResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@Tag(name =  "商家消息节点设置管理API", description =  "StoreMessageNodeSettingController")
@RestController
@Validated
@RequestMapping(value = "/storeMessageNodeSetting")
public class StoreMessageNodeSettingController {

    @Autowired
    private StoreMessageNodeSettingQueryProvider storeMessageNodeSettingQueryProvider;

    @Autowired
    private StoreMessageNodeSettingProvider storeMessageNodeSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "列表查询商家消息节点设置")
    @GetMapping("/list")
    public BaseResponse<StoreMessageNodeSettingListResponse> getList() {
        StoreMessageNodeSettingListRequest listReq = new StoreMessageNodeSettingListRequest();
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.setStoreId(getStoreId());
        return storeMessageNodeSettingQueryProvider.list(listReq);
    }

    @Operation(summary = "修改商家消息节点开关状态")
    @PutMapping("/modifyStatus")
    public BaseResponse modifyStatus(@RequestBody @Valid StoreMessageNodeSettingModifyStatusRequest modifyStatusRequest) {
        modifyStatusRequest.setStoreId(getStoreId());
        modifyStatusRequest.setUpdatePerson(commonUtil.getOperatorId());
        modifyStatusRequest.setPlatformType(StoreMessagePlatform.fromValue(commonUtil.getOperator().getPlatform()));
        return storeMessageNodeSettingProvider.modifyStatus(modifyStatusRequest);
    }

    /**
     * 获取商家ID
     * @return
     */
    private Long getStoreId() {
        return commonUtil.getOperator().getPlatform() == Platform.PLATFORM ? Constants.BOSS_DEFAULT_STORE_ID : commonUtil.getStoreId();
    }

}
