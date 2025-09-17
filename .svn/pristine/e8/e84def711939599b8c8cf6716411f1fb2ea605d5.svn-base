package com.wanmi.sbc.newcomerpurchaseconfig;

import com.alibaba.fastjson2.JSONArray;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmitWithToken;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.elastic.api.provider.sku.EsSkuQueryProvider;
import com.wanmi.sbc.elastic.api.request.sku.EsSkuPageRequest;
import com.wanmi.sbc.elastic.api.response.sku.EsSkuPageResponse;
import com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods.NewcomerPurchaseGoodsProvider;
import com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods.NewcomerPurchaseGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.NewcomerPurchaseGoodsListRequest;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.NewcomerPurchaseGoodsPageMagicRequest;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.NewcomerPurchaseGoodsSaveRequest;
import com.wanmi.sbc.goods.api.response.newcomerpurchasegoods.NewcomerPurchaseGoodsMagicPageResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchaseconfig.NewcomerPurchaseConfigProvider;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchaseconfig.NewcomerPurchaseConfigQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newcomerpurchasecoupon.NewcomerPurchaseCouponQueryProvider;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigAddRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigDelByIdListRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigModifyRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.NewcomerPurchaseCouponGetFetchRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigAddResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigByIdResponse;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigModifyResponse;
import com.wanmi.sbc.marketing.bean.enums.FullBuyType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Tag(name =  "新人专享设置管理API", description =  "NewcomerPurchaseConfigController")
@RestController
@Validated
@RequestMapping(value = "/newcomerPurchaseConfig")
public class NewcomerPurchaseConfigController {

    @Autowired
    private NewcomerPurchaseConfigQueryProvider newcomerPurchaseConfigQueryProvider;

    @Autowired
    private NewcomerPurchaseConfigProvider newcomerPurchaseConfigProvider;

    @Autowired
    private NewcomerPurchaseGoodsProvider newcomerPurchaseGoodsProvider;

    @Autowired
    private NewcomerPurchaseGoodsQueryProvider newcomerPurchaseGoodsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsSkuQueryProvider esSkuQueryProvider;

    @Autowired
    private NewcomerPurchaseCouponQueryProvider newcomerPurchaseCouponQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Operation(summary = "根据id查询新人专享设置")
    @GetMapping
    public BaseResponse<NewcomerPurchaseConfigByIdResponse> getOne() {
        return newcomerPurchaseConfigQueryProvider.getOne();
    }

    @Operation(summary = "新增新人专享设置")
    @PostMapping("/add")
    public BaseResponse<NewcomerPurchaseConfigAddResponse> add(@RequestBody @Valid NewcomerPurchaseConfigAddRequest addReq) {
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        return newcomerPurchaseConfigProvider.add(addReq);
    }

    @Operation(summary = "修改新人专享设置")
    @PutMapping("/modify")
    public BaseResponse<NewcomerPurchaseConfigModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseConfigModifyRequest modifyReq) {
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        return newcomerPurchaseConfigProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除新人专享设置")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Integer id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        NewcomerPurchaseConfigDelByIdRequest delByIdReq = new NewcomerPurchaseConfigDelByIdRequest();
        delByIdReq.setId(id);
        return newcomerPurchaseConfigProvider.deleteById(delByIdReq);
    }

    @Operation(summary = "根据idList批量删除新人专享设置")
    @DeleteMapping("/delete-by-id-list")
    public BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseConfigDelByIdListRequest delByIdListReq) {
        return newcomerPurchaseConfigProvider.deleteByIdList(delByIdListReq);
    }


    @Operation(summary = "查询新人专享设置详情")
    @GetMapping("/detail")
    public BaseResponse<NewcomerPurchaseConfigByIdResponse> detail() {
        List<NewcomerPurchaseGoodsVO> newcomerPurchaseGoodsVOList = newcomerPurchaseGoodsQueryProvider.list(NewcomerPurchaseGoodsListRequest.builder()
                .delFlag(DeleteFlag.NO)
                .build()).getContext().getNewcomerPurchaseGoodsVOList();
        NewcomerPurchaseConfigByIdResponse response = newcomerPurchaseConfigQueryProvider.detail().getContext();
        if (Objects.isNull(response.getNewcomerPurchaseConfigVO())) {
            return BaseResponse.success(new NewcomerPurchaseConfigByIdResponse());
        }
        List<String> goodsInfoIds = newcomerPurchaseGoodsVOList.parallelStream()
                .map(NewcomerPurchaseGoodsVO::getGoodsInfoId).collect(Collectors.toList());
        List<GoodsInfoVO> goodsInfoList = getGoodsInfoList(goodsInfoIds);

        if(CollectionUtils.isNotEmpty(goodsInfoList)){
            goodsBaseService.populateMarketingGoodsStatus(goodsInfoList);
        }

        response.getNewcomerPurchaseConfigVO().setGoodsInfoVOList(goodsInfoList);
        return BaseResponse.success(response);
    }

    @Operation(summary = "保存新人专享设置详情")
    @PutMapping("/save")
    @GlobalTransactional
    @MultiSubmitWithToken
    public BaseResponse save(@RequestBody @Valid NewcomerPurchaseConfigModifyRequest modifyRequest){

        if (modifyRequest.getStartTime().isAfter(modifyRequest.getEndTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        String poster = modifyRequest.getPoster();
        JSONArray array = JSONArray.parseArray(poster);
        if (array.size()== 0  || array.size() > 8) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<String> goodsInfoIds = modifyRequest.getGoodsInfoIds();
        long count = goodsInfoIds.parallelStream().filter(StringUtils::isEmpty).count();
        if (count > 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<GoodsInfoVO> goodsInfoList = getGoodsInfoList(goodsInfoIds);
        if (goodsInfoList.size() != goodsInfoIds.size()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //保存新人购商品
        newcomerPurchaseGoodsProvider.saveAll(NewcomerPurchaseGoodsSaveRequest.builder()
                .goodsInfoIds(goodsInfoIds)
                .build());
        return newcomerPurchaseConfigProvider.save(modifyRequest);
    }

    /**
     * 查询商品信息
     * @param goodsInfoIds
     * @return
     */
    private  List<GoodsInfoVO> getGoodsInfoList(List<String> goodsInfoIds) {
        if (CollectionUtils.isEmpty(goodsInfoIds)) {
            return Lists.newLinkedList();
        }
        EsSkuPageRequest esSkuPageRequest = new EsSkuPageRequest();
        esSkuPageRequest.setPageSize(goodsInfoIds.size());
        esSkuPageRequest.setGoodsInfoIds(goodsInfoIds);
        esSkuPageRequest.setDelFlag(DeleteFlag.NO.toValue());
        EsSkuPageResponse response = esSkuQueryProvider.page(esSkuPageRequest).getContext();
        return response.getGoodsInfoPage().getContent();
    }

    @Operation(summary = "魔方查询新人专享商品")
    @PostMapping("/magicPage")
    public BaseResponse<NewcomerPurchaseGoodsMagicPageResponse> magicGoods(@RequestBody @Valid NewcomerPurchaseGoodsPageMagicRequest request) {
        //检查活动是否有效
        Boolean active = newcomerPurchaseConfigQueryProvider.checkActive().getContext();
        if (!active) {
            return BaseResponse.success(new NewcomerPurchaseGoodsMagicPageResponse(new MicroServicePage<>()));
        }

        BaseResponse<NewcomerPurchaseGoodsMagicPageResponse> response = newcomerPurchaseGoodsQueryProvider.magicPage(request);

        //查询可使用的新人券
        List<CouponInfoVO> coupons = newcomerPurchaseCouponQueryProvider
                .getFetchCoupons(NewcomerPurchaseCouponGetFetchRequest.builder().build())
                .getContext().getCoupons();

        response.getContext().getNewcomerPurchaseGoodsVOS().forEach(entity -> {
            Optional<CouponInfoVO> optional = coupons.stream().filter(coupon -> {
                boolean flag = true;
                if (FullBuyType.FULL_MONEY == coupon.getFullBuyType()
                        && entity.getMarketPrice().compareTo(coupon.getFullBuyPrice()) < NumberUtils.INTEGER_ZERO) {
                    flag = false;
                }
                switch (coupon.getScopeType()) {
                    case ALL:
                        break;
                    case BRAND:
                        if (Objects.isNull(entity.getBrandId())
                                || !coupon.getScopeIds().contains(entity.getBrandId().toString())) {
                            flag = false;
                        }
                        break;
                    case BOSS_CATE:
                        if (!coupon.getScopeIds().contains(entity.getCateId().toString())) {
                            flag = false;
                        }
                        break;
                    case SKU:
                        if (!coupon.getScopeIds().contains(entity.getGoodsInfoId())) {
                            flag = false;
                        }
                        break;
                    case STORE:
                        if (!coupon.getScopeIds().contains(entity.getStoreId())) {
                            flag = false;
                        }
                        break;
                    default:
                }
                return flag;
            }).max(Comparator.comparing(CouponInfoVO::getDenomination));
            if (optional.isPresent()) {
                BigDecimal denomination = optional.get().getDenomination();
                if (entity.getMarketPrice().compareTo(denomination) > NumberUtils.INTEGER_ZERO) {
                    entity.setCouponPrice(entity.getMarketPrice().subtract(optional.get().getDenomination()));
                } else {
                    entity.setCouponPrice(BigDecimal.ZERO);
                }
            }
        });

        return response;
    }

}
