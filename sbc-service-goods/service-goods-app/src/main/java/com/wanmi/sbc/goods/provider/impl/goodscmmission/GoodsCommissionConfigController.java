package com.wanmi.sbc.goods.provider.impl.goodscmmission;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionConfigProvider;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigUpdateRequest;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.GoodsCateThirdCateRelModifyResponse;
import com.wanmi.sbc.goods.api.response.goodscommission.GoodsCommissionConfigQueryResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCommissionConfigVO;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @description   商品代销智能设价加价比例设置
 * @author  wur
 * @date: 2021/9/10 16:16
 **/
@RestController
@Validated
public class GoodsCommissionConfigController implements GoodsCommissionConfigProvider {

    @Autowired private GoodsCommissionConfigService commissionConfigService;

    @Override
    public BaseResponse update(@Valid GoodsCommissionConfigUpdateRequest goodsCommissionConfigUpdateRequest) {
        GoodsCommissionConfigVO commissionConfigVO = KsBeanUtil.convert(goodsCommissionConfigUpdateRequest, GoodsCommissionConfigVO.class);
        commissionConfigVO.setStoreId(goodsCommissionConfigUpdateRequest.getBaseStoreId());
        commissionConfigService.updateStoreId(commissionConfigVO, goodsCommissionConfigUpdateRequest.getUserId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateAddRate(@Valid GoodsCommissionConfigUpdateRequest goodsCommissionConfigUpdateRequest) {
        GoodsCommissionConfigVO commissionConfigVO = KsBeanUtil.convert(goodsCommissionConfigUpdateRequest, GoodsCommissionConfigVO.class);
        commissionConfigVO.setStoreId(goodsCommissionConfigUpdateRequest.getBaseStoreId());
        commissionConfigService.updateAddRate(commissionConfigVO, goodsCommissionConfigUpdateRequest.getUserId());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsCommissionConfigQueryResponse> query(@Valid GoodsCommissionConfigQueryRequest goodsCommissionConfigQueryRequest) {
        GoodsCommissionConfig commissionConfig = commissionConfigService.queryBytStoreId(goodsCommissionConfigQueryRequest.getBaseStoreId());
        return BaseResponse.success(GoodsCommissionConfigQueryResponse
                .builder()
                .commissionConfigVO(commissionConfigService.wrapperVo(commissionConfig))
                .build());
    }
}

