package com.wanmi.sbc.goods.api.provider.brand;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListVerifyByStoreIdRequest;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandListResponse;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandListVerifyByStoreIdResponse;
import com.wanmi.sbc.goods.bean.dto.ContractBrandSaveDTO;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对签约品牌查询接口</p>
 * Created by daiyitian on 2018-10-31-下午6:23.
 */
@FeignClient(value = "${application.goods.name}", contextId = "ContractBrandQueryProvider")
public interface ContractBrandQueryProvider {

    /**
     * 条件查询签约品牌列表
     *
     * @param request 签约品牌查询数据结构 {@link ContractBrandListRequest}
     * @return 签约品牌列表 {@link ContractBrandListResponse}
     */
    @PostMapping("/goods/${application.goods.version}/brand/contract/list")
    BaseResponse<ContractBrandListResponse> list(@RequestBody @Valid ContractBrandListRequest request);

    /**
     * 根据店铺id校验自定义品牌是否与平台重复
     *
     * @param request 包含店铺id的校检数据结构 {@link ContractBrandListVerifyByStoreIdRequest}
     * @return 去重复后的签约品牌列表 {@link ContractBrandListVerifyByStoreIdResponse}
     */
    @PostMapping("/goods/${application.goods.version}/brand/contract/list-verify-by-store-id")
    BaseResponse<ContractBrandListVerifyByStoreIdResponse> listVerifyByStoreId(@RequestBody @Valid
                                                                        ContractBrandListVerifyByStoreIdRequest
                                                                        request);
    /***
     * 查询签约品牌是否存在，不存在则新增
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/brand/contract/query-and-save-contract-brand")
    BaseResponse<ContractBrandVO> queryAndSaveContractBrand(@RequestBody ContractBrandSaveDTO request);
}
