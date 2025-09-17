package com.wanmi.sbc.goods.freight.vo;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wur
 * @className FreightPackageGoodsPriceVO
 * @description TODO
 * @date 2022/7/21 18:08
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightPackageGoodsPriceVO {

    List<GoodsInfoVO> goodsInfoList;

    BigDecimal totalAmount;
}