package com.wanmi.sbc.order.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dyt
 * @className CommunityTradeCountVO
 * @description 社区团购跟团统计信息
 * @date 2023/8/9 11:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class CommunityTradeCountVO implements Serializable {

    private static final long serialVersionUID = -1501379562459844656L;

    @Schema(description = "活动ID")
    private String activityId;

    @Schema(description = "已付款订单数")
    private Long payOrderNum;
}
