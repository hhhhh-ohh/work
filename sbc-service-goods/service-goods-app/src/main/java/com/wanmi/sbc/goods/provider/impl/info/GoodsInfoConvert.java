package com.wanmi.sbc.goods.provider.impl.info;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoDetailByGoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewBySkuNoResponse;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoDetailResponse;
import com.wanmi.sbc.goods.info.reponse.GoodsInfoEditResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: wanggang
 * @createDate: 2018/11/8 15:15
 * @version: 1.0
 */
public class GoodsInfoConvert {

    private GoodsInfoConvert(){

    }

    /**
     * GoodsInfoEditResponse 对象 转换成 GoodsInfoViewByIdResponse 对象
     * @param editResponse
     * @return GoodsInfoViewByIdResponse 对象
     */
    public static GoodsInfoViewByIdResponse toGoodsInfoViewByGoodsInfoEditResponse(GoodsInfoEditResponse editResponse){
        GoodsInfoViewByIdResponse response = new GoodsInfoViewByIdResponse();
        response.setGoodsInfo(new GoodsInfoVO());
        KsBeanUtil.copyPropertiesThird(editResponse.getGoodsInfo(), response.getGoodsInfo());

        if (Objects.nonNull(editResponse.getGoods())){
            response.setGoods(new GoodsVO());
            KsBeanUtil.copyPropertiesThird(editResponse.getGoods(), response.getGoods());
        }

        if (Objects.nonNull(editResponse.getGoodsAudit())){
            response.setGoodsAuditVO(new GoodsAuditVO());
            KsBeanUtil.copyPropertiesThird(editResponse.getGoodsAudit(), response.getGoodsAuditVO());
        }

        response.setGoodsSpecs(KsBeanUtil.convertList(editResponse.getGoodsSpecs(), GoodsSpecVO.class));
        response.setGoodsSpecDetails(KsBeanUtil.convertList(editResponse.getGoodsSpecDetails(), GoodsSpecDetailVO.class));
        response.setImages(editResponse.getImages());

        if (CollectionUtils.isNotEmpty(editResponse.getGoodsLevelPrices())) {
            response.setGoodsLevelPrices(editResponse.getGoodsLevelPrices());
        }
        if (CollectionUtils.isNotEmpty(editResponse.getGoodsCustomerPrices())) {
            response.setGoodsCustomerPrices(editResponse.getGoodsCustomerPrices());
        }
        if (CollectionUtils.isNotEmpty(editResponse.getGoodsIntervalPrices())) {
            response.setGoodsIntervalPrices(editResponse.getGoodsIntervalPrices());
        }
        return response;
    }


    /**
     * GoodsInfoDetailResponse 对象 转换成 GoodsInfoDetailByGoodsInfoResponse 对象
     * @param goodsInfoDetailResponse
     * @return
     */
    public static GoodsInfoDetailByGoodsInfoResponse toGoodsInfoDetailByGoodsInfoResponse(GoodsInfoDetailResponse goodsInfoDetailResponse){
        GoodsInfoDetailByGoodsInfoResponse goodsInfoDetailByGoodsInfoResponse = new GoodsInfoDetailByGoodsInfoResponse();
        KsBeanUtil.copyPropertiesThird(goodsInfoDetailResponse, goodsInfoDetailByGoodsInfoResponse);
        GoodsInfoVO goodsInfoVO = new GoodsInfoVO();
        GoodsInfoSaveVO goodsInfo = goodsInfoDetailResponse.getGoodsInfo();
        KsBeanUtil.copyPropertiesThird(goodsInfo, goodsInfoVO);
        goodsInfoVO.setMarketingLabels(goodsInfo.getMarketingLabels().stream().map(marketingLabel -> {
            MarketingLabelVO marketingLabelVO = new MarketingLabelVO();
            KsBeanUtil.copyPropertiesThird(marketingLabel, marketingLabelVO);
            return marketingLabelVO;
        }).collect(Collectors.toList()));
        goodsInfoDetailByGoodsInfoResponse.setGoodsInfo(goodsInfoVO);
        GoodsVO goodsVO = new GoodsVO();
        KsBeanUtil.copyPropertiesThird(goodsInfoDetailResponse.getGoods(), goodsVO);
        goodsInfoDetailByGoodsInfoResponse.setGoods(goodsVO);
        goodsInfoDetailByGoodsInfoResponse.setGoodsPropDetailRels(KsBeanUtil.convertList(goodsInfoDetailResponse.getGoodsPropDetailRels(), GoodsPropDetailRelVO.class));
        goodsInfoDetailByGoodsInfoResponse.setGoodsSpecs(KsBeanUtil.convertList(goodsInfoDetailResponse.getGoodsSpecs(), GoodsSpecVO.class));
        goodsInfoDetailByGoodsInfoResponse.setGoodsSpecDetails(KsBeanUtil.convertList(goodsInfoDetailResponse.getGoodsSpecDetails(), GoodsSpecDetailVO.class));
        goodsInfoDetailByGoodsInfoResponse.setGoodsLevelPrices(KsBeanUtil.convertList(goodsInfoDetailResponse.getGoodsLevelPrices(), GoodsLevelPriceVO.class));
        goodsInfoDetailByGoodsInfoResponse.setGoodsCustomerPrices(KsBeanUtil.convertList(goodsInfoDetailResponse.getGoodsCustomerPrices(), GoodsCustomerPriceVO.class));
        goodsInfoDetailByGoodsInfoResponse.setGoodsIntervalPrices(KsBeanUtil.convertList(goodsInfoDetailResponse.getGoodsIntervalPrices(), GoodsIntervalPriceVO.class));
        goodsInfoDetailByGoodsInfoResponse.setImages(KsBeanUtil.convertList(goodsInfoDetailResponse.getImages(), GoodsImageVO.class));
        return goodsInfoDetailByGoodsInfoResponse;
    }

    /**
    *
     * GoodsInfoEditResponse 对象 转换成 GoodsInfoViewBySkuNoResponse 对象
     * @author  wur
     * @date: 2021/6/8 20:12
     * @param editResponse GoodsInfoEditResponse对象
     * @return GoodsInfoViewByIdResponse 对象
     **/
    public static GoodsInfoViewBySkuNoResponse toGoodsInfoViewsByGoodsInfoEditResponse(GoodsInfoEditResponse editResponse){
        GoodsInfoViewBySkuNoResponse response = new GoodsInfoViewBySkuNoResponse();
        if(Objects.nonNull(editResponse.getGoodsInfo())) {
            response.setGoodsInfo(new GoodsInfoVO());
            KsBeanUtil.copyPropertiesThird(editResponse.getGoodsInfo(), response.getGoodsInfo());
            response.setGoods(new GoodsVO());
            KsBeanUtil.copyPropertiesThird(editResponse.getGoods(), response.getGoods());
        }
        return response;
    }
}
