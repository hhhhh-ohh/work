package com.wanmi.sbc.goods.common;

import com.wanmi.sbc.goods.api.request.common.GoodsCommonBatchAddRequest;
import com.wanmi.sbc.goods.api.request.common.InfoForPurchaseRequest;
import com.wanmi.sbc.goods.api.response.common.GoodsInfoForPurchaseResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsSaveDTO;
import com.wanmi.sbc.goods.info.model.root.Goods;

import java.util.List;

/***
 * 商品公共服务接口服务
 * @className GoodsCommonServiceInterface
 * @author zhengyang
 * @date 2021/8/16 16:38
 **/
public interface GoodsCommonServiceInterface {
    /**
     * 批量导入商品数据
     *
     * @param request 商品批量信息
     * @return 批量新增的skuId
     */
    List<String> batchAdd(GoodsCommonBatchAddRequest request);

    /**
     * 新增/编辑操作中，商品审核状态
     *
     * @param goods 商品
     */
    void setCheckState(GoodsSaveDTO goods);

    GoodsInfoForPurchaseResponse queryInfoForPurchase(InfoForPurchaseRequest request);

    /**
     * 递归方式，获取全局唯一SPU编码
     *
     * @return Spu编码
     */
    String getSpuNoByUnique();

    /**
     * 获取Spu编码
     *
     * @return Spu编码
     */
    String getSpuNo();

    /**
     * 递归方式，获取全局唯一SPU编码
     *
     * @return Sku编码
     */
    String getSkuNoByUnique();

    /**
     * 获取Sku编码
     *
     * @return Sku编码
     */
    String getSkuNo();
}
