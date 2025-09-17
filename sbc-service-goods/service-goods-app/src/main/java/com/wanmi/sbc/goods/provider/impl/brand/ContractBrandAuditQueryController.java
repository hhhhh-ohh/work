package com.wanmi.sbc.goods.provider.impl.brand;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandAuditQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandAuditListRequest;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandAuditListResponse;
import com.wanmi.sbc.goods.bean.vo.ContractBrandAuditVO;
import com.wanmi.sbc.goods.brand.model.root.ContractBrandAudit;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.request.*;
import com.wanmi.sbc.goods.brand.service.ContractBrandAuditService;
import com.wanmi.sbc.goods.brand.service.GoodsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>对签约品牌查询接口</p>
 * Created by daiyitian on 2018-10-31-下午6:23.
 */
@RestController
@Validated
public class ContractBrandAuditQueryController implements ContractBrandAuditQueryProvider {

    @Autowired
    private ContractBrandAuditService contractBrandAuditService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsBrandService goodsBrandService;

    /**
     * 条件查询签约品牌列表
     *
     * @param request 签约品牌查询数据结构 {@link ContractBrandAuditListRequest}
     * @return 签约品牌列表 {@link ContractBrandAuditListResponse}
     */
    @Override
    public BaseResponse<ContractBrandAuditListResponse> list(@RequestBody @Valid ContractBrandAuditListRequest request) {
        ContractBrandAuditQueryRequest queryRequest = new ContractBrandAuditQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        List<ContractBrandAudit> contractBrands = null;
        List<GoodsBrand> goodsBrands = null;
        StoreType storetype = null;
        if(request.getStoreId() != null){
            storetype = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(request.getStoreId()).build())
                    .getContext().getStoreVO().getStoreType();
        }
        if(storetype != StoreType.O2O){
            contractBrands = contractBrandAuditService.queryList(queryRequest);
        }else {
            goodsBrands = goodsBrandService.query(new GoodsBrandQueryRequest());
            contractBrands = goodsBrands.stream().map(brand -> {
                ContractBrandAudit contractBrand = new ContractBrandAudit();
                contractBrand.setGoodsBrand(brand);
                contractBrand.setBrandId(brand.getBrandId());
                return contractBrand;
            }).collect(Collectors.toList());
        }
        return BaseResponse.success(
                ContractBrandAuditListResponse.builder()
                        .contractBrandVOList(
                                KsBeanUtil.convert(contractBrands, ContractBrandAuditVO.class))
                        .build());
    }
}
