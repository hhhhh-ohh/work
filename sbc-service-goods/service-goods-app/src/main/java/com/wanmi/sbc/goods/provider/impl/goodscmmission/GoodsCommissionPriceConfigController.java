package com.wanmi.sbc.goods.provider.impl.goodscmmission;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionPriceConfigProvider;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigStatusUpdateRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionPriceConfigUpdateRequest;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionPriceConfigQueryResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoSaveDTO;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;
import com.wanmi.sbc.goods.bean.vo.GoodsCommissionConfigVO;
import com.wanmi.sbc.goods.bean.vo.GoodsCommissionPriceConfigVO;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root.GoodsCommissionPriceConfig;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceConfigService;
import com.wanmi.sbc.goods.goodscommissionpriceconfig.service.GoodsCommissionPriceService;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.service.GoodsInfoService;
import io.seata.common.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description 商品代销智能设价加价比例设置
 * @author  wur
 * @date: 2021/9/10 16:17
 **/
@RestController
@Validated
public class GoodsCommissionPriceConfigController implements GoodsCommissionPriceConfigProvider {

    @Autowired private GoodsCommissionPriceConfigService goodsCommissionPriceConfigService;

    @Autowired private GoodsCommissionPriceService goodsCommissionPriceService;

    @Autowired private GoodsInfoService goodsInfoService;

    @Override
    public BaseResponse update(@Valid GoodsCommissionPriceConfigUpdateRequest goodsCommissionConfigUpdateRequest) {
        goodsCommissionPriceConfigService.update(goodsCommissionConfigUpdateRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsCommissionPriceConfigQueryResponse> query(@Valid GoodsCommissionPriceConfigQueryRequest goodsCommissionPriceConfigQueryRequest) {
        if (CommissionPriceTargetType.SKU.toValue() == goodsCommissionPriceConfigQueryRequest.getTargetType().toValue()) {
            goodsCommissionPriceConfigQueryRequest.setEnableStatus(EnableStatus.ENABLE);
        }
        List<GoodsCommissionPriceConfig> commissionPriceConfigList = goodsCommissionPriceConfigService.query(goodsCommissionPriceConfigQueryRequest);
        List<GoodsCommissionPriceConfigVO> commissionPriceConfigVOList = KsBeanUtil.convertList(commissionPriceConfigList, GoodsCommissionPriceConfigVO.class);
        // 如果是SKU根据 商品类目获取
        if (CommissionPriceTargetType.SKU.toValue() == goodsCommissionPriceConfigQueryRequest.getTargetType().toValue()) {
            List<String> targetIdList = commissionPriceConfigList.stream().map(GoodsCommissionPriceConfig :: getTargetId).collect(Collectors.toList());
            List<String> skuIdList = new ArrayList<>();
            if (StringUtils.isNotBlank(goodsCommissionPriceConfigQueryRequest.getTargetId())
                    && !targetIdList.contains(goodsCommissionPriceConfigQueryRequest.getTargetId())) {
                skuIdList.add(goodsCommissionPriceConfigQueryRequest.getTargetId());
            }
            if (CollectionUtils.isNotEmpty(goodsCommissionPriceConfigQueryRequest.getTargetIdList())) {
                skuIdList = goodsCommissionPriceConfigQueryRequest.getTargetIdList().stream().filter(item -> !targetIdList.contains(item)).collect(Collectors.toList());
            }
            if (CollectionUtils.isNotEmpty(skuIdList)) {
                List<GoodsInfoSaveDTO> goodsInfoList = KsBeanUtil.convertList(goodsInfoService.findByIds(skuIdList), GoodsInfoSaveDTO.class);
                goodsInfoList.forEach(goodsInfo -> {
                    GoodsCommissionPriceConfig priceConfig = goodsCommissionPriceService.queryByGoodsInfo(goodsCommissionPriceConfigQueryRequest.getBaseStoreId(), goodsInfo);
                    if (Objects.nonNull(priceConfig)) {
                        GoodsCommissionPriceConfigVO priceConfigVO = new GoodsCommissionPriceConfigVO();
                        KsBeanUtil.copyPropertiesThird(priceConfig, priceConfigVO);
                        priceConfigVO.setTargetId(goodsInfo.getGoodsInfoId());
                        priceConfigVO.setId(null);
                        commissionPriceConfigVOList.add(priceConfigVO);
                    }
                });
            }

        }
        return BaseResponse.success(GoodsCommissionPriceConfigQueryResponse.builder().commissionPriceConfigVOList(commissionPriceConfigVOList).build());
    }

    @Override
    public BaseResponse updateStatus(@Valid GoodsCommissionPriceConfigStatusUpdateRequest commissionPriceConfigStatusUpdateRequest) {
        goodsCommissionPriceConfigService.updateStatus(commissionPriceConfigStatusUpdateRequest);
        return BaseResponse.SUCCESSFUL();
    }
}

