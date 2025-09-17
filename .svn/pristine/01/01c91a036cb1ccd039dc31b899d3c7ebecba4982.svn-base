package com.wanmi.sbc.elastic.api.provider.storeInformation;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.storeInformation.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author yangzhen
 * @Description // 商家店铺信息
 * @Date 18:27 2020/12/7
 * @Param
 * @return
 **/
@FeignClient(value = "${application.elastic.name}", contextId = "StoreInformationProvider")
public interface EsStoreInformationProvider {

    /**
     * 初始化商家店铺
     *
     * @param request 商家店铺信息 {@link StoreInformationRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/init")
    BaseResponse initStoreInformation(@RequestBody @Valid StoreInformationRequest request);

    /**
     * 批量初始化商家店铺
     *
     * @param request 商家店铺信息列表 {@link StoreInfoQueryPageRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/init-store-info-list")
    BaseResponse initStoreInformationList(@RequestBody @Valid ESStoreInfoInitRequest request);

    /**
     * 修改es 店铺信息
     *
     * @param request 商家店铺信息 {@link StoreInfoModifyRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/modify/modify-store-basic-info")
    BaseResponse<Boolean> modifyStoreBasicInfo(@RequestBody @Valid StoreInfoModifyRequest request);

    /**
     * 修改es 店铺状态
     *
     * @param request 商家店铺状态信息 {@link StoreInfoStateModifyRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/modify/modify-store-audit-state")
    BaseResponse<Boolean> modifyStoreState(@RequestBody @Valid StoreInfoStateModifyRequest request);

    /**
     * 审核变更店铺信息
     *
     * @param request 审核变更商家店铺信息 {@link StoreInfoRejectModifyRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/modify/modify-store-reject")
    BaseResponse<Boolean> modifyStoreReject(@RequestBody @Valid StoreInfoRejectModifyRequest request);

    /**
     * 修改签约信息
     *
     * @param request 签约信息 {@link StoreInfoContractRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/modify/modify-store-contractInfo")
    BaseResponse<Boolean> modifyStoreContractInfo(@RequestBody @Valid StoreInfoContractRequest request);

    /**
     * 修改es 店铺二次签约状态
     *
     * @param request 商家店铺状态信息 {@link StoreInfoStateModifyRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/modify/modify-contract-audit-state")
    BaseResponse<Boolean> modifyContractAuditState(@RequestBody @Valid StoreInfoStateModifyRequest request);
}
