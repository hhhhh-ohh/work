package com.wanmi.sbc.marketing.api.response.preferential;

import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialLevelVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className PreferentialDetailResponse
 * @description 加价购活动信息
 * @date 2022/11/18 14:18
 **/
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PreferentialDetailResponse implements Serializable {

    @Schema(description = "活动主体信息")
    private MarketingVO marketingVO;

    @Schema(description = "活动阶梯信息")
    private List<MarketingPreferentialLevelVO> preferentialLevelList;

    @Schema(description = "活动阶梯商品信息")
    private List<GoodsInfoVO> goodsInfoVOList;
}
