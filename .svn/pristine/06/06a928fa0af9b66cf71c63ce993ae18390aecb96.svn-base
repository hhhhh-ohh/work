package com.wanmi.sbc.appexternalconfig;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.appexternalconfig.AppExternalConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.appexternallink.AppExternalLinkQueryProvider;
import com.wanmi.sbc.setting.api.provider.appexternallink.AppExternalLinkProvider;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternallink.*;
import com.wanmi.sbc.setting.api.response.appexternallink.*;

import jakarta.validation.Valid;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import java.util.Objects;

import com.wanmi.sbc.setting.bean.vo.AppExternalConfigVO;
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
@Tag(name =  "AppExternalLink管理API", description =  "AppExternalLinkController")
@RestController
@Validated
@RequestMapping(value = "/app/external/link")
public class AppExternalLinkController {

    @Autowired
    private AppExternalLinkQueryProvider appExternalLinkQueryProvider;

    @Autowired
    private AppExternalLinkProvider appExternalLinkProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private AppExternalConfigQueryProvider appExternalConfigQueryProvider;

    @Operation(summary = "列表查询AppExternalLink")
    @GetMapping("/list/{configId}")
    public BaseResponse<AppExternalLinkListResponse> getList(@PathVariable Long configId) {
        AppExternalLinkListRequest request = new AppExternalLinkListRequest();
        request.setConfigId(configId);
        request.setDelFlag(DeleteFlag.NO);
        return appExternalLinkQueryProvider.list(request);
    }

    @Operation(summary = "根据id查询AppExternalLink")
    @GetMapping("/{id}")
    public BaseResponse<AppExternalLinkByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AppExternalLinkByIdRequest idReq = new AppExternalLinkByIdRequest();
        idReq.setId(id);
        return appExternalLinkQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增AppExternalLink")
    @PostMapping("/add")
    public BaseResponse<AppExternalLinkAddResponse> add(@RequestBody @Valid AppExternalLinkAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        AppExternalConfigVO appExternalConfigVO = appExternalConfigQueryProvider
                .getById(AppExternalConfigByIdRequest.builder().id(addReq.getConfigId()).build())
                .getContext()
                .getAppExternalConfigVO();
        if (Objects.isNull(appExternalConfigVO)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BaseResponse<AppExternalLinkAddResponse> response = appExternalLinkProvider.add(addReq);
        operateLogMqUtil.convertAndSend("魔方",
                "新增小程序页面链接",
                "小程序链接Id:" + response.getContext().getAppExternalLinkVO().getId());
        return response;
    }

    @Operation(summary = "修改AppExternalLink")
    @PutMapping("/modify")
    public BaseResponse<AppExternalLinkModifyResponse> modify(@RequestBody @Valid AppExternalLinkModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        BaseResponse<AppExternalLinkModifyResponse> response = appExternalLinkProvider.modify(modifyReq);
        operateLogMqUtil.convertAndSend("魔方",
                "新增小程序页面链接",
                "小程序链接Id:" + response.getContext().getAppExternalLinkVO().getId());
        return response;
    }

    @Operation(summary = "根据id删除AppExternalLink")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        AppExternalLinkDelByIdRequest delByIdReq = new AppExternalLinkDelByIdRequest();
        delByIdReq.setId(id);
        appExternalLinkProvider.deleteById(delByIdReq);
        operateLogMqUtil.convertAndSend("魔方",
                "删除小程序页面链接",
                "小程序链接Id:" + id);
        return BaseResponse.SUCCESSFUL();
    }
}
