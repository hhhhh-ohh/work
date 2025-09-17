package com.wanmi.sbc.appexternalconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.appexternalconfig.AppExternalConfigQueryProvider;
import com.wanmi.sbc.setting.api.provider.appexternallink.AppExternalLinkProvider;
import com.wanmi.sbc.setting.api.provider.appexternallink.AppExternalLinkQueryProvider;
import com.wanmi.sbc.setting.api.request.appexternalconfig.AppExternalConfigByIdRequest;
import com.wanmi.sbc.setting.api.request.appexternallink.*;
import com.wanmi.sbc.setting.api.response.appexternallink.AppExternalConfigAndLinkByIdResponse;
import com.wanmi.sbc.setting.bean.vo.AppExternalConfigVO;
import com.wanmi.sbc.setting.bean.vo.AppExternalLinkVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Objects;


/**
 * @author huangzhao
 */
@Tag(description= "AppExternalLink管理API", name = "AppExternalLinkController")
@RestController
@Validated
@RequestMapping(value = "/app/external/link")
public class AppExternalLinkController {

    @Autowired
    private AppExternalLinkQueryProvider appExternalLinkQueryProvider;

    @Autowired
    private AppExternalLinkProvider appExternalLinkProvider;

    @Autowired
    private AppExternalConfigQueryProvider appExternalConfigQueryProvider;

    @Operation(summary = "根据id查询AppExternalLink")
    @GetMapping("/{id}")
    public BaseResponse<AppExternalConfigAndLinkByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        AppExternalConfigAndLinkByIdResponse appExternalLinkByIdResponse = new AppExternalConfigAndLinkByIdResponse();

        AppExternalLinkByIdRequest idReq = new AppExternalLinkByIdRequest();
        idReq.setId(id);
        AppExternalLinkVO appExternalLinkVO = appExternalLinkQueryProvider.getById(idReq)
                .getContext().getAppExternalLinkVO();
        appExternalLinkByIdResponse.setAppExternalLinkVO(appExternalLinkVO);

        //根据configid 查询小程序信息
        if(!Objects.isNull(appExternalLinkVO)){
            AppExternalConfigByIdRequest appExternalConfigByIdRequest = new AppExternalConfigByIdRequest();
            appExternalConfigByIdRequest.setId(appExternalLinkVO.getConfigId());
            AppExternalConfigVO appExternalConfigVO = appExternalConfigQueryProvider.getById(appExternalConfigByIdRequest)
                    .getContext().getAppExternalConfigVO();
            if(!Objects.isNull(appExternalConfigVO)){
                appExternalConfigVO.setAppExternalLinks(null);
            }
            appExternalLinkByIdResponse.setAppExternalConfigVO(appExternalConfigVO);
        }

        return BaseResponse.success(appExternalLinkByIdResponse);
    }
}
