package com.wanmi.sbc.pointsmall.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 积分商城会员数据
 * Created by YINXIANZHI on 2019/05/10.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerInfoResponse extends BasicResponse {

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;

    /**
     * 可用积分
     */
    @Schema(description = "可用积分")
    private Long pointsAvailable;

}
