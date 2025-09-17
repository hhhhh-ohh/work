package com.wanmi.sbc.goods.info.model.entity;

import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoCartParam
 * @description TODO
 * @date 2022/1/4 2:51 下午
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoCartParam {

    List<GoodsInfoSaveVO> goodsInfoList;

    List<GoodsSaveVO> goodsList;

    List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailList;

    List<StoreCateGoodsRelaVO> storeCateGoodsRelList;

    List<GoodsIntervalPriceVO> goodsIntervalPriceList;

    List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseList;

    CustomerVO customer;
}
