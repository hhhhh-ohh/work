package com.wanmi.sbc.elastic.api.response.pointsgoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.PointsGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>积分商品表分页结果</p>
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsPointsGoodsPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 积分商品表分页结果
     */
    @Schema(description = "积分商品表分页结果")
    private MicroServicePage<PointsGoodsVO> pointsGoodsVOPage;
}
