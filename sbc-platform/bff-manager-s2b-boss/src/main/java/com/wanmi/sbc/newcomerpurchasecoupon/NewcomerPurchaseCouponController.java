package com.wanmi.sbc.newcomerpurchasecoupon;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponProvider;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.*;
import com.wanmi.sbc.marketing.api.response.newcomerpurchasecoupon.*;

import jakarta.validation.Valid;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name =  "新人购优惠券管理API", description =  "NewcomerPurchaseCouponController")
@RestController
@Validated
@RequestMapping(value = "/newcomerPurchaseCoupon")
public class NewcomerPurchaseCouponController {

    @Autowired
    private NewcomerPurchaseCouponQueryProvider newcomerPurchaseCouponQueryProvider;

    @Autowired
    private NewcomerPurchaseCouponProvider newcomerPurchaseCouponProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Operation(summary = "分页查询新人购优惠券")
    @PostMapping("/page")
    public BaseResponse<NewcomerPurchaseCouponPageResponse> getPage(@RequestBody @Valid NewcomerPurchaseCouponPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("id", "desc");
        return newcomerPurchaseCouponQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询新人购优惠券")
    @PostMapping("/list")
    public BaseResponse<NewcomerPurchaseCouponListResponse> getList(@RequestBody @Valid NewcomerPurchaseCouponListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        return newcomerPurchaseCouponQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询新人购优惠券")
    @GetMapping("/{id}")
    public BaseResponse<NewcomerPurchaseCouponByIdResponse> getById(@PathVariable Integer id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        NewcomerPurchaseCouponByIdRequest idReq = new NewcomerPurchaseCouponByIdRequest();
        idReq.setId(id);
        return newcomerPurchaseCouponQueryProvider.getById(idReq);
    }

    @Operation(summary = "新增新人购优惠券")
    @PostMapping("/add")
    public BaseResponse<NewcomerPurchaseCouponAddResponse> add(@RequestBody @Valid NewcomerPurchaseCouponAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return newcomerPurchaseCouponProvider.add(addReq);
    }

    @Operation(summary = "修改新人购优惠券")
    @PutMapping("/modify")
    public BaseResponse<NewcomerPurchaseCouponModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseCouponModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        return newcomerPurchaseCouponProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除新人购优惠券")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        NewcomerPurchaseCouponDelByIdRequest delByIdReq = new NewcomerPurchaseCouponDelByIdRequest();
        delByIdReq.setId(id);
        return newcomerPurchaseCouponProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除新人购优惠券")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseCouponDelByIdListRequest delByIdListReq) {
        return newcomerPurchaseCouponProvider.deleteByIdList(delByIdListReq);
    }

    @Operation(summary = "导出新人购优惠券列表")
//    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        NewcomerPurchaseCouponExportRequest listReq = JSON.parseObject(decrypted, NewcomerPurchaseCouponExportRequest.class);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(listReq));
//FIXME: 需要手动添加ReportType里面的枚举值, 替换掉下面的ReportType.XXXXXXX, 并放开注释, 实现方法的调用
//        exportDataRequest.setTypeCd(ReportType.XXXXXXX);
//        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "批量保存新人购优惠券")
    @PostMapping("/batchSave")
    @MultiSubmitWithToken
    @GlobalTransactional
    public BaseResponse batchSave(@RequestBody @Valid NewcomerPurchaseCouponBatchSaveRequest saveRequest) {
        List<NewcomerPurchaseCouponModifyRequest> newcomerPurchaseCouponModifyRequestList = saveRequest.getNewcomerPurchaseCouponModifyRequestList();
        Set<String> couponIds = newcomerPurchaseCouponModifyRequestList.parallelStream().map(NewcomerPurchaseCouponModifyRequest::getCouponId).collect(Collectors.toSet());
        if (newcomerPurchaseCouponModifyRequestList.size() != couponIds.size()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        return newcomerPurchaseCouponProvider.batchSave(saveRequest);
    }


}
