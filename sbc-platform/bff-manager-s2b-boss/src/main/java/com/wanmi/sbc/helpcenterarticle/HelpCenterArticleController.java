package com.wanmi.sbc.helpcenterarticle;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.setting.api.provider.helpcenterarticle.HelpCenterArticleQueryProvider;
import com.wanmi.sbc.setting.api.provider.helpcenterarticle.HelpCenterArticleProvider;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.*;
import com.wanmi.sbc.setting.api.response.helpcenterarticle.*;
import jakarta.validation.Valid;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import java.time.LocalDateTime;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name =  "帮助中心文章信息管理API", description =  "HelpCenterArticleController")
@RestController
@RequestMapping(value = "/helpCenterArticle")
public class HelpCenterArticleController {

    @Autowired
    private HelpCenterArticleQueryProvider helpCenterArticleQueryProvider;

    @Autowired
    private HelpCenterArticleProvider helpCenterArticleProvider;

    @Autowired
    private CommonUtil commonUtil;

    @ReturnSensitiveWords(functionName = "f_help_center_article_list_sign_word")
    @Operation(summary = "分页查询帮助中心文章信息")
    @PostMapping("/page")
    public BaseResponse<HelpCenterArticlePageResponse> getPage(@RequestBody @Valid HelpCenterArticlePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
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
        return helpCenterArticleQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增帮助中心文章信息")
    @PostMapping("/add")
    public BaseResponse<HelpCenterArticleAddResponse> add(@RequestBody @Valid HelpCenterArticleAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        addReq.setUpdateTime(LocalDateTime.now());
        addReq.setSolveNum(0L);
        addReq.setUnresolvedNum(0L);
        addReq.setViewNum(0L);
        return helpCenterArticleProvider.add(addReq);
    }

    @Operation(summary = "修改帮助中心文章信息")
    @PutMapping("/modify")
    public BaseResponse<HelpCenterArticleModifyResponse> modify(@RequestBody @Valid HelpCenterArticleModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return helpCenterArticleProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除帮助中心文章信息")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        HelpCenterArticleDelByIdRequest delByIdReq = new HelpCenterArticleDelByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setUpdatePerson(commonUtil.getOperatorId());
        return helpCenterArticleProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除帮助中心文章信息")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleDelByIdListRequest delByIdListReq) {
        return helpCenterArticleProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "根据id展示帮助中心文章信息")
    @GetMapping("/showArticleById/{id}")
    public BaseResponse showArticleById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        HelpCenterArticleChangeTypeByIdRequest idReq = new HelpCenterArticleChangeTypeByIdRequest();
        idReq.setId(id);
        idReq.setArticleType(DefaultFlag.NO);
        return helpCenterArticleQueryProvider.changeTypeById(idReq);
    }

    @Operation(summary = "根据id隐藏帮助中心文章信息")
    @GetMapping("/hideArticleById/{id}")
    public BaseResponse hideArticleById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        HelpCenterArticleChangeTypeByIdRequest idReq = new HelpCenterArticleChangeTypeByIdRequest();
        idReq.setId(id);
        idReq.setArticleType(DefaultFlag.YES);
        return helpCenterArticleQueryProvider.changeTypeById(idReq);
    }

}
