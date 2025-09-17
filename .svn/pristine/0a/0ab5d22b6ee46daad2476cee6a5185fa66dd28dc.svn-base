package com.wanmi.sbc.marketing.api.response.market;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsGoodsInfoDetailVO;
import com.wanmi.sbc.marketing.bean.vo.SuitsRelationGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingMoreGoodsInfoResponse extends BasicResponse {

    /**
     * 组合套餐活动
     */
    private MarketingSuitsGoodsInfoDetailVO marketingSuitsGoodsInfoDetailVO;


    /**
     * 组合套餐关联商品详情
     */
    private List<SuitsRelationGoodsInfoVO> suitsRelationGoodsInfoVOList;

}
