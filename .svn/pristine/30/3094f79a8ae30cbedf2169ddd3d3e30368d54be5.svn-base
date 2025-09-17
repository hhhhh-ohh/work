package com.wanmi.sbc.crm.api.request.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName RecommendGoodsManageUpdateWeightRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/19 14:15
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendGoodsManageUpdateWeightRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(description = "主键")
    @Max(9223372036854775807L)
    private Long id;

    /**
     * 主键
     */
    @Schema(description = "主键List")
    private List<Long> ids;

    /**
     * 权重
     */
    @Schema(description = "权重")
    private Integer weight;
}
