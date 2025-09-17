package com.wanmi.sbc.goods.info.service;

import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.info.model.entity.GoodsInfoCartParam;

import java.util.List;

/**
 * @author zhanggaolei
 */
public interface GoodsInfoCartInterface {

    /**
     * 获取sku数据
     * @param goodsInfoIds
     * @return
     */
    List<GoodsInfoSaveVO> getGoodsInfoList(List<String> goodsInfoIds);

    /**
     * 获取spu数据
     * @param goodsInfos
     * @return
     */
    List<GoodsSaveVO> getGoodsList(List<GoodsInfoSaveVO> goodsInfos);

    /**
     * 获取规格数据
     * @param goodsInfos
     * @return
     */
    List<GoodsInfoSpecDetailRelVO> getGoodsInfoSpecDetailList(List<GoodsInfoSaveVO> goodsInfos);

    /**
     * 获取店铺分类数据
     * @param goodsInfos
     * @return
     */
    List<StoreCateGoodsRelaVO> getStoreCateGoodsList(List<GoodsInfoSaveVO> goodsInfos);

    /**
     * 获取区间价
     * @param goodsInfos
     * @return
     */
    List<GoodsIntervalPriceVO> getIntervalPriceList(List<GoodsInfoSaveVO> goodsInfos);

    /**
     * 获取限售记录
     * @param goodsInfos
     * @param storeId    门店ID，O2O模式有值
     * @return
     */
    List<GoodsRestrictedPurchaseVO> getGoodsRestrictedInfo(List<GoodsInfoSaveVO> goodsInfos, CustomerVO customer, Long storeId);

    /**
     * 拼装获取参数
     * @param param
     * @return
     */
    List<GoodsInfoBaseVO> setAttributes(GoodsInfoCartParam param);
}
