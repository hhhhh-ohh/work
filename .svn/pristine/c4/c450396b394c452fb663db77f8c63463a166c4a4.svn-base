package com.wanmi.sbc.goods.api.response.goodsrestrictedsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedSaleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售配置修改结果</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedSaleModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的限售配置信息
     */
    @Schema(description = "已修改的限售配置信息")
    private GoodsRestrictedSaleVO goodsRestrictedSaleVO;
}
