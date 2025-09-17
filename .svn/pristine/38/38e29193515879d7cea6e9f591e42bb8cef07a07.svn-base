package com.wanmi.sbc.buycycle;


import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoRequest;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsProvider;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoods.*;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoAddRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoSaveRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsChangeRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.buycyclegoods.BuyCycleGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoods.BuyCycleGoodsListResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoods.BuyCycleGoodsPageResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByGoodsIdresponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.order.api.provider.follow.FollowProvider;
import com.wanmi.sbc.order.api.request.follow.FollowCycleUpdateRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Tag(name =  "周期购spu表管理API", description =  "BuyCycleGoodsController")
@RestController
@RequestMapping(value = "/buyCycle")
public class BuyCycleGoodsController {

    @Autowired
    private BuyCycleGoodsQueryProvider buyCycleGoodsQueryProvider;

    @Autowired
    private BuyCycleGoodsProvider buyCycleGoodsProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsGoodsInfoElasticProvider esGoodsInfoElasticProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private FollowProvider followProvider;

    @Autowired
    private BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;

    @Autowired
    private MarketingBaseService marketingBaseService;


    @Operation(summary = "分页查询周期购spu表")
    @PostMapping("/page")
    public BaseResponse<BuyCycleGoodsPageResponse> getPage(@RequestBody @Valid BuyCycleGoodsPageRequest pageReq) {
        pageReq.putSort("createTime", "desc");
        pageReq.setStoreId(commonUtil.getStoreId());
        return buyCycleGoodsQueryProvider.page(pageReq);
    }

    @Operation(summary = "列表查询周期购spu表")
    @PostMapping("/list")
    public BaseResponse<BuyCycleGoodsListResponse> getList(@RequestBody @Valid BuyCycleGoodsListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.setStoreId(commonUtil.getStoreId());
        return buyCycleGoodsQueryProvider.list(listReq);
    }

    @Operation(summary = "根据id查询周期购spu表")
    @GetMapping("/{id}")
    public BaseResponse<BuyCycleGoodsByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BuyCycleGoodsByIdRequest idReq = new BuyCycleGoodsByIdRequest();
        idReq.setId(id);
        BuyCycleGoodsVO buyCycleGoodsVO = buyCycleGoodsQueryProvider.getById(idReq).getContext().getBuyCycleGoodsVO();
        String goodsId = buyCycleGoodsVO.getGoodsId();
        Long storeId = commonUtil.getStoreId();
        BaseResponse<GoodsInfoByGoodsIdresponse> byGoodsId = goodsInfoQueryProvider.getByGoodsId(DistributionGoodsChangeRequest.builder()
                .goodsId(goodsId)
                .showProviderInfoFlag(Boolean.TRUE)
                .showVendibilityFlag(Boolean.TRUE)
                .showSpecFlag(Boolean.TRUE)
                .showDeleteFlag(Boolean.TRUE)
                .build());
        List<GoodsInfoVO> goodsInfoVOList = byGoodsId.getContext().getGoodsInfoVOList();
        //如果该商品不是此店铺商品，则没有权限访问
        Optional<GoodsInfoVO> optional = goodsInfoVOList.parallelStream()
                .filter(goodsInfoVO -> !Objects.equals(goodsInfoVO.getStoreId(), storeId)).findFirst();
        if (optional.isPresent()){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        // 设置品牌名称
        List<Long> brandIds = goodsInfoVOList.parallelStream().map(GoodsInfoVO::getBrandId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(brandIds)) {
            List<GoodsBrandVO> goodsBrandVOList = goodsBrandQueryProvider.listByIds(GoodsBrandByIdsRequest.builder()
                    .brandIds(brandIds)
                    .build()).getContext().getGoodsBrandVOList();
            Map<Long, GoodsBrandVO> brandVOMap = goodsBrandVOList.parallelStream().collect(Collectors.toMap(GoodsBrandVO::getBrandId, Function.identity()));
            goodsInfoVOList.forEach(goodsInfoVO -> {
                Long brandId = goodsInfoVO.getBrandId();
                if (Objects.nonNull(brandId)) {
                    goodsInfoVO.setBrandName(brandVOMap.get(brandId).getBrandName());
                }
            });
        }

        Map<String, GoodsInfoVO> skuMap = goodsInfoVOList.parallelStream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        buyCycleGoodsVO.getBuyCycleGoodsInfos().forEach(buyCycleGoodsInfoVO -> {
            GoodsInfoVO goodsInfoVO = skuMap.get(buyCycleGoodsInfoVO.getGoodsInfoId());
            buyCycleGoodsInfoVO.setGoodsInfo(goodsInfoVO);
            CheckStatus auditStatus = goodsInfoVO.getAuditStatus();
            Integer minCycleNum = buyCycleGoodsInfoVO.getMinCycleNum();
            if (Objects.equals(goodsInfoVO.getDelFlag(),DeleteFlag.YES)) {
                buyCycleGoodsInfoVO.setStateDesc(StateDesc.ALREADY_DELETE.getDesc());
            } else if (Objects.nonNull(goodsInfoVO.getProviderId()) && Objects.equals(goodsInfoVO.getVendibility(), Constants.ZERO)){
                buyCycleGoodsInfoVO.setStateDesc(StateDesc.NOT_SALE.getDesc());
            } else if (CheckStatus.FORBADE.equals(auditStatus)) {
                buyCycleGoodsInfoVO.setStateDesc(StateDesc.FORBID_SALE.getDesc());
            } else if (!AddedFlag.YES.equals(AddedFlag.fromValue(goodsInfoVO.getAddedFlag()))) {
                buyCycleGoodsInfoVO.setStateDesc(StateDesc.UNDERCARRIAGE.getDesc());
            } else if (minCycleNum > goodsInfoVO.getStock()) {
                buyCycleGoodsInfoVO.setStateDesc(StateDesc.OUT_STOCK.getDesc());
            }
        });
        return BaseResponse.success(new BuyCycleGoodsByIdResponse(buyCycleGoodsVO));
    }

    @Operation(summary = "新增周期购spu表")
    @PostMapping("/add")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse add(@RequestBody @Valid BuyCycleGoodsAddRequest addReq) {
        //校验周期购，并返回商品名称
        String goodsName = checkBuyCycle(addReq.getGoodsId(), KsBeanUtil.convert(addReq.getBuyCycleGoodsInfoAddRequestList(),BuyCycleGoodsInfoSaveRequest.class),Boolean.FALSE);
        addReq.setDelFlag(DeleteFlag.NO);
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCycleState(Constants.ZERO);
        addReq.setStoreId(commonUtil.getStoreId());
        addReq.setGoodsName(goodsName);
        //营销互斥校验
        Long storeId = commonUtil.getStoreId();
        List<String> skuList = addReq.getBuyCycleGoodsInfoAddRequestList().stream().map(BuyCycleGoodsInfoAddRequest::getGoodsInfoId).toList();
        marketingBaseService.mutexValidateByAdd(storeId, LocalDateTime.now(), LocalDateTime.now().plusYears(100), skuList);
        //新增周期购
        buyCycleGoodsProvider.add(addReq);

        // 收藏赠品修改周期购标识
        List<String> skuIds = addReq.getBuyCycleGoodsInfoAddRequestList()
                .stream()
                .map(BuyCycleGoodsInfoAddRequest::getGoodsInfoId)
                .collect(Collectors.toList());
        followProvider.cycleUpdate(FollowCycleUpdateRequest.builder()
                .isBuyCycle(Constants.ONE)
                .goodsInfoIds(skuIds).build());
        //同步周期购标识
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder()
                .idList(Lists.newArrayList(addReq.getGoodsId()))
                .build());
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改周期购spu表")
    @PutMapping("/modify")
    @MultiSubmit
    public BaseResponse modify(@RequestBody @Valid BuyCycleGoodsModifyRequest modifyReq) {
        BuyCycleGoodsByIdRequest idReq = new BuyCycleGoodsByIdRequest();
        idReq.setId(modifyReq.getId());
        BuyCycleGoodsVO buyCycleGoodsVO = buyCycleGoodsQueryProvider.getById(idReq).getContext().getBuyCycleGoodsVO();
        String goodsId = buyCycleGoodsVO.getGoodsId();
        //校验周期购
        checkBuyCycle(goodsId, KsBeanUtil.convert(modifyReq.getBuyCycleGoodsInfoModifyRequests(),BuyCycleGoodsInfoSaveRequest.class),Boolean.TRUE);
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        return buyCycleGoodsProvider.modify(modifyReq);
    }

    @Operation(summary = "根据id删除周期购spu表")
    @DeleteMapping("/{id}")
    @MultiSubmit
    public BaseResponse deleteById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        BuyCycleGoodsVO buyCycleGoodsVO = buyCycleGoodsQueryProvider.getById(BuyCycleGoodsByIdRequest.builder()
                .id(id)
                .build())
                .getContext().getBuyCycleGoodsVO();
        Integer cycleState = buyCycleGoodsVO.getCycleState();
        //生效中的周期购不能删除
        if (Objects.equals(cycleState,Constants.ZERO)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050160);
        }
        String goodsId = buyCycleGoodsVO.getGoodsId();
        //校验周期购
        checkBuyCycle(goodsId, Lists.newArrayList(),Boolean.TRUE);
        BuyCycleGoodsDelByIdRequest
                delByIdReq = new BuyCycleGoodsDelByIdRequest();
        delByIdReq.setId(id);
        buyCycleGoodsProvider.deleteById(delByIdReq);
        //同步周期购标识
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder()
                .idList(Lists.newArrayList(goodsId))
                .build());
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改周期购spu表状态")
    @PutMapping("/modifyState")
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse modifyState(@RequestBody @Valid BuyCycleGoodsModifyStateRequest modifyStateRequest) {
        BuyCycleGoodsVO buyCycleGoodsVO = buyCycleGoodsQueryProvider.getById(BuyCycleGoodsByIdRequest.builder()
                .id(modifyStateRequest.getId())
                .build())
                .getContext().getBuyCycleGoodsVO();
        String goodsId = buyCycleGoodsVO.getGoodsId();
        checkBuyCycle(goodsId, Lists.newArrayList(),Objects.equals(modifyStateRequest.getCycleState(),Constants.yes));
        buyCycleGoodsProvider.modifyState(modifyStateRequest);
        //修改收藏商品周期购标识
        Integer isBuyCycle = modifyStateRequest.getCycleState() == Constants.ZERO ? Constants.ONE : Constants.ZERO;
        List<String> skuIds = buyCycleGoodsVO.getBuyCycleGoodsInfos().stream().map(BuyCycleGoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        followProvider.cycleUpdate(FollowCycleUpdateRequest.builder()
                .isBuyCycle(isBuyCycle)
                .goodsInfoIds(skuIds).build());
        //同步周期购标识
        esGoodsInfoElasticProvider.initEsGoodsInfo(EsGoodsInfoRequest.builder()
                .idList(Lists.newArrayList(goodsId))
                .build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验周期购物,正常返回商品名称
     * @param goodsId
     * @param buyCycleGoodsInfoSaveRequestList
     */
    private String checkBuyCycle(String goodsId, List<BuyCycleGoodsInfoSaveRequest> buyCycleGoodsInfoSaveRequestList, Boolean isModify) {
        //先校验spu
        GoodsByIdResponse goods = goodsQueryProvider.getById(GoodsByIdRequest.builder()
                .goodsId(goodsId)
                .build()).getContext();
        //新增校验
        if (!isModify) {
            //如果为null，或者被删除，则提示商品不存在
            if (Objects.isNull(goods) || DeleteFlag.YES.equals(goods.getDelFlag())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            //是否是实体商品，周期购仅支持实体商品
            Integer goodsType = goods.getGoodsType();
            if(!GoodsType.REAL_GOODS.equals(GoodsType.fromValue(goodsType))) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030174);
            }
            //不支持批发
            Integer saleType = goods.getSaleType();
            if (Objects.equals(SaleType.WHOLESALE,SaleType.fromValue(saleType))) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080141);
            }
            //商品已经禁售
            CheckStatus auditStatus = goods.getAuditStatus();
            if (Objects.equals(CheckStatus.FORBADE,auditStatus)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030176);
            }
        }
        //如果该商品不是此店铺商品，则没有权限访问
        Long storeId = goods.getStoreId();
        if (!Objects.equals(storeId,commonUtil.getStoreId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
        //再校验sku
        if (CollectionUtils.isNotEmpty(buyCycleGoodsInfoSaveRequestList)) {
            List<String> skuIds = buyCycleGoodsInfoSaveRequestList.parallelStream().map(BuyCycleGoodsInfoSaveRequest::getGoodsInfoId)
                    .distinct().collect(Collectors.toList());
            //说明有重复的sku
            if (!Objects.equals(skuIds.size(),buyCycleGoodsInfoSaveRequestList.size())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            buyCycleGoodsInfoSaveRequestList.forEach(buyCycleGoodsInfoSaveRequest -> {
                String goodsInfoId;
                if (isModify) {
                    BuyCycleGoodsInfoVO buyCycleGoodsInfoVO = buyCycleGoodsInfoQueryProvider.findById(BuyCycleGoodsInfoByIdRequest.builder()
                            .id(buyCycleGoodsInfoSaveRequest.getId())
                            .build()).getContext().getBuyCycleGoodsInfoVO();
                    if (Objects.isNull(buyCycleGoodsInfoVO)) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                    goodsInfoId = buyCycleGoodsInfoVO.getGoodsInfoId();
                } else {
                    goodsInfoId = buyCycleGoodsInfoSaveRequest.getGoodsInfoId();
                }

                GoodsInfoByIdResponse goodsInfo = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder()
                        .goodsInfoId(goodsInfoId)
                        .build()).getContext();
                if (!isModify) {
                    //如果为null，或者被删除，则提示商品不存在
                    if (Objects.isNull(goodsInfo) || DeleteFlag.YES.equals(goodsInfo.getDelFlag())) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030047);
                    }
                    //供应商商品不可售
                    Integer vendibility = goodsInfo.getVendibility();
                    if (Objects.nonNull(goodsInfo.getProviderId()) && vendibility.equals(Constants.no)) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030172);
                    }
                    //是否下架
                    Integer addedFlag = goodsInfo.getAddedFlag();
                    if (!AddedFlag.YES.equals(AddedFlag.fromValue(addedFlag))) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030041);
                    }
                }
                //是否是实体商品，周期购仅支持实体商品
                Integer goodsTypeForSku = goodsInfo.getGoodsType();
                if(!GoodsType.REAL_GOODS.equals(GoodsType.fromValue(goodsTypeForSku))) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030174);
                }
                //如果sku所属的spu跟传入的不一致，则参数不正确
                if (!StringUtils.equals(goodsId,goodsInfo.getGoodsId())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                Long stock = goodsInfo.getStock();
                Integer minCycleNum = buyCycleGoodsInfoSaveRequest.getMinCycleNum();
                //最低期数校验
                if (Objects.isNull(minCycleNum) || minCycleNum < Constants.ONE || minCycleNum > Constants.NUM_365) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                //最低期数必须小于sku库存
                if (minCycleNum > stock) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030175);
                }
                //每期价格校验
                BigDecimal cyclePrice = buyCycleGoodsInfoSaveRequest.getCyclePrice();
                if (Objects.isNull(cyclePrice) || cyclePrice.compareTo(BigDecimal.ZERO) <= Constants.ZERO || cyclePrice.compareTo(BigDecimal.valueOf(9999999.99)) > Constants.ZERO) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
        }
        return goods.getGoodsName();
    }
}
