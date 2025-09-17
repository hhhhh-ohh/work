package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PointsGoodsCloseRequest
 * @description 关闭积分商品活动请求体
 * @date 2021/6/23 2:26 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsCloseRequest extends BaseRequest {

    private static final long serialVersionUID = -5020229632771906545L;

    @Schema(description = "积分商品id")
    private String pointsGoodsId;
}
