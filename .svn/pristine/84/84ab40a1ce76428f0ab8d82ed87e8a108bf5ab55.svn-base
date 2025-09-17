package com.wanmi.sbc.helpcenterarticle;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.provider.helpcenterarticle.HelpCenterArticleProvider;
import com.wanmi.sbc.setting.api.provider.helpcenterarticle.HelpCenterArticleQueryProvider;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlerecord.HelpCenterArticleRecordProvider;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlerecord.HelpCenterArticleRecordQueryProvider;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.*;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordListRequest;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.*;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleRecordVO;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleVO;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Tag(name =  "帮助中心文章信息管理API", description =  "HelpCenterArticleController")
@RestController
@RequestMapping(value = "/helpCenterArticle")
public class HelpCenterArticleController {

    @Autowired
    private HelpCenterArticleQueryProvider helpCenterArticleQueryProvider;

    @Autowired
    private HelpCenterArticleProvider helpCenterArticleProvider;

    @Autowired
    private HelpCenterArticleRecordQueryProvider helpCenterArticleRecordQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    private static final Long DAY_HOURS = 24L;

    @Operation(summary = "分页查询帮助中心文章信息")
    @PostMapping("/page")
    public BaseResponse<HelpCenterArticlePageResponse> getPage(@RequestBody @Valid HelpCenterArticlePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("updateTime", "desc");
        pageReq.setArticleType(0);
        return helpCenterArticleQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询帮助中心文章信息")
    @PostMapping("/list")
    public BaseResponse<HelpCenterArticleListResponse> getList(@RequestBody @Valid HelpCenterArticleListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return helpCenterArticleQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询帮助中心文章信息")
    @GetMapping("/{id}")
    public BaseResponse<HelpCenterArticleByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        HelpCenterArticleByIdRequest idReq = new HelpCenterArticleByIdRequest();
        idReq.setId(id);
        HelpCenterArticleVO helpCenterArticleVO = helpCenterArticleQueryProvider.getById(idReq).getContext().getHelpCenterArticleVO();
        helpCenterArticleVO.setClickSolveType(DefaultFlag.YES);
        //增加查看次数
        helpCenterArticleProvider.addViewNum(idReq);
        List<HelpCenterArticleRecordVO> helpCenterArticleRecordVOList = helpCenterArticleRecordQueryProvider.list(HelpCenterArticleRecordListRequest.builder()
                .customerId(commonUtil.getOperatorId()).articleId(id).build()).getContext().getHelpCenterArticleRecordVOList();
        helpCenterArticleRecordVOList.forEach(helpCenterArticleRecordVO -> {
            Duration duration = Duration.between(helpCenterArticleRecordVO.getSolveTime(),LocalDateTime.now());
            long hours = duration.toHours();
            if(hours < DAY_HOURS){
                helpCenterArticleVO.setClickSolveType(DefaultFlag.NO);
            }
        });
        return BaseResponse.success(HelpCenterArticleByIdResponse.builder().helpCenterArticleVO(helpCenterArticleVO).build());
    }

    @Operation(summary = "点击文章解决状态")
    @GetMapping("/clickSolve/{id}")
    public BaseResponse clickSolve(@PathVariable Long id){
        return helpCenterArticleProvider.clickSolve(HelpCenterArticleChangeSolveTypeRequest.builder()
                .id(id).customerId(commonUtil.getOperatorId()).solveType(DefaultFlag.YES).build());
    }

    @Operation(summary = "点击文章未解决状态")
    @GetMapping("/clickUnresolved/{id}")
    public BaseResponse clickUnresolved(@PathVariable Long id){
        return helpCenterArticleProvider.clickUnresolved(HelpCenterArticleChangeSolveTypeRequest.builder()
                .id(id).customerId(commonUtil.getOperatorId()).solveType(DefaultFlag.NO).build());
    }

}
