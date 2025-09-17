package com.wanmi.sbc.appexternalconfig;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.appexternalconfig.AppExternalConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.appexternalconfig.AppExternalConfigProvider;
import com.wanmi.sbc.setting.api.request.appexternalconfig.*;
import com.wanmi.sbc.setting.api.response.appexternalconfig.*;

import jakarta.validation.Valid;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @author huangzhao
 */
@Tag(name =  "AppExternalConfig管理API", description =  "AppExternalConfigController")
@RestController
@Validated
@RequestMapping(value = "/app/external/config")
public class AppExternalConfigController {

    @Autowired
    private AppExternalConfigQueryProvider appExternalConfigQueryProvider;

    @Autowired
    private AppExternalConfigProvider appExternalConfigProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Operation(summary = "分页查询AppExternalConfig")
    @PostMapping("/page")
    public BaseResponse<AppExternalConfigPageResponse> getPage(@RequestBody @Valid AppExternalConfigPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return appExternalConfigQueryProvider.page(pageReq);
    }

    @Operation(summary = "新增AppExternalConfig")
    @PostMapping("/add")
    public BaseResponse<AppExternalConfigAddResponse> add(@RequestBody @Valid AppExternalConfigAddRequest addReq) {
        addReq.setCreatePerson(commonUtil.getOperatorId());
        BaseResponse<AppExternalConfigAddResponse> response = appExternalConfigProvider.add(addReq);
        operateLogMqUtil.convertAndSend("魔方",
                "新增小程序页面管理",
                "小程序id:" + response.getContext().getAppExternalConfigVO().getAppId());
        return response;
    }

    @Operation(summary = "根据id查询AppExternalConfig")
    @GetMapping("/{id}")
    public BaseResponse<AppExternalConfigByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AppExternalConfigByIdRequest idReq = new AppExternalConfigByIdRequest();
        idReq.setId(id);
        return appExternalConfigQueryProvider.getById(idReq);
    }



    @Operation(summary = "修改AppExternalConfig")
    @PutMapping("/modify")
    public BaseResponse<AppExternalConfigModifyResponse> modify(@RequestBody @Valid AppExternalConfigModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        BaseResponse<AppExternalConfigModifyResponse> response = appExternalConfigProvider.modify(modifyReq);
        operateLogMqUtil.convertAndSend("魔方",
                "修改小程序页面管理",
                "小程序id:" + response.getContext().getAppExternalConfigVO().getAppId());
        return response;
    }

    @Operation(summary = "根据id删除AppExternalConfig")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AppExternalConfigDelByIdRequest delByIdReq = new AppExternalConfigDelByIdRequest();
        delByIdReq.setId(id);
        appExternalConfigProvider.deleteById(delByIdReq);
        operateLogMqUtil.convertAndSend("魔方",
                "修改小程序页面管理",
                "小程序id:" + id);
        return BaseResponse.SUCCESSFUL();
    }

}
