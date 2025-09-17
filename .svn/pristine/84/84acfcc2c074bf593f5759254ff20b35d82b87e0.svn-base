package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>完善信息页面初始化所需要的数据</p>
 *
 * @author zhangwenchang
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowthValueAndPointResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 完善信息可获得的积分值
     */
    @Schema(description = "完善信息可获得的积分值")
    private Long point;

    /**
     * 完善信息获取积分配置是否开启
     */
    @Schema(description = "完善信息获取积分配置是否开启")
    private Boolean pointFlag;

    /**
     * 完善信息获取成长值配置是否开启
     */
    @Schema(description = "完善信息获取成长值配置是否开启")
    private Boolean growthFlag;

    /**
     * 完善信息可获得的成长值
     */
    @Schema(description = "完善信息可获得的成长值")
    private Long growthValue;
}
