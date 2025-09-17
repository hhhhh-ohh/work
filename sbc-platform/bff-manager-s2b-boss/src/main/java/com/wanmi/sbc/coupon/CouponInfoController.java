package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.coupon.service.CouponInfoService;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByNameResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoProvider;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoQueryProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoAddRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoPageRequest;
import com.wanmi.sbc.elastic.api.response.coupon.EsCouponInfoPageResponse;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponInfoDTO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponInfoVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoAddRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponInfoAddResponse;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.ArrayList;


@Tag(name =  "优惠券信息API", description =  "CouponInfoController")
@RestController
@Validated
@RequestMapping("/coupon-info")
public class CouponInfoController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CouponInfoProvider couponInfoProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCouponInfoProvider esCouponInfoProvider;

    @Autowired
    private EsCouponInfoQueryProvider esCouponInfoQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private CouponInfoService couponInfoService;

    /**
     * 获取优惠券列表
     *
     * @param couponInfoQueryRequest
     * @return
     */
    @Operation(summary = "获取优惠券列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<EsCouponInfoPageResponse> page(@RequestBody EsCouponInfoPageRequest couponInfoQueryRequest) {
        couponInfoQueryRequest.setPlatformFlag(DefaultFlag.YES);
        couponInfoQueryRequest.setDelFlag(DeleteFlag.NO);
        couponInfoQueryRequest.setStoreId(Constant.BOSS_DEFAULT_STORE_ID);
        couponInfoQueryRequest.putSort("createTime", SortType.DESC.toValue());
        couponInfoService.populateRequest(couponInfoQueryRequest);
        return esCouponInfoQueryProvider.page(couponInfoQueryRequest);
    }

    /**
     * 新增优惠券
     *
     * @param request
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "新增优惠券")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse add(@Valid @RequestBody CouponInfoAddRequest request) {
        request.setPlatformFlag(DefaultFlag.YES);
        request.setStoreId(Constant.BOSS_DEFAULT_STORE_ID);
        request.setCreatePerson(commonUtil.getOperatorId());
        ScopeType scopeType = request.getScopeType();
        if (CouponMarketingType.REDUCTION_COUPON != request.getCouponMarketingType()) {
            // 平台目前仅支持满减券，后续支持满折和运费，可放开此校验
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (ScopeType.SKU.equals(scopeType) && CollectionUtils.isEmpty(request.getScopeIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //记录操作日志
        operateLogMQUtil.convertAndSend("营销", "创建优惠券", "优惠券名称：" + request.getCouponName());
        BaseResponse<CouponInfoAddResponse> baseResponse = couponInfoProvider.add(request);
        CouponInfoVO couponInfoVO = baseResponse.getContext().getCouponInfoVO();
        EsCouponInfoDTO esCouponInfoDTO = KsBeanUtil.convert(couponInfoVO, EsCouponInfoDTO.class);
        esCouponInfoProvider.add(new EsCouponInfoAddRequest(esCouponInfoDTO));
        return baseResponse;
    }

    /**
     * 获取商家端优惠券列表
     *
     * @param couponInfoQueryRequest
     * @return
     */
    @Operation(summary = "获取商家端优惠券列表")
    @RequestMapping(value = "/supplier/page", method = RequestMethod.POST)
    public BaseResponse<EsCouponInfoPageResponse> supplierPage(@RequestBody EsCouponInfoPageRequest couponInfoQueryRequest) {
        couponInfoQueryRequest.setPlatformFlag(DefaultFlag.NO);
        couponInfoQueryRequest.setDelFlag(DeleteFlag.NO);
        couponInfoQueryRequest.setSupplierFlag(DefaultFlag.YES);
        couponInfoQueryRequest.putSort("createTime", SortType.DESC.toValue());
        BaseResponse<EsCouponInfoPageResponse> page;
        if (StringUtils.isNotBlank(couponInfoQueryRequest.getStoreName())) {
            ListStoreByNameResponse response = storeQueryProvider
                    .listByName(ListStoreByNameRequest
                            .builder()
                            .storeName(couponInfoQueryRequest.getStoreName())
                            .storeType(StoreType.SUPPLIER)
                            .build())
                    .getContext();
            if (CollectionUtils.isEmpty(response.getStoreVOList())) {
                return BaseResponse.success(new EsCouponInfoPageResponse(
                        new MicroServicePage<>(Collections.emptyList(), couponInfoQueryRequest.getPageable(), 0)));
            }
            couponInfoQueryRequest
                    .setStoreIds(response.getStoreVOList()
                            .stream()
                            .map(StoreVO::getStoreId)
                            .collect(Collectors.toList()));
        }

        //只查询商家券，增加商家券的过滤条件，过滤掉门店券
        List<CouponType> couponTypeList = new ArrayList<>();
        couponTypeList.add(CouponType.STORE_VOUCHERS);
        couponInfoQueryRequest.setCouponTypes(couponTypeList);

        page = esCouponInfoQueryProvider.page(couponInfoQueryRequest);
        List<Long> storeIds = page.getContext().getCouponInfos().getContent().stream()
                .map(EsCouponInfoVO::getStoreId)
                .collect(Collectors.toList());
        List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build())
                .getContext()
                .getStoreVOList();
        if (CollectionUtils.isNotEmpty(storeIds)) {
            page.getContext().getCouponInfos().getContent().forEach(info -> {
                StoreVO vo = storeVOList.stream()
                        .filter(storeVO -> Objects.equals(storeVO.getStoreId(), info.getStoreId()))
                        .findFirst()
                        .orElse(null);
                if (Objects.nonNull(vo)) {
                    info.setStoreName(vo.getStoreName());
                }
            });
        }

        return page;
    }
}
