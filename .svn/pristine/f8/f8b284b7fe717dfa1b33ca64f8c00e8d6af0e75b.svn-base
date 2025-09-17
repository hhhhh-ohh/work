package com.wanmi.sbc.order.api.request.returnorder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 根据结束时间查询退单列表请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ReturnOrderListByEndDateRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    /**
     * 偏移始点
     */
    @Schema(description = "偏移始点")
    @NotNull
    private Integer start;

    /**
     * 偏移终点
     */
    @Schema(description = "偏移终点")
    @NotNull
    private Integer end;

    /**
     * 退货流程状态
     */
    @Schema(description = "退单流程状态")
    private ReturnFlowState returnFlowState;
}
