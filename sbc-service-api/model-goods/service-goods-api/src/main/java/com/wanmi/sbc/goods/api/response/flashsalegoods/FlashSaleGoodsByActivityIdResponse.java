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
 * <p>根据id查询任意（包含已删除）抢购商品表信息response</p>
 * @author xufeng
 * @date 2022-02-10 14:54:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleGoodsByActivityIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 抢购商品表信息
     */
    @Schema(description = "抢购商品表信息")
    private List<FlashSaleGoodsVO> flashSaleGoodsVOs;
}
