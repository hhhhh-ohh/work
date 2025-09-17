package com.wanmi.sbc.marketing.api.response.plugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-22
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingLevelGoodsListFilterResponse extends BasicResponse {

    private static final long serialVersionUID = 1991675981674315350L;

    @Schema(description = "单品信息列表")
    private List<GoodsInfoVO> goodsInfoVOList;
}
