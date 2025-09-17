package com.wanmi.sbc.goods.api.response.buycyclegoods;

import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）周期购spu表信息response</p>
 * @author zhanghao
 * @date 2022-10-11 17:48:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 周期购spu表信息
     */
    @Schema(description = "周期购spu表信息")
    private BuyCycleGoodsVO buyCycleGoodsVO;
}
