package com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName RecommendCateManageUpdateWeightRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/19 15:14
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateManageUpdateWeightRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 类目id
     */
    @Schema(description = "类目id")
    private Long cateId;

    /**
     * 权重
     */
    @Schema(description = "权重")
    private Integer weight;
}
