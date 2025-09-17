package com.wanmi.sbc.goods.api.request.pointsgoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>批量删除积分商品表请求参数</p>
 *
 * @author yang
 * @date 2019-05-07 15:01:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsGoodsDelByIdListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量删除-积分商品idList
     */
    @Schema(description = "批量删除-积分商品idList")
    @NotEmpty
    private List<String> pointsGoodsIdList;
}