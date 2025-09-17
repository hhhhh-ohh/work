package com.wanmi.sbc.goods.provider.impl.price;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceByCustomerIdRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceByGoodsAndSkuRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceRequest;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByGoodsAndSkuResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.price.service.GoodsIntervalPriceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.wanmi.sbc.goods.provider.impl.intervalprice.GoodsIntervalPriceController
 *
 * @author lipeng
 * @dateTime 2018/11/7 下午3:20
 */
@RestController
@Validated
public class GoodsIntervalPriceController implements GoodsIntervalPriceProvider {

    @Autowired
    private GoodsIntervalPriceService goodsIntervalPriceService;

    /**
     * 为每个SKU填充区间价范围值intervalMinPrice,intervalMaxPrice,intervalPriceIds，并返回相应区间价结果
     *
     * @param request {@link GoodsIntervalPriceRequest}
     * @return 区间价结果 {@link GoodsIntervalPriceResponse}
     */
    @Override
    public BaseResponse<GoodsIntervalPriceResponse> put(
            @RequestBody @Valid GoodsIntervalPriceRequest request) {
        GoodsIntervalPriceResponse goodsIntervalPriceResponse = new GoodsIntervalPriceResponse();
        List<GoodsInfoSaveVO> goodsInfoList = KsBeanUtil.convert(request.getGoodsInfoDTOList(), GoodsInfoSaveVO.class);

        List<GoodsIntervalPriceVO> goodsIntervalPriceList = goodsIntervalPriceService.putIntervalPrice(goodsInfoList, null);
        goodsIntervalPriceResponse.setGoodsInfoVOList(KsBeanUtil.convert(goodsInfoList, GoodsInfoVO.class));
        if (CollectionUtils.isEmpty(goodsIntervalPriceList)) {
            return BaseResponse.success(goodsIntervalPriceResponse);
        }
        goodsIntervalPriceResponse.setGoodsIntervalPriceVOList(goodsIntervalPriceList);
        return BaseResponse.success(goodsIntervalPriceResponse);
    }

    /**
     * 为每个SKU填充区间价范围值intervalMinPrice,intervalMaxPrice,intervalPriceIds，并返回相应区间价结果
     *
     * @param request {@link GoodsIntervalPriceByCustomerIdRequest}
     * @return 区间价结果 {@link GoodsIntervalPriceResponse}
     */
    @Override
    public BaseResponse<GoodsIntervalPriceByCustomerIdResponse> putByCustomerId(
            @RequestBody @Valid GoodsIntervalPriceByCustomerIdRequest request) {
        return goodsIntervalPriceService.putByCustomerId(request);
    }

    /**
     * 为每个SKU和SPU填充区间价范围值intervalMinPrice,intervalMaxPrice,intervalPriceIds，并返回相应区间价结果
     *
     * @param request {@link GoodsIntervalPriceByGoodsAndSkuRequest}
     * @return 区间价结果 {@link GoodsIntervalPriceByGoodsAndSkuResponse}
     */
    @Override
    public BaseResponse<GoodsIntervalPriceByGoodsAndSkuResponse> putGoodsAndSku (
            @RequestBody @Valid GoodsIntervalPriceByGoodsAndSkuRequest request) {
        GoodsIntervalPriceByGoodsAndSkuResponse response = new GoodsIntervalPriceByGoodsAndSkuResponse();
        List<GoodsInfoSaveVO> goodsInfoList = KsBeanUtil.convert(request.getGoodsInfoDTOList(), GoodsInfoSaveVO.class);
        List<GoodsSaveVO> goods = KsBeanUtil.convert(request.getGoodsDTOList(), GoodsSaveVO.class);
        if(CollectionUtils.isEmpty(goodsInfoList) && CollectionUtils.isEmpty(goods)){
            response.setGoodsInfoVOList(Collections.emptyList());
            response.setGoodsDTOList(Collections.emptyList());
            response.setGoodsIntervalPriceVOList(Collections.emptyList());
            return BaseResponse.success(response);
        }

        //提取店铺
        Map<Long, CommonLevelVO> levelMap = null;
        if(StringUtils.isNotBlank(request.getCustomerId())) {
            List<Long> storeIds = goods.stream()
                    .filter(spu -> Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(spu.getPriceType()))
                    .map(GoodsSaveVO::getStoreId)
                    .filter(id -> id != null && id > 0)
                    .distinct().collect(Collectors.toList());
            levelMap = goodsIntervalPriceService.getLevelMap(request.getCustomerId(), storeIds);
        }

        List<GoodsIntervalPriceVO> goodsIntervalPriceList = goodsIntervalPriceService.putIntervalPrice(goodsInfoList, levelMap);
        goodsIntervalPriceList.addAll(goodsIntervalPriceService.putGoodsIntervalPrice(goods, levelMap));

        response.setGoodsInfoVOList(KsBeanUtil.convert(goodsInfoList, GoodsInfoVO.class));
        response.setGoodsDTOList(KsBeanUtil.convert(goods, GoodsVO.class));
        response.setGoodsIntervalPriceVOList(goodsIntervalPriceList);
        return BaseResponse.success(response);
    }
}
