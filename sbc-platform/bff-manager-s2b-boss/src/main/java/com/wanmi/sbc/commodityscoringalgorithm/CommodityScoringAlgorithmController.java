package com.wanmi.sbc.commodityscoringalgorithm;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.crm.api.provider.autotag.AutoTagQueryProvider;
import com.wanmi.sbc.crm.api.request.autotag.AutoTagListRequest;
import com.wanmi.sbc.vas.api.provider.commodityscoringalgorithm.CommodityScoringAlgorithmProvider;
import com.wanmi.sbc.vas.api.provider.commodityscoringalgorithm.CommodityScoringAlgorithmQueryProvider;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmListRequest;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmModifyRequest;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmListResponse;
import com.wanmi.sbc.vas.api.response.commodityscoringalgorithm.CommodityScoringAlgorithmModifyResponse;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;


@Tag(name =  "商品评分算法管理API", description =  "CommodityScoringAlgorithmController")
@RestController
@Validated
@RequestMapping(value = "/commodity-scoringalg-orithm")
public class CommodityScoringAlgorithmController {

    @Autowired
    private CommodityScoringAlgorithmQueryProvider commodityScoringAlgorithmQueryProvider;

    @Autowired
    private CommodityScoringAlgorithmProvider commodityScoringAlgorithmProvider;

    @Autowired
    private AutoTagQueryProvider autoTagQueryProvider;

    @Operation(summary = "列表查询商品评分算法")
    @PostMapping("/list")
    public BaseResponse<CommodityScoringAlgorithmListResponse> getList() {
        CommodityScoringAlgorithmListRequest listReq =
                CommodityScoringAlgorithmListRequest.builder().delFlag(DeleteFlag.NO).build();
        listReq.putSort("id", "desc");
        CommodityScoringAlgorithmListResponse response =
                commodityScoringAlgorithmQueryProvider.list(listReq).getContext();
        autoTagQueryProvider.getPreferenceList(AutoTagListRequest.builder().delFlag(DeleteFlag.NO).systemFlag(Boolean.TRUE).build()).getContext().getAutoTagVOList().forEach(autoTag -> {
            if (autoTag.getSystemTagId() == Constants.TWO) {
                response.setCateTagId(autoTag.getId());
            } else if (autoTag.getSystemTagId() == Constants.THREE) {
                response.setTopCateTagId(autoTag.getId());
            } else if (autoTag.getSystemTagId() == Constants.FOUR) {
                response.setBrandTagId(autoTag.getId());
            } else if (autoTag.getSystemTagId() == Constants.SIX) {
                response.setStoreTagId(autoTag.getId());
            }
        });
        return BaseResponse.success(response);
    }

    @Operation(summary = "修改商品评分算法")
    @PutMapping("/modify")
    public BaseResponse<CommodityScoringAlgorithmModifyResponse> modify(@RequestBody @Valid List<CommodityScoringAlgorithmModifyRequest> modifyReq) {
        return commodityScoringAlgorithmProvider.modify(modifyReq);
    }
}
