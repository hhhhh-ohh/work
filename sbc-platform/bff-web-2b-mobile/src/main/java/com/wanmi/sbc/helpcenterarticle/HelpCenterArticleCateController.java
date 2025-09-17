package com.wanmi.sbc.helpcenterarticle;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlecate.HelpCenterArticleCateProvider;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlecate.HelpCenterArticleCateQueryProvider;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.*;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.*;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;


@Tag(name =  "帮助中心文章信息管理API", description =  "HelpCenterArticleCateController")
@RestController
@RequestMapping(value = "/helpCenterArticleCate")
public class HelpCenterArticleCateController {

    @Autowired
    private HelpCenterArticleCateQueryProvider helpCenterArticleCateQueryProvider;

    @Operation(summary = "分页查询帮助中心文章信息")
    @PostMapping("/page")
    public BaseResponse<HelpCenterArticleCatePageResponse> getPage(@RequestBody @Valid HelpCenterArticleCatePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("updateTime", "desc");
        return helpCenterArticleCateQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询帮助中心文章分类列表")
    @PostMapping("/getCateList")
    public BaseResponse<HelpCenterArticleCateListResponse> getList() {
        return helpCenterArticleCateQueryProvider.getCateList();
    }

}
