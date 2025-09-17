package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.GrouponInstanceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by feitingting on 2019/8/12.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GrouponInstanceByActivityIdResponse extends BasicResponse {
    /**
     * 团信息
     */
    @Schema(description = "团信息")
    private GrouponInstanceVO grouponInstance;

    private String customerName;
}
