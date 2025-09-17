package com.wanmi.sbc.marketing.api.response.market;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoResponseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-14 14:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingGetGoodsByIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1728637538606821749L;
    /**
     * 商品信息
     */
    @Schema(description = "商品信息")
    private GoodsInfoResponseVO goodsInfoResponseVO;

}
