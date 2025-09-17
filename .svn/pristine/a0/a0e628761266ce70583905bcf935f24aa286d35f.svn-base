package com.wanmi.sbc.goods.api.response.buycyclegoodsinfo;

import com.wanmi.sbc.goods.bean.vo.BuyCycleGoodsInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）周期购sku表信息response</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 周期购sku表信息
     */
    @Schema(description = "周期购sku表信息")
    private BuyCycleGoodsInfoVO buyCycleGoodsInfoVO;
}
