package com.wanmi.sbc.buyCycle;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.brand.GoodsBrandQueryProvider;
import com.wanmi.sbc.goods.api.provider.buycyclegoods.BuyCycleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.brand.GoodsBrandByIdsRequest;
import com.wanmi.sbc.goods.api.request.buycyclegoods.*;
import com.wanmi.sbc.goods.api.request.info.DistributionGoodsChangeRequest;
import com.wanmi.sbc.goods.api.response.buycyclegoods.BuyCycleGoodsByIdResponse;
import com.wanmi.sbc.goods.api.response.buycyclegoods.BuyCycleGoodsPageResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByGoodsIdresponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.StateDesc;
import com.wanmi.sbc.goods.bean.vo.*;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Tag(name =  "周期购spu表管理API", description =  "BuyCycleGoodsController")
@RestController
@RequestMapping(value = "/buyCycle")
public class BuyCycleGoodsController {

    @Autowired
    private BuyCycleGoodsQueryProvider buyCycleGoodsQueryProvider;


    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsBrandQueryProvider goodsBrandQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;


    @Operation(summary = "分页查询周期购spu表")
    @PostMapping("/page")
    public BaseResponse<BuyCycleGoodsPageResponse> getPage(@RequestBody @Valid BuyCycleGoodsPageRequest pageReq) {
        pageReq.putSort("createTime", "desc");
        return buyCycleGoodsQueryProvider.page(pageReq);
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
        BaseResponse<GoodsInfoByGoodsIdresponse> byGoodsId = goodsInfoQueryProvider.getByGoodsId(DistributionGoodsChangeRequest.builder()
                .goodsId(goodsId)
                .showProviderInfoFlag(Boolean.TRUE)
                .showVendibilityFlag(Boolean.TRUE)
                .showSpecFlag(Boolean.TRUE)
                .showDeleteFlag(Boolean.TRUE)
                .build());
        List<GoodsInfoVO> goodsInfoVOList = byGoodsId.getContext().getGoodsInfoVOList();
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
}
