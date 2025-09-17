package com.wanmi.sbc.marketing;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelListByCustomerLevelNameRequest;
import com.wanmi.sbc.customer.bean.dto.MarketingCustomerLevelDTO;
import com.wanmi.sbc.customer.bean.vo.MarketingCustomerLevelVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.provider.fullreturn.FullReturnProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoQueryRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnAddRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnModifyRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnPageRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingPageRequest;
import com.wanmi.sbc.marketing.api.response.market.MarketingPageResponse;
import com.wanmi.sbc.marketing.bean.dto.FullReturnDetailDTO;
import com.wanmi.sbc.marketing.bean.dto.FullReturnLevelDTO;
import com.wanmi.sbc.marketing.bean.dto.MarketingPageDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPageVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 满返
 */
@Tag(name = "MarketingFullReturnController", description = "满返服务API")
@RestController
@Validated
@RequestMapping("/marketing/fullReturn")
public class MarketingFullReturnController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private FullReturnProvider fullReturnProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private MarketingBaseService marketingBaseService;

    @Autowired
    private MarketingProvider marketingProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    /**
     * 新增满返营销信息
     * @param request
     * @return
     */
    @Operation(summary = "新增满返营销信息")
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public BaseResponse add(@Valid @RequestBody FullReturnAddRequest request) {
        Platform platform = commonUtil.getOperator().getPlatform();
        //商家
        if (platform == Platform.SUPPLIER){
            request.setIsBoss(BoolFlag.NO);
            request.setStoreId(commonUtil.getStoreId());
            //校验优惠券是否越权
            checkCoupons(request.getFullReturnLevelList());
            if (Objects.equals(MarketingScopeType.SCOPE_TYPE_CUSTOM,request.getScopeType())){
                //如果适用范围为自定义商品，校验商品越权
                checkGoods(request.getScopeIds());
            }
        }else if (platform == Platform.PLATFORM){
            request.setIsBoss(BoolFlag.YES);
            request.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        }else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.valid();
        request.setCreatePerson(commonUtil.getOperatorId());
        fullReturnProvider.add(request);

        operateLogMQUtil.convertAndSend("营销","创建满返活动","创建满返活动："+request.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验商品归属店铺
     * @param scopeIds
     */
    private void checkGoods(List<String> scopeIds) {
        if (CollectionUtils.isEmpty(scopeIds)){
            return;
        }

        List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider
                .listByCondition(GoodsInfoListByConditionRequest.builder().goodsInfoIds(scopeIds).build())
                .getContext()
                .getGoodsInfos();

        List<Long> storeIds = goodsInfos.stream()
                .map(GoodsInfoVO::getStoreId)
                .distinct()
                .collect(Collectors.toList());

        if (storeIds.size() != Constants.ONE || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
    }

    /**
     * 校验优惠券归属店铺
     * @param fullReturnLevelList
     */
    private void checkCoupons(List<FullReturnLevelDTO> fullReturnLevelList) {
        if (CollectionUtils.isEmpty(fullReturnLevelList)){
            return;
        }
        List<String> couponIds = fullReturnLevelList
                .stream()
                .map(FullReturnLevelDTO::getFullReturnDetailList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream()
                .map(FullReturnDetailDTO::getCouponId)
                .collect(Collectors.toList());

        List<CouponInfoVO> couponCodeList = couponInfoQueryProvider
                .queryCouponInfos(CouponInfoQueryRequest.builder()
                        .couponIds(couponIds)
                        .build())
                .getContext()
                .getCouponCodeList();

        List<Long> storeIds = couponCodeList
                .stream()
                .map(CouponInfoVO::getStoreId)
                .distinct()
                .collect(Collectors.toList());

        if (storeIds.size() != Constants.ONE || !Objects.equals(commonUtil.getStoreId(),storeIds.get(0))){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
    }

    /**
     * 满返营销列表
     * @param request
     * @return
     */
    @Operation(summary = "满返营销列表")
    @RequestMapping(value = "/page" , method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<MarketingPageVO>> page(@Valid @RequestBody FullReturnPageRequest request) {
        MarketingPageRequest marketingPageRequest = new MarketingPageRequest();
        MarketingPageDTO pageDTO = KsBeanUtil.convert(request, MarketingPageDTO.class);
        pageDTO.setDelFlag(DeleteFlag.NO);
        pageDTO.setMarketingType(MarketingType.RETURN);
        pageDTO.setSortColumn("createTime");
        pageDTO.setSortRole("desc");
        if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
            marketingPageRequest.setStoreId(commonUtil.getStoreId());
        }else {
            pageDTO.setIsBoss(BoolFlag.YES);
        }
        marketingPageRequest.setMarketingPageDTO(pageDTO);
        BaseResponse<MarketingPageResponse> page = marketingQueryProvider.page(marketingPageRequest);

        List<MarketingPageVO> content = page.getContext().getMarketingVOS().getContent();

        //查询客户等级+店铺名称
        List<MarketingCustomerLevelDTO> customerLevelDTOList = content.stream().map(m -> {
            MarketingCustomerLevelDTO dto = new MarketingCustomerLevelDTO();
            dto.setMarketingId(m.getMarketingId());
            dto.setStoreId(m.getStoreId());
            dto.setJoinLevel(m.getJoinLevel());
            return dto;
        }).collect(Collectors.toList());
        List<MarketingCustomerLevelVO> marketingCustomerLevelVOList = customerLevelQueryProvider.
                listByCustomerLevelName(new CustomerLevelListByCustomerLevelNameRequest(customerLevelDTOList)).
                getContext().getCustomerLevelVOList();
        Map<Long, MarketingCustomerLevelVO> levelVOMap = marketingCustomerLevelVOList.stream().
                collect(Collectors.toMap(MarketingCustomerLevelVO::getMarketingId, Function.identity()));
        content.forEach(vo -> {
            MarketingCustomerLevelVO levelVO = levelVOMap.get(vo.getMarketingId());
            vo.setLevelName(levelVO.getLevelName());
            vo.setStoreName(levelVO.getStoreName());
        });
        return BaseResponse.success(page.getContext().getMarketingVOS());
    }

    /**
     * 编辑满返营销信息
     * @param request
     * @return
     */
    @Operation(summary = "编辑满返营销信息")
    @RequestMapping(value = "/modify" , method = RequestMethod.POST)
    public BaseResponse modify(@Valid @RequestBody FullReturnModifyRequest request) {
        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
        marketingGetByIdRequest.setMarketingId(request.getMarketingId());
        MarketingVO marketing = marketingQueryProvider.getById(marketingGetByIdRequest).getContext().getMarketingVO();
        if(marketing != null){
            marketingBaseService.checkPlatForm(marketing);
            if (Objects.equals(BoolFlag.NO,marketing.getIsBoss())){
                checkCoupons(request.getFullReturnLevelList());
            }
        }else{
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080001);
        }

        request.valid();
        request.setUpdatePerson(commonUtil.getOperatorId());
        fullReturnProvider.modify(request);

        operateLogMQUtil.convertAndSend("营销","修改满返活动","修改满返活动："+request.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }
}
