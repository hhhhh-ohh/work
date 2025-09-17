package com.wanmi.sbc.goods.api.response.pointsgoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）积分商品表信息response</p>
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 积分商品表信息
     */
    @Schema(description = "积分商品表信息")
    private PointsGoodsVO pointsGoodsVO;
}
