package com.wanmi.sbc.elastic.api.provider.storeInformation;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyAccountQueryRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.EsCompanyPageRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoContractRequest;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoQueryPageRequest;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsCompanyAccountResponse;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsCompanyInfoResponse;
import com.wanmi.sbc.elastic.api.response.storeInformation.EsListStoreByNameForAutoCompleteResponse;
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
@FeignClient(value = "${application.elastic.name}", contextId = "EsStoreInformationQueryProvider")
public interface EsStoreInformationQueryProvider {

    /**
     * es商家结算账户分页
     *
     * @param request 搜索请求 {@link EsCompanyAccountQueryRequest}
     * @return 分页列表 {@link EsCompanyAccountResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/company-account-page")
    BaseResponse<EsCompanyAccountResponse> companyAccountPage(@RequestBody @Valid EsCompanyAccountQueryRequest request);

    /**
     * 查询es商家列表分页
     *
     * @param request 搜索请求 {@link EsCompanyPageRequest}
     * @return 分页列表 {@link EsCompanyInfoResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/company-info-page")
    BaseResponse<EsCompanyInfoResponse> companyInfoPage(@RequestBody @Valid EsCompanyPageRequest request);

    /**
     * 根据店铺名称自动填充店铺名称
     *
     * @param request 店铺信息 {@link StoreInfoContractRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/es-list-by-name-for-auto-complete")
    BaseResponse<EsListStoreByNameForAutoCompleteResponse> queryStoreByNameAndStoreTypeForAutoComplete(
            @RequestBody @Valid StoreInfoQueryPageRequest request);

    /**
     * 根据店铺ids查询店铺
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/es-list-by-store-ids")
    BaseResponse<EsListStoreByNameForAutoCompleteResponse> queryStoreByStoreIds(
            @RequestBody StoreInfoQueryPageRequest storeInfoQueryPageRequest);
}
