package com.wanmi.sbc.coupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.crm.api.provider.customerplan.CustomerPlanQueryProvider;
import com.wanmi.sbc.crm.api.request.customerplan.CustomerPlanListRequest;
import com.wanmi.sbc.crm.bean.vo.CustomerPlanVO;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoAddRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoDeleteByIdRequest;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponInfoDTO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.*;
import com.wanmi.sbc.marketing.api.response.coupon.*;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import com.wanmi.sbc.marketing.bean.vo.CouponGoodsVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.store.StoreBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "CouponInfoBaseController", description = "优惠券基本 Api")
@RestController
@Validated
@RequestMapping("/coupon-info")
public class CouponInfoBaseController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CouponInfoProvider couponInfoProvider;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private CustomerPlanQueryProvider customerPlanQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCouponInfoProvider esCouponInfoProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private StoreBaseService storeBaseService;

    @Autowired
    private GoodsBaseService goodsBaseService;

    /**
     * 删除优惠券
     *
     * @param couponId
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "删除优惠券")
    @Parameter(name = "couponId", description = "优惠券Id", required = true)
    @RequestMapping(value = "/{couponId}", method = RequestMethod.DELETE)
    public BaseResponse deleteMarketingId(@PathVariable("couponId") String couponId) {
        CouponInfoVO vo = couponInfoQueryProvider.getById(CouponInfoByIdRequest.builder().couponId(couponId).build())
                .getContext();
        if (Objects.nonNull(vo)) {
            //越权校验
            commonUtil.checkStoreId(vo.getStoreId());
            //验证是否购买CRM
            TradeConfigGetByTypeRequest typeRequest = new TradeConfigGetByTypeRequest();
            typeRequest.setConfigType(ConfigType.CRM_FLAG);
            Integer status = auditQueryProvider.getTradeConfigByType(typeRequest).getContext().getStatus();
            if (Constants.yes.equals(status)) {
                //判断计划名称是否已经存在
                List<CustomerPlanVO> customerPlanList = customerPlanQueryProvider.list(
                        CustomerPlanListRequest.builder().couponId(couponId).notEndStatus(Boolean.TRUE).delFlag(DeleteFlag.NO)
                                .build()).getContext().getCustomerPlanList();
                if (CollectionUtils.isNotEmpty(customerPlanList)) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080186);
                }
            }

            couponInfoProvider.deleteById(CouponInfoDeleteByIdRequest.builder().couponId(couponId)
                    .operatorId(commonUtil.getOperatorId()).build());

            esCouponInfoProvider.deleteById(new EsCouponInfoDeleteByIdRequest(couponId));
            //记录操作日志
            operateLogMQUtil.convertAndSend("营销", "删除优惠券", "优惠券名称：" + vo.getCouponName());
            return BaseResponse.SUCCESSFUL();
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
        }
    }

    /**
     * 修改优惠券
     *
     * @param request
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "修改优惠券")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse modify(@Valid @RequestBody CouponInfoModifyRequest request) {
        //越权校验
        CouponInfoByIdResponse response = couponInfoQueryProvider
                .getById(CouponInfoByIdRequest.builder().couponId(request.getCouponId()).build())
                .getContext();
        if (Objects.nonNull(response)){
            commonUtil.checkStoreId(response.getStoreId());
        }
        if (Platform.BOSS == commonUtil.getOperator().getPlatform()
                && CouponMarketingType.REDUCTION_COUPON != request.getCouponMarketingType()) {
            // 平台目前仅支持满减券，后续支持满折和运费，可放开此校验
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ScopeType scopeType = request.getScopeType();
        if (ScopeType.SKU.equals(scopeType) && CollectionUtils.isEmpty(request.getScopeIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.setUpdatePerson(commonUtil.getOperatorId());
        operateLogMQUtil.convertAndSend("营销", "编辑优惠券", "优惠券名称：" + request.getCouponName());
        BaseResponse<CouponInfoModifyResponse> baseResponse = couponInfoProvider.modify(request);
        CouponInfoVO couponInfoVO = baseResponse.getContext().getCouponInfoVO();
        EsCouponInfoDTO esCouponInfoDTO = KsBeanUtil.convert(couponInfoVO, EsCouponInfoDTO.class);
        esCouponInfoProvider.add(new EsCouponInfoAddRequest(esCouponInfoDTO));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据优惠券Id获取优惠券详细信息
     *
     * @param couponId
     * @return
     */
    @Operation(summary = "根据优惠券Id获取优惠券详细信息")
    @Parameter(name = "couponId", description = "优惠券Id", required = true)
    @RequestMapping(value = "/{couponId}", method = RequestMethod.GET)
    public BaseResponse<CouponInfoDetailByIdResponse> getCouponInfoById(@PathVariable("couponId") String couponId) {
        Long storeId;
        Platform platform = commonUtil.getOperator().getPlatform();
        if (Platform.PLATFORM == platform){
            storeId = Constants.BOSS_DEFAULT_STORE_ID;
        }else {
            storeId = commonUtil.getStoreId();
        }
        CouponInfoDetailByIdRequest couponInfoDetailByIdRequest = new CouponInfoDetailByIdRequest();
        couponInfoDetailByIdRequest.setCouponId(couponId);
        couponInfoDetailByIdRequest.setStoreId(storeId);
        BaseResponse<CouponInfoDetailByIdResponse> response = couponInfoQueryProvider
                .getDetailById(couponInfoDetailByIdRequest);
        //越权校验
        commonUtil.checkStoreId(response.getContext().getCouponInfo().getStoreId());

        //查询商家优惠券storeName
        if(response.getContext().getCouponInfo().getStoreId()>0){
            StoreVO storeVO = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder()
                            .storeId(response.getContext().getCouponInfo().getStoreId())
                            .build())
                    .getContext()
                    .getStoreVO();
            response.getContext().getCouponInfo().setStoreName(storeVO.getStoreName());
        }

        //填充供应商名称
        storeBaseService.populateProviderName(Optional.ofNullable(response.getContext().getGoodsList())
                .map(CouponGoodsVO::getGoodsInfoPage).map(MicroServicePage::getContent)
                .orElse(Collections.emptyList()));

        //填充营销商品状态
        goodsBaseService.populateMarketingGoodsStatus(Optional.ofNullable(response.getContext().getGoodsList())
                .map(CouponGoodsVO::getGoodsInfoPage).map(MicroServicePage::getContent)
                .orElse(Collections.emptyList()));
        return response;
    }

    /**
     * 复制优惠券
     *
     * @param couponId
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "复制优惠券")
    @Parameter(name = "couponId", description = "优惠券Id", required = true)
    @RequestMapping(value = "/copy/{couponId}", method = RequestMethod.GET)
    public BaseResponse copyCouponInfo(@PathVariable("couponId") String couponId) {
        CouponInfoVO vo = couponInfoQueryProvider.getById(CouponInfoByIdRequest.builder().couponId(couponId).build())
                .getContext();
        if (Objects.nonNull(vo)) {
            //越权校验
            commonUtil.checkStoreId(vo.getStoreId());
            //记录操作日志
            operateLogMQUtil.convertAndSend("营销", "复制优惠券", "优惠券名称：" + vo.getCouponName());
            BaseResponse<CouponInfoCopyByIdResponse> baseResponse = couponInfoProvider.copyById(CouponInfoCopyByIdRequest.builder().couponId(couponId)
                    .operatorId(commonUtil.getOperatorId()).build());
            CouponInfoVO couponInfoVO = baseResponse.getContext().getCouponInfoVO();
            EsCouponInfoDTO esCouponInfoDTO = KsBeanUtil.convert(couponInfoVO, EsCouponInfoDTO.class);
            esCouponInfoProvider.add(new EsCouponInfoAddRequest(esCouponInfoDTO));
            return BaseResponse.SUCCESSFUL();
        } else {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080046);
        }
    }

    /**
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoPageResponse>
     * @description 根据活动ID获取魔方优惠券列表(boss / supplier)
     * @author EDZ
     * @date 2021/6/11 11:25
     **/
    @Operation(summary = "根据活动ID获取魔方优惠券列表(boss/supplier)")
    @RequestMapping(value = "/magic/page", method = RequestMethod.POST)
    public BaseResponse<MagicCouponInfoPageResponse> page(@RequestBody MagicCouponInfoPageRequest request) {
        Long storeId = null;
        Platform platform = commonUtil.getOperator().getPlatform();
        switch (platform) {
            case PLATFORM:
                break;
            case SUPPLIER:
                storeId = commonUtil.getStoreId();
                break;
            default:
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        return couponInfoQueryProvider.magicCouponInfoPage(request, storeId);
    }

    /**
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.coupon.MagicCouponInfoPageResponse>
     * @description 魔方新人专享券列表(boss)
     * @author EDZ
     * @date 2022/8/25 17:25
     **/
    @Operation(summary = "魔方新人专享券列表(boss)")
    @RequestMapping(value = "/newcomer/magic/page", method = RequestMethod.POST)
    public BaseResponse<MagicCouponInfoPageResponse> newComerCouponPage(@RequestBody MagicCouponInfoPageRequest request) {
        Platform platform = commonUtil.getOperator().getPlatform();
        if (!Platform.PLATFORM.equals(platform)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        return couponInfoQueryProvider.magicNewcomerCouponInfoPage(request);
    }

}
