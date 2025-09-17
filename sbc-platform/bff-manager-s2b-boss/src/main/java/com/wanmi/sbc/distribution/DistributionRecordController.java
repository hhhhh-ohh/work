package com.wanmi.sbc.distribution;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoForDistributionRecordRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailListByPageFordrRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerDetailListByPageResponse;
import com.wanmi.sbc.customer.api.response.store.StoreListForDistributionResponse;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.distributionrecord.EsDistributionRecordQueryProvider;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionrecord.EsDistributionRecordPageResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoPageRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoPageResponse;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author : baijz
 * @Date : 2019/2/25 10 46
 * @Description : 分销记录控制器
 */
@Tag(name = "DistributionRecordController", description = "分销记录控制器")
@Slf4j
@RestController
@Validated
@RequestMapping("/distribution/record")
public class DistributionRecordController {

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private EsDistributionRecordQueryProvider esDistributionRecordQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;


    /**
     * 分页查询分销记录信息
     * @param distributionRecordPageRequest
     * @return
     */
    @Schema(description = "分页查询分销记录信息")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<EsDistributionRecordPageResponse> page(@RequestBody @Valid EsDistributionRecordPageRequest distributionRecordPageRequest){
        BaseResponse<EsDistributionRecordPageResponse> page = esDistributionRecordQueryProvider.page(distributionRecordPageRequest);
        List<String> distributorCustomerIds = page.getContext().getDistributionRecordVOPage().getContent()
                .stream()
                .map(v->v.getDistributionCustomerVO().getCustomerId())
                .collect(Collectors.toList());
        Map<String, LogOutStatus> distributorMap = customerCacheService.getLogOutStatus(distributorCustomerIds);
        page.getContext().getDistributionRecordVOPage().getContent()
                .forEach(v->v.setDistributorLogOutStatus(distributorMap.get(v.getDistributionCustomerVO().getCustomerId())));

        List<String> customerCustomerIds = page.getContext().getDistributionRecordVOPage().getContent()
                .stream()
                .filter(v-> Objects.nonNull(v.getCustomerDetailVO()))
                .map(v->v.getCustomerDetailVO().getCustomerId())
                .collect(Collectors.toList());
        Map<String, LogOutStatus> customerMap = customerCacheService.getLogOutStatus(customerCustomerIds);
        page.getContext().getDistributionRecordVOPage().getContent().stream()
                .filter(v-> Objects.nonNull(v.getCustomerDetailVO()))
                .forEach(v->v.setCustomerLogOutStatus(customerMap.get(v.getCustomerDetailVO().getCustomerId())));
        return page;
    }

    /**
     * 根据店铺名称模糊查询名称
     * @return
     */
    @Schema(description = "根据名称模糊查询店铺信息")
    @RequestMapping(value = "/listStore" ,method = RequestMethod.POST)
    public BaseResponse<StoreListForDistributionResponse> listStoreInfo(@RequestBody @Valid ListStoreByNameRequest request){
        return storeQueryProvider.listByStoreName(request);
    }

    /**
     * 根据company编号模糊查询company信息
     * @param recordRequest
     * @return
     */
    @Schema(description = "根据编码模糊查询company信息")
    @RequestMapping(value = "/listCompany" ,method = RequestMethod.POST)
    public BaseResponse<StoreListForDistributionResponse> listCompanyInfo
            (@RequestBody @Valid CompanyInfoForDistributionRecordRequest recordRequest){
        return companyInfoQueryProvider.queryByCompanyCode(recordRequest);

    }


    /**
     *  模糊查询会员信息
     * @param recordRequest
     * @return
     */
    @Schema(description = "根据会员的名称或账号模糊查询会员信息")
    @RequestMapping(value = "/listCustomer" ,method = RequestMethod.POST)
    public BaseResponse<CustomerDetailListByPageResponse> pageCustomerInfo(@RequestBody @Valid
                                                                                   CustomerDetailListByPageFordrRequest recordRequest){
        return customerQueryProvider.listCustomerDetailForDistributionRecord(recordRequest);
    }


    /**
     * 根据商品的名称和编码模糊查询商品信息
     * @param request
     * @return
     */
    @Schema(description = "根据商品的名称和编码模糊查询商品信息")
    @RequestMapping(value = "/listGoodsInfo" ,method = RequestMethod.POST)
    public BaseResponse<GoodsInfoPageResponse> queryGoodsInfoListByNameOrNo(@RequestBody @Valid GoodsInfoPageRequest
                                                                                    request){
        return goodsInfoQueryProvider.page(request);
    }
}
