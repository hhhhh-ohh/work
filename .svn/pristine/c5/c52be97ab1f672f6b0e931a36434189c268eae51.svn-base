package com.wanmi.sbc.marketing;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.storelevel.StoreLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelListRequest;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelListResponse;
import com.wanmi.sbc.customer.api.response.storelevel.StoreLevelListResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.StoreLevelVO;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateByIdsRequest;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.StoreCateVO;
import com.wanmi.sbc.marketing.api.provider.discount.MarketingFullDiscountProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingProvider;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullDiscountAddRequest;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullDiscountModifyRequest;
import com.wanmi.sbc.marketing.api.request.market.PauseModifyRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 满折
 */
@Tag(name = "MarketingFullDiscountController", description = "满折服务API")
@RestController
@Validated
@RequestMapping("/marketing/fullDiscount")
public class MarketingFullDiscountController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MarketingFullDiscountProvider marketingFullDiscountProvider;

    @Autowired
    private MarketingProvider marketingProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired private MarketingBaseService marketingBaseService;

    @Autowired
    private StoreLevelQueryProvider storeLevelQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    /**
     * 新增满折营销信息
     *
     * @param request
     * @return
     */
    @Operation(summary = "新增满折营销信息")
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse add(@Valid @RequestBody MarketingFullDiscountAddRequest request) {
        request.valid();

        request.setIsBoss(BoolFlag.NO);
        request.setStoreId(commonUtil.getStoreId());
        request.setCreatePerson(commonUtil.getOperatorId());
        this.valid(request.getJoinLevel(), request.getStoreId(), request.getScopeType(), request.getScopeIds());
        //全局互斥
        marketingBaseService.mutexValidate(MarketingType.DISCOUNT, request.getBeginTime(), request.getEndTime(),
                request.getScopeType(), request.getScopeIds(), request.getStoreId(), null);
        marketingFullDiscountProvider.add(request);

        operateLogMQUtil.convertAndSend("营销", "创建满折活动", "创建满折活动：" + request.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改满折营销信息
     *
     * @param request
     * @return
     */
    @Operation(summary = "修改满折营销信息")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse modify(@Valid @RequestBody MarketingFullDiscountModifyRequest request) {

        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setStoreId(commonUtil.getStoreId());

        if (DefaultFlag.YES.equals(request.getIsPause()) && LocalDateTime.now().isAfter(request.getBeginTime())) {
            //暂停中,满折活动可以编辑
            marketingProvider.pauseModify(PauseModifyRequest.builder()
                    .marketingId(request.getMarketingId())
                    .endTime(request.getEndTime())
                    .joinLevel(request.getJoinLevel())
                    .updatePerson(request.getUpdatePerson())
                    .build());
        } else {
            request.valid();
            this.valid(request.getJoinLevel(), request.getStoreId(), request.getScopeType(), request.getScopeIds());
            //全局互斥
            marketingBaseService.mutexValidate(MarketingType.DISCOUNT, request.getBeginTime(), request.getEndTime(),
                    request.getScopeType(), request.getScopeIds(), request.getStoreId(), request.getMarketingId());
            marketingFullDiscountProvider.modify(request);
        }

        operateLogMQUtil.convertAndSend("营销", "编辑促销活动", "编辑促销活动：" + request.getMarketingName());
        return BaseResponse.SUCCESSFUL();
    }

    private void valid(String joinLevel, Long storeId, MarketingScopeType scopeType, List<String> scopeIds){
        // 等级参数有效性
        if (!StringUtils.equals(joinLevel, "0") && !StringUtils.equals(joinLevel, "-1")){
            Set<String> levelIds;
            if (BoolFlag.NO.equals(commonUtil.getCompanyType())){
                CustomerLevelListResponse customerLevelListResponse =
                        customerLevelQueryProvider.listAllCustomerLevel().getContext();
                levelIds =
                        customerLevelListResponse.getCustomerLevelVOList().stream().map(CustomerLevelVO::getCustomerLevelId)
                                .map(Objects::toString).collect(Collectors.toSet());
            } else {
                StoreLevelListResponse storeLevelListResponse =
                        storeLevelQueryProvider.listAllStoreLevelByStoreId(StoreLevelListRequest.builder().storeId(storeId).build()).getContext();
                levelIds =
                        storeLevelListResponse.getStoreLevelVOList().stream().map(StoreLevelVO::getStoreLevelId)
                                .map(Objects::toString).collect(Collectors.toSet());
            }
            if (!levelIds.containsAll(Arrays.asList(joinLevel.split(",")))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        if (MarketingScopeType.SCOPE_TYPE_BRAND.equals(scopeType)){
            List<Long> ids = scopeIds.stream().map(Long::parseLong).collect(Collectors.toList());
            List<GoodsBrandVO> goodsBrandVOS = goodsBrandQueryProvider.listByIds(GoodsBrandByIdsRequest.builder().brandIds(ids).build()).getContext().getGoodsBrandVOList();
            if (goodsBrandVOS.size() != scopeIds.size()){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else if (MarketingScopeType.SCOPE_TYPE_STORE_CATE.equals(scopeType)){
            StoreCateListByIdsRequest storeCateListByIdsRequest = new StoreCateListByIdsRequest();
            List<Long> ids = scopeIds.stream().map(Long::parseLong).collect(Collectors.toList());
            storeCateListByIdsRequest.setCateIds(ids);
            List<StoreCateVO> storeCateVOList = storeCateQueryProvider.listByIds(storeCateListByIdsRequest).getContext().getStoreCateVOList();
            if (storeCateVOList.size() != scopeIds.size()){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else if(MarketingScopeType.SCOPE_TYPE_GOODS_CATE.equals(scopeType)){
            List<Long> ids = scopeIds.stream().map(Long::parseLong).collect(Collectors.toList());
            GoodsCateByIdsRequest goodsCateByIdsRequest = new GoodsCateByIdsRequest();
            goodsCateByIdsRequest.setCateIds(ids);
            List<GoodsCateVO> goodsCateVOList = goodsCateQueryProvider.getByIds(goodsCateByIdsRequest).getContext().getGoodsCateVOList();
            if (goodsCateVOList.size() != scopeIds.size()){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}
