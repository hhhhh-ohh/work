package com.wanmi.sbc.goods.provider.impl.goods;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goods.GoodsProvider;
import com.wanmi.sbc.goods.api.request.goods.*;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigUpdateRequest;
import com.wanmi.sbc.goods.api.response.goods.*;
import com.wanmi.sbc.goods.api.response.linkedmall.ThirdPlatformGoodsDelResponse;
import com.wanmi.sbc.goods.bean.dto.*;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.CommissionPriceTargetVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.request.GoodsRequest;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;
import com.wanmi.sbc.goods.info.service.*;
import com.wanmi.sbc.goods.standard.model.root.StandardGoodsRel;
import com.wanmi.sbc.goods.standard.repository.StandardGoodsRelRepository;
import com.wanmi.sbc.goods.standard.service.StandardImportAsyncService;
import com.wanmi.sbc.goods.standard.service.StandardImportService;
import com.wanmi.sbc.goods.wechatvideosku.service.WechatSkuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * com.wanmi.sbc.goods.provider.impl.goods.GoodsController
 *
 * @author lipeng
 * @dateTime 2018/11/7 下午3:20
 */
@RestController
@Validated
@Slf4j
public class GoodsController implements GoodsProvider {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StandardImportService standardImportService;

    @Autowired
    private S2bGoodsService s2bGoodsService;

    @Autowired
    private GoodsStockService goodsStockService;

    @Autowired
    private ThirdPlatformGoodsService thirdPlatformGoodsService;

    @Autowired
    private StandardGoodsRelRepository standardGoodsRelRepository;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StandardImportAsyncService standardImportAsyncService;

    @Resource
    private RedisUtil redisService;

    @Autowired
    private GoodsCommissionPriceConfigService goodsCommissionPriceConfigService;

    @Autowired
    private GoodsCommissionConfigService goodsCommissionConfigService;

    @Autowired
    private GoodsCommissionPriceService goodsCommissionPriceService;

    @Autowired
    private WechatSkuService wechatSkuService;

    @Autowired
    private GoodsLedgerService goodsLedgerService;

    /**
     * 新增商品
     *
     * @param request {@link GoodsAddRequest}
     * @return 新增结果 {@link GoodsAddResponse}
     */
    @Override
    public BaseResponse<GoodsAddResponse> add(@RequestBody @Valid GoodsAddRequest request) {
        GoodsSaveRequest goodsSaveRequest = KsBeanUtil.convert(request, GoodsSaveRequest.class);
        List<GoodsPropertyDetailRelDTO> goodsDetailRel = request.getGoodsDetailRel();
        // 处理商品与属性关联关系数据
        if (CollectionUtils.isNotEmpty(goodsDetailRel)){
            // 把属性值为空的数据去除掉
            List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = this.getGoodsPropertyDetailList(goodsDetailRel);
            goodsSaveRequest.setGoodsPropertyDetailRel(propertyDetailRels);
        }

        return BaseResponse.success(goodsService.add(goodsSaveRequest));
    }

    @Override
    public BaseResponse<ThirdPlatformGoodsDelResponse> deleteThirdPlatformGoods(@Valid ThirdPlatformGoodsDelRequest request) {
        List<String> esGoodsInfoIds = new ArrayList<>();
        List<String> standardIds = new ArrayList<>();
        List<String> delStandardIds = new ArrayList<>();
        for (ThirdPlatformGoodsDelDTO thirdPlatformGoodsDelDTO : request.getThirdPlatformGoodsDelDTOS()) {
            RLock fairLock = redissonClient.getFairLock(thirdPlatformGoodsDelDTO.getItemId().toString());
            fairLock.lock();
            try {
                ThirdPlatformGoodsDelResponse response = thirdPlatformGoodsService.thirdPlatformGoodsDel(thirdPlatformGoodsDelDTO, request.getDeleteAllSku());
                esGoodsInfoIds.addAll(response.getGoodsInfoIds());
                standardIds.addAll(response.getStandardIds());
                delStandardIds.addAll(response.getDelStandardIds());
            } catch (Exception e) {
                throw e;
            } finally {
                fairLock.unlock();
            }

        }
        ThirdPlatformGoodsDelResponse response = new ThirdPlatformGoodsDelResponse();
        response.setGoodsInfoIds(esGoodsInfoIds);
        response.setStandardIds(standardIds);
        response.setDelStandardIds(delStandardIds);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse vendibilityThirdGoods(@Valid ThirdGoodsVendibilityRequest request) {
        goodsService.vendibilityLinkedmallGoods(request);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 修改商品
     *
     * @param request {@link GoodsModifyRequest}
     * @return 修改结果 {@link GoodsModifyResponse}
     */
    @Override
    public BaseResponse<GoodsModifyResponse> modify(@RequestBody @Valid GoodsModifyRequest request) {
        GoodsSaveRequest goodsSaveRequest = KsBeanUtil.convert(request, GoodsSaveRequest.class);
        List<GoodsPropertyDetailRelDTO> goodsDetailRel = request.getGoodsDetailRel();
        // 处理商品与属性关联关系数据
        if (CollectionUtils.isNotEmpty(goodsDetailRel)){
            // 把属性值为空的数据去除掉
            List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = this.getGoodsPropertyDetailList(goodsDetailRel);
            goodsSaveRequest.setGoodsPropertyDetailRel(propertyDetailRels);
        }
        goodsSaveRequest.getGoods().setAddedTimingTime(request.getGoods().getAddedTimingTime());
        Integer goodsType = request.getGoods().getGoodsType();
        goodsSaveRequest.getGoodsInfos().forEach(goodsInfoSaveDTO -> goodsInfoSaveDTO.setGoodsType(goodsType));
        Goods goods = goodsService.getGoodsById(goodsSaveRequest.getGoods().getGoodsId());
        GoodsModifyInfoResponse goodsModifyInfoResponse;
        GoodsModifyResponse response = new GoodsModifyResponse();
        //供应商   LinkedMall   VOP
        if ((0 == goods.getGoodsSource() || 4 == goods.getGoodsSource() || 2 == goods.getGoodsSource())
                && StringUtils.isEmpty(goods.getProviderGoodsId())) {
            // 供应商编辑商品
            goodsModifyInfoResponse = goodsService.edit(goodsSaveRequest);

            if (Objects.nonNull(goodsModifyInfoResponse)) {
                //商品编辑后，同步商品库(异步处理)
                StandardGoodsRel standardGoodsRel = standardGoodsRelRepository.findByGoodsId(goods.getGoodsId());
                if (Objects.nonNull(standardGoodsRel)) {
                    standardImportAsyncService.asyncProviderGoods(goods.getGoodsId(), standardGoodsRel.getStandardId());
                    response.setStandardIds(Collections.singletonList(standardGoodsRel.getStandardId()));
                } else {
                    // 平台禁售供应商商品库商品后，商品库关系被删除com.wanmi.sbc.goods.info.service.S2bGoodsService.dealStandardGoods
                    // 此时如果商品为审核通过状态，则同步到商品库
                    GoodsVO modifiedGoods = goodsModifyInfoResponse.getOldGoods();
                    if (Objects.equals(CheckStatus.CHECKED, modifiedGoods.getAuditStatus())) {
                        // 同步到商品库
                        GoodsRequest synRequest = new GoodsRequest();
                        synRequest.setGoodsIds(Collections.singletonList(modifiedGoods.getGoodsId()));
                        response.setStandardIds(standardImportService.importStandard(synRequest));
                    }
                }
                //更改商家代销商品的可售性
                Boolean isDealGoodsVendibility = goodsModifyInfoResponse.getIsDealGoodsVendibility();
                if (isDealGoodsVendibility != null && isDealGoodsVendibility) {
                    goodsService.dealGoodsVendibility(ProviderGoodsNotSellRequest.builder()
                            .checkFlag(Boolean.TRUE).stockFlag(Boolean.TRUE)
                            .goodsIds(Lists.newArrayList(goods.getGoodsId())).build());
                }
            }
        } else {
            if (StringUtils.isNotEmpty(goods.getProviderGoodsId())) {
                //处理加价比例
                this.updateCommissionPriceConfig(request.getGoodsInfos(), KsBeanUtil.convert(goods, GoodsSaveDTO.class), request.getUserId(), request.getIsIndependent());

            }
            goodsModifyInfoResponse = goodsService.edit(goodsSaveRequest);
        }

        // 删除商品商品缓存
        if (Objects.nonNull(goodsModifyInfoResponse)){
            redisService.delete(RedisKeyConstant.GOODS_DETAIL_CACHE + request.getGoods().getGoodsId());
        }
        response.setReturnMap(goodsModifyInfoResponse);
        return BaseResponse.success(response);
    }

    /**
     * @description  处理SKU加价比例
     * @author  wur
     * @date: 2021/10/15 11:03
     * @param goodsInfos  SKU信息
     * @param goods       SPU
     * @param userId      操作人
     **/
    private void updateCommissionPriceConfig(List<GoodsInfoVO> goodsInfos, GoodsSaveDTO goods, String userId, EnableStatus isIndependent) {
        //处理加价比例
        List<CommissionPriceTargetVO> commissionPriceTargetVOList = new ArrayList<>();
        goodsInfos.forEach(goodsInfoVO -> {
            if(Objects.nonNull(goodsInfoVO.getAddRate())){
                CommissionPriceTargetVO commissionPriceTargetVO = new CommissionPriceTargetVO();
                commissionPriceTargetVO.setEnableStatus(isIndependent);
                commissionPriceTargetVO.setAddRate(goodsInfoVO.getAddRate());
                commissionPriceTargetVO.setTargetId(goodsInfoVO.getGoodsInfoId());
                commissionPriceTargetVO.setTargetType(CommissionPriceTargetType.SKU);
                commissionPriceTargetVOList.add(commissionPriceTargetVO);
            }
        });
        if(CollectionUtils.isNotEmpty(commissionPriceTargetVOList)){
            GoodsCommissionPriceConfigUpdateRequest updateRequest = new GoodsCommissionPriceConfigUpdateRequest();
            updateRequest.setCommissionPriceTargetVOList(commissionPriceTargetVOList);
            updateRequest.setBaseStoreId(goods.getStoreId());
            updateRequest.setUserId(userId);
            goodsCommissionPriceConfigService.update(updateRequest);
        }
    }

    /**
     * 新增商品定价
     *
     * @param request {@link GoodsAddPriceRequest}
     */
    @Override
    public BaseResponse addPrice(@RequestBody @Valid GoodsAddPriceRequest request) {
        GoodsSaveRequest goodsSaveRequest = KsBeanUtil.convert(request, GoodsSaveRequest.class);
        goodsService.savePrice(goodsSaveRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 新增商品基本信息、基价
     *
     * @param request {@link GoodsAddAllRequest}
     * @return 商品编号 {@link GoodsAddAllResponse}
     */
    @Override

    public BaseResponse<GoodsAddAllResponse> addAll(@RequestBody @Valid GoodsAddAllRequest request) {
        GoodsSaveRequest goodsSaveRequest = KsBeanUtil.convert(request, GoodsSaveRequest.class);
        List<GoodsPropertyDetailRelDTO> goodsDetailRel = request.getGoodsDetailRel();
        // 处理商品与属性关联关系数据
        if (CollectionUtils.isNotEmpty(goodsDetailRel)){
            // 把属性值为空的数据去除掉
            List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = this.getGoodsPropertyDetailList(goodsDetailRel);
            goodsSaveRequest.setGoodsPropertyDetailRel(propertyDetailRels);
        }

        return BaseResponse.success(goodsService.addAll(goodsSaveRequest));
    }

    /**
     * 修改商品基本信息、基价
     *
     * @param request {@link GoodsModifyAllRequest}
     * @return 修改结果 {@link GoodsModifyInfoResponse}
     */
    @Override

    public BaseResponse<GoodsModifyInfoResponse> modifyAll(@RequestBody @Valid GoodsModifyAllRequest request) {
        GoodsSaveRequest goodsSaveRequest = KsBeanUtil.convert(request, GoodsSaveRequest.class);
        List<GoodsPropertyDetailRelDTO> goodsDetailRel = request.getGoodsDetailRel();
        // 处理商品与属性关联关系数据
        if (CollectionUtils.isNotEmpty(goodsDetailRel)){
            // 把属性值为空的数据去除掉
            List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = this.getGoodsPropertyDetailList(goodsDetailRel);
            goodsSaveRequest.setGoodsPropertyDetailRel(propertyDetailRels);
        }

        if (StringUtils.isNotEmpty(goodsSaveRequest.getGoods().getProviderGoodsId())) {
            //处理加价比例
            this.updateCommissionPriceConfig(request.getGoodsInfos(), goodsSaveRequest.getGoods(), request.getUserId(), request.getIsIndependent());

        }

        //未设置独立加价比例则自动变为店铺默认加价比例
        if (Objects.nonNull(request.getGoods().getProviderGoodsId())){
            GoodsCommissionConfig goodsCommissionConfig = goodsCommissionConfigService.queryBytStoreId(request.getBaseStoreId());

            if(Objects.nonNull(goodsCommissionConfig)
                    && Objects.equals(CommissionSynPriceType.AI_SYN,goodsCommissionConfig.getSynPriceType())
                    && !Objects.equals(EnableStatus.ENABLE,request.getIsIndependent())){

                goodsSaveRequest.getGoodsInfos().forEach(v->{
                    BigDecimal marketPrice = v.getSupplyPrice()
                            .add(goodsCommissionPriceService.getAddPrice(goodsCommissionConfig.getAddRate(), v.getSupplyPrice()));
                    v.setMarketPrice(marketPrice);
                });
                BigDecimal minMarketPrice = goodsSaveRequest
                        .getGoodsInfos()
                        .stream()
                        .map(GoodsInfoSaveDTO::getMarketPrice)
                        .min(Comparator.naturalOrder())
                        .orElse(null);
                goodsSaveRequest.getGoods().setMarketPrice(minMarketPrice);
            }
        }

        GoodsModifyInfoResponse map = goodsService.editAll(goodsSaveRequest);
        if (Objects.nonNull(map)) {
            //更改商家代销商品的可售性
            Boolean isDealGoodsVendibility = map.getIsDealGoodsVendibility();
            if (isDealGoodsVendibility != null && isDealGoodsVendibility) {
                goodsService.dealGoodsVendibility(ProviderGoodsNotSellRequest.builder().checkFlag(Boolean.TRUE).stockFlag(Boolean.TRUE)
                        .goodsIds(Lists.newArrayList(request.getGoods().getGoodsId())).build());
            }
        } else {
            map  = new GoodsModifyInfoResponse();
            map.setNewGoodsInfo(KsBeanUtil.convert(goodsSaveRequest.getGoodsInfos(), GoodsInfoVO.class));
        }
        return BaseResponse.success(map);
    }

    /**
     * 修改商品基本信息、基价
     *
     * @param request {@link GoodsDeleteByIdsRequest}
     */
    @Override
    public BaseResponse<GoodsDeleteResponse> deleteByIds(@RequestBody @Valid GoodsDeleteByIdsRequest request) {
        return BaseResponse.success(goodsService.delete(request));
    }

    /**
     * 供应商删除商品
     *
     * @param request {@link GoodsDeleteByIdsRequest}
     */
    @Override
    public BaseResponse<ProviderGoodsDelResponse> deleteProviderGoodsByIds(@RequestBody @Valid GoodsDeleteByIdsRequest request) {
        List<String> ids = goodsService.deleteProvider(request);
        return BaseResponse.success(ProviderGoodsDelResponse.builder().standardIds(ids).build());
    }

    /**
     * 修改商品上下架状态
     * @param request {@link GoodsModifyAddedStatusRequest}
     */
    @Override
    public BaseResponse modifyAddedStatus(@RequestBody @Valid GoodsModifyAddedStatusRequest request) {
        goodsService.updateAddedStatus(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改商品上下架状态
     *
     * @param request {@link GoodsModifyAddedStatusRequest}
     */
    @Override
    public BaseResponse providerModifyAddedStatus(@RequestBody @Valid GoodsModifyAddedStatusRequest request) {
        goodsService.providerUpdateAddedStatus(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 定时任务修改商品上下架状态
     *
     * @param request {@link GoodsModifyAddedStatusRequest}
     */
    @Override
    public BaseResponse providerModifyAddedStatusByTiming(@RequestBody @Valid GoodsModifyAddedStatusRequest request) {
        goodsService.providerModifyAddedStatusByTiming(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改商品分类
     *
     * @param request {@link GoodsModifyCateRequest}
     */
    @Override

    public BaseResponse modifyCate(@RequestBody @Valid GoodsModifyCateRequest request) {
        List<String> goodsIds = request.getGoodsIds();
        List<Long> storeCateIds = request.getStoreCateIds();
        goodsService.updateCate(goodsIds, storeCateIds);

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改商品商家名称
     *
     * @param request {@link GoodsModifySupplierNameRequest}
     */
    @Override

    public BaseResponse modifySupplierName(@RequestBody @Valid GoodsModifySupplierNameRequest request) {
        String supplierName = request.getSupplierName();
        Long companyInfoId = request.getCompanyInfoId();
        goodsService.updateSupplierName(supplierName, companyInfoId);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 修改商品运费模板
     *
     * @param request {@link GoodsModifyFreightTempRequest}
     */
    @Override

    public BaseResponse modifyFreightTemp(@RequestBody @Valid GoodsModifyFreightTempRequest request) {
        Long freightTempId = request.getFreightTempId();
        List<String> goodsIds = request.getGoodsIds();
        goodsService.updateFreight(freightTempId, goodsIds);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 商品审核
     *
     * @param request {@link GoodsCheckRequest}
     */
    @Override
    public BaseResponse<GoodsCheckResponse> checkGoods(@RequestBody @Valid GoodsCheckRequest request) {
        com.wanmi.sbc.goods.info.request.GoodsCheckRequest goodsCheckRequest =
                KsBeanUtil.convert(request, com.wanmi.sbc.goods.info.request.GoodsCheckRequest.class);
        GoodsCheckResponse response = s2bGoodsService.check(goodsCheckRequest);
        response.setGoodsCheckRequest(KsBeanUtil.convert(goodsCheckRequest,GoodsCheckRequest.class));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse updateGoodsCollectNum(@RequestBody @Valid GoodsModifyCollectNumRequest
                                                      goodsModifyCollectNumRequest) {
        goodsService.updateGoodsCollectNum(goodsModifyCollectNumRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateGoodsSalesNum(@RequestBody @Valid GoodsModifySalesNumRequest goodsModifySalesNumRequest) {
        goodsService.updateGoodsSalesNum(goodsModifySalesNumRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateGoodsFavorableCommentNum(@RequestBody @Valid GoodsModifyEvaluateNumRequest request) {
        goodsService.updateGoodsFavorableCommentNum(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyShamSalesNum(@RequestBody @Valid GoodsModifyShamSalesNumRequest request) {
        goodsService.updateShamSalesNum(request.getGoodsId(), request.getShamSalesNum());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifySortNo(@RequestBody @Valid GoodsModifySortNoRequest request) {
        goodsService.updateSortNo(request.getGoodsId(), request.getSortNo());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsSynResponse> synGoods(@RequestBody @Valid GoodsSynRequest request) {
        List<String> goodsIds = new ArrayList<>(10);
        for (String standardgoodsId : request.getGoodsIds()) {
            goodsIds.add(standardImportService.syn(standardgoodsId, request.getStoreId()));
        }
        return BaseResponse.success(new GoodsSynResponse(goodsIds));
    }

    /**
     * 更新代销商品供应商店铺状态
     */
    @Override
    public BaseResponse updateProviderStatus(@RequestBody @Valid GoodsProviderStatusRequest request) {
        goodsService.updateProviderStatus(request.getProviderStatus(), request.getStoreIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateStoreGoodsStatus(@Valid StoreGoodsStatusRequest request) {
        if (CollectionUtils.isEmpty(request.getStoreIds())) {
            return BaseResponse.SUCCESSFUL();
        }
        goodsService.updateGoodsStatus(request.getStoreIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse addGoodsToDefaultStoreCateRel(String goodsId) {
        goodsService.addGoodsToDefaultStoreCateRel(goodsId);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse changeAddedFlagByLedgerBindState() {
        goodsLedgerService.changeAddedByLedgerBindState();
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 过滤商品属性，只取有值的属性列表
     * @param goodsDetailRel
     * @return
     */
    public List<GoodsPropertyDetailRelSaveDTO> getGoodsPropertyDetailList(List<GoodsPropertyDetailRelDTO> goodsDetailRel){
        // 把属性值为空的数据去除掉
        List<GoodsPropertyDetailRel> propertyDetailRels = goodsDetailRel.stream()
                .filter(rel ->
                        (rel.getPropType() == GoodsPropertyEnterType.CHOOSE && CollectionUtils.isNotEmpty(rel.getDetailIdList()))
                                || (rel.getPropType() == GoodsPropertyEnterType.TEXT && StringUtils.isNoneBlank(rel.getPropValueText()))
                                || (rel.getPropType() == GoodsPropertyEnterType.DATE && Objects.nonNull(rel.getPropValueDate()))
                                || (rel.getPropType() == GoodsPropertyEnterType.PROVINCE && CollectionUtils.isNotEmpty(rel.getPropValueProvince()))
                                || (rel.getPropType() == GoodsPropertyEnterType.COUNTRY && CollectionUtils.isNotEmpty(rel.getPropValueCountry())))
                .map(this:: copyProperties).collect(Collectors.toList());
        return KsBeanUtil.convert(propertyDetailRels, GoodsPropertyDetailRelSaveDTO.class);
    }

    /**
     * 将list数据以逗号分隔进行存储
     *
     * @param goodsDetailRel
     * @return
     */
    private GoodsPropertyDetailRel copyProperties(GoodsPropertyDetailRelDTO goodsDetailRel) {
        GoodsPropertyDetailRel detailRel = KsBeanUtil.convert(goodsDetailRel, GoodsPropertyDetailRel.class);
        List<Long> goodsPropDetails = goodsDetailRel.getDetailIdList();
        if (CollectionUtils.isNotEmpty(goodsPropDetails)) {
            //排序处理方便后续比对
            Collections.sort(goodsPropDetails);
            String detailId = StringUtils.join(goodsPropDetails, ",");
            detailRel.setDetailId(detailId);
        }

        List<String> propValueProvinceList = goodsDetailRel.getPropValueProvince();
        if (CollectionUtils.isNotEmpty(propValueProvinceList)) {
            //排序处理方便后续比对
            Collections.sort(propValueProvinceList);
            String propValueProvince = StringUtils.join(propValueProvinceList, ",");
            detailRel.setPropValueProvince(propValueProvince);

        }

        List<Long> countryList = goodsDetailRel.getPropValueCountry();
        if (CollectionUtils.isNotEmpty(countryList)) {
            //排序处理方便后续比对
            Collections.sort(countryList);
            String countryId = StringUtils.join(countryList, ",");
            detailRel.setPropValueCountry(countryId);

        }
        detailRel.setDetailRelId(null);
        detailRel.setDelFlag(DeleteFlag.NO);
        detailRel.setCreateTime(LocalDateTime.now());
        detailRel.setUpdateTime(LocalDateTime.now());
        detailRel.setGoodsType(GoodsPropertyType.GOODS);
        return detailRel;
    }
}
