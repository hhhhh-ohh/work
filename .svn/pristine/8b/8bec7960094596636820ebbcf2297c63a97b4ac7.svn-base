package com.wanmi.sbc.goods.provider.impl.brand;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.goods.api.provider.brand.ContractBrandQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListRequest;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandListVerifyByStoreIdRequest;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandListResponse;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandListVerifyByStoreIdResponse;
import com.wanmi.sbc.goods.bean.dto.ContractBrandSaveDTO;
import com.wanmi.sbc.goods.bean.vo.ContractBrandVO;
import com.wanmi.sbc.goods.brand.entity.ContractBrandBase;
import com.wanmi.sbc.goods.brand.model.root.ContractBrand;
import com.wanmi.sbc.goods.brand.model.root.GoodsBrand;
import com.wanmi.sbc.goods.brand.request.ContractBrandQueryRequest;
import com.wanmi.sbc.goods.brand.request.ContractBrandSaveRequest;
import com.wanmi.sbc.goods.brand.request.GoodsBrandQueryRequest;
import com.wanmi.sbc.goods.brand.service.ContractBrandService;
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
public class ContractBrandQueryController implements ContractBrandQueryProvider {

    @Autowired
    private ContractBrandService contractBrandService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsBrandService goodsBrandService;

    /**
     * 条件查询签约品牌列表
     *
     * @param request 签约品牌查询数据结构 {@link ContractBrandListRequest}
     * @return 签约品牌列表 {@link ContractBrandListResponse}
     */
    @Override

    public BaseResponse<ContractBrandListResponse> list(@RequestBody @Valid ContractBrandListRequest request) {
        ContractBrandQueryRequest queryRequest = new ContractBrandQueryRequest();
        KsBeanUtil.copyPropertiesThird(request, queryRequest);
        List<ContractBrand> contractBrands = null;
        List<GoodsBrand> goodsBrands = null;
        StoreType storetype = null;
        if(request.getStoreId() != null){
            storetype = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(request.getStoreId()).build())
                    .getContext().getStoreVO().getStoreType();
        }
        if(storetype != StoreType.O2O){
            contractBrands = contractBrandService.queryList(queryRequest);
        }else {
            goodsBrands = goodsBrandService.query(new GoodsBrandQueryRequest());
            contractBrands = goodsBrands.stream().map(brand -> {
                ContractBrand contractBrand = new ContractBrand();
                contractBrand.setGoodsBrand(brand);
                contractBrand.setBrandId(brand.getBrandId());
                return contractBrand;
            }).collect(Collectors.toList());
        }
        return BaseResponse.success(
                ContractBrandListResponse.builder()
                        .contractBrandVOList(
                                KsBeanUtil.convert(contractBrands, ContractBrandVO.class))
                        .build());
    }

    /**
     * 根据店铺id校验自定义品牌是否与平台重复
     *
     * @param request 包含店铺id的校检数据结构 {@link ContractBrandListVerifyByStoreIdRequest}
     * @return 去重复后的签约品牌列表 {@link ContractBrandListVerifyByStoreIdResponse}
     */
    @Override
    public BaseResponse<ContractBrandListVerifyByStoreIdResponse> listVerifyByStoreId(@RequestBody @Valid
                                                                                       ContractBrandListVerifyByStoreIdRequest
                                                                                       request){
        return BaseResponse.success(
                ContractBrandListVerifyByStoreIdResponse.builder()
                        .contractBrandVOList(
                                KsBeanUtil.convert(contractBrandService.brandListVerify(request.getStoreId()), ContractBrandVO.class))
                        .build());
    }

    /***
     * 查询签约品牌是否存在，不存在则新增
     * @param request
     * @return
     */
    @Override
    public BaseResponse<ContractBrandVO> queryAndSaveContractBrand(ContractBrandSaveDTO request){
        return BaseResponse.success(KsBeanUtil
                .copyPropertiesThird(contractBrandService
                        .queryAndSaveContractBrand(KsBeanUtil.copyPropertiesThird(request,ContractBrandSaveRequest.class)),ContractBrandVO.class));
    }
}
