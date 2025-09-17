package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.request.goods.GoodsDeleteByIdsRequest;
import com.wanmi.sbc.goods.api.response.goods.GoodsAddResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsDeleteResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsModifyInfoResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.request.GoodsSaveRequest;

/***
 * 商品基础信息维护接口
 * @className GoodsBaseInterface
 * @author zhengyang
 * @date 2021/7/6 15:01
 **/
public interface GoodsBaseInterface {

    /**
     * 1.校验商品分类、商品品牌、是否存在
     * 2.如果是S2B，校验签约分类、签约品牌、店铺分类是否正确
     *
     * @param goods 商品信息
     * @param type 0-新增；1-修改
     */
    void checkBasic(GoodsSaveDTO goods,int type);

    /***
     * 商品新增
     * @param saveRequest           商品保存对象
     * @return                      保存商品主键
     * @throws SbcRuntimeException   新增业务异常
     */
    GoodsAddResponse add(GoodsSaveRequest saveRequest) throws SbcRuntimeException;

    /**
     * 商品更新
     * @param saveRequest            商品更新请求对象
     * @return                       更新参数
     * @throws SbcRuntimeException   更新业务异常
     */
    GoodsModifyInfoResponse edit(GoodsSaveRequest saveRequest) throws SbcRuntimeException;

    /***
     * 商品删除
     * @param request                   删除请求
     * @return                          删除商品对象
     * @throws SbcRuntimeException      报错信息
     */
    GoodsDeleteResponse delete(GoodsDeleteByIdsRequest request) throws SbcRuntimeException;

    /**
     * 对比商品是否经过修改
     * @param oldGoods 老商品信息
     * @param newGoods 新商品信息
     * @param saveRequest 新商品相关信息
     * @return
     */
    boolean checkGoodsIsAudit(GoodsSaveDTO oldGoods, GoodsSaveDTO newGoods, GoodsSaveRequest saveRequest, GoodsCommissionConfig goodsCommissionConfig,boolean flag);
}
