package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * PointsGoodsSalesModifyRequest
 * 积分商品增加销量Request
 * @author lvzhenwei
 * @date 2019/5/29 10:35
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsSalesModifyRequest extends BaseRequest {

    private static final long serialVersionUID = -215418455973475366L;

    /**
     * 积分商品id
     */
    @Schema(description = "积分商品id")
    private String pointsGoodsId;

    /**
     * 商品销量
     */
    @Schema(description = "商品销量")
    private Long salesNum;
}
