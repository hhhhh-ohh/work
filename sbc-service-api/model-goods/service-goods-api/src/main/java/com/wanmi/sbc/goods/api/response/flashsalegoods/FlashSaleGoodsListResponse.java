package com.wanmi.sbc.goods.api.response.flashsalegoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>抢购商品表列表结果</p>
 * @author bob
 * @date 2019-06-11 14:54:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 抢购商品表列表结果
     */
    @Schema(description = "抢购商品表列表结果")
    private List<FlashSaleGoodsVO> flashSaleGoodsVOList;
}
