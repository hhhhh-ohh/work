package com.wanmi.sbc.helpcenterarticlecate;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlecate.HelpCenterArticleCateQueryProvider;
import com.wanmi.sbc.setting.api.provider.helpcenterarticlecate.HelpCenterArticleCateProvider;
import com.wanmi.sbc.setting.api.request.helpcenterarticlecate.*;
import com.wanmi.sbc.setting.api.response.helpcenterarticlecate.*;
import jakarta.validation.Valid;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import java.time.LocalDateTime;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name =  "帮助中心文章信息管理API", description =  "HelpCenterArticleCateController")
@RestController
@RequestMapping(value = "/helpCenterArticleCate")
public class HelpCenterArticleCateController {

    @Autowired
    private HelpCenterArticleCateQueryProvider helpCenterArticleCateQueryProvider;

    @Autowired
    private HelpCenterArticleCateProvider helpCenterArticleCateProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询帮助中心文章信息")
    @PostMapping("/page")
    public BaseResponse<HelpCenterArticleCatePageResponse> getPage(@RequestBody @Valid HelpCenterArticleCatePageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return helpCenterArticleCateQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询帮助中心文章信息")
    @PostMapping("/list")
    public BaseResponse<HelpCenterArticleCateListResponse> getList(@RequestBody @Valid HelpCenterArticleCateListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return helpCenterArticleCateQueryProvider.list(listReq);
    }

    @Operation(summary = "列表查询帮助中心文章信息")
    @PostMapping("/getCateList")
    public BaseResponse<HelpCenterArticleCateListResponse> getCateList() {
        return helpCenterArticleCateQueryProvider.getCateList();
    }

    @Operation(summary = "根据id查询帮助中心文章信息")
    @GetMapping("/{id}")
    public BaseResponse<HelpCenterArticleCateByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        HelpCenterArticleCateByIdRequest idReq = new HelpCenterArticleCateByIdRequest();
        idReq.setId(id);
        return helpCenterArticleCateQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增帮助中心文章信息")
    @PostMapping("/add")
    public BaseResponse<HelpCenterArticleCateAddResponse> add(@RequestBody @Valid HelpCenterArticleCateAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        return helpCenterArticleCateProvider.add(addReq);
    }

    @Operation(summary = "新增帮助中心文章信息")
    @PostMapping("/sort")
    public BaseResponse sort(@RequestBody @Valid HelpCenterArticleCateSortModifyRequest addReq) {
        return helpCenterArticleCateProvider.sort(addReq);
    }

    @Operation(summary = "修改帮助中心文章信息")
    @PutMapping("/modify")
    public BaseResponse<HelpCenterArticleCateModifyResponse> modify(@RequestBody @Valid HelpCenterArticleCateModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        return helpCenterArticleCateProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除帮助中心文章信息")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        HelpCenterArticleCateDelByIdRequest delByIdReq = new HelpCenterArticleCateDelByIdRequest();
        delByIdReq.setId(id);
        return helpCenterArticleCateProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除帮助中心文章信息")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid HelpCenterArticleCateDelByIdListRequest delByIdListReq) {
        return helpCenterArticleCateProvider.deleteByIdList(delByIdListReq);
    }


}
