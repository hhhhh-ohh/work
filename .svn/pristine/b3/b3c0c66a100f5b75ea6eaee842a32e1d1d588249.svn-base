package com.wanmi.sbc.goods.provider.impl.standard;

import com.wanmi.sbc.goods.api.request.standard.StandardGoodsAddRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsBaseRequest;
import com.wanmi.sbc.goods.api.request.standard.StandardGoodsModifyRequest;
import com.wanmi.sbc.goods.standard.request.StandardSaveRequest;

import java.util.Objects;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-08 16:24
 */
public class StandardConvert {

    /**
     * 标准商品添加请求对象转化为服务的保存对象
     * @param addRequest {@link StandardGoodsAddRequest}
     * @return
     */
    protected static StandardSaveRequest convertAddRequest2saveRequest(StandardGoodsAddRequest addRequest){

        StandardSaveRequest saveRequest = new StandardSaveRequest();

        converBaseReq2SaveReq(addRequest,saveRequest);

        return saveRequest;
    }

    /**
     * 标准商品修改请求对象转化为服务的保存对象
     * @param modifyRequest {@link StandardGoodsAddRequest}
     * @return
     */
    protected static StandardSaveRequest convertModifyRequest2saveRequest(StandardGoodsModifyRequest modifyRequest){

        StandardSaveRequest saveRequest = new StandardSaveRequest();

        converBaseReq2SaveReq(modifyRequest,saveRequest);

        return saveRequest;
    }

    /**
     * 标准商品基类对象转化为服务的保存对象
     * @param baseRequest
     * @param saveRequest
     */
    private static void converBaseReq2SaveReq(StandardGoodsBaseRequest baseRequest,StandardSaveRequest saveRequest){
        if (Objects.nonNull(baseRequest.getGoods())) {
            saveRequest.setGoods(baseRequest.getGoods());
        }
        if (Objects.nonNull(baseRequest.getGoodsInfos())) {
            saveRequest.setGoodsInfos(baseRequest.getGoodsInfos());
        }
        if (Objects.nonNull(baseRequest.getGoodsPropDetailRels())) {
            saveRequest.setGoodsPropDetailRels(baseRequest.getGoodsPropDetailRels());
        }
        if (Objects.nonNull(baseRequest.getGoodsSpecs())) {
            saveRequest.setGoodsSpecs(baseRequest.getGoodsSpecs());
        }
        if (Objects.nonNull(baseRequest.getGoodsSpecDetails())) {
            saveRequest.setGoodsSpecDetails(baseRequest.getGoodsSpecDetails());
        }
        if (Objects.nonNull(baseRequest.getImages())) {
            saveRequest.setImages(baseRequest.getImages());
        }
    }
}
