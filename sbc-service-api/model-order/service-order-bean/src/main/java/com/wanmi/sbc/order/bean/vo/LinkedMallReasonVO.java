package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * linkedMall原因内容
 * Created by jinwei on 14/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class LinkedMallReasonVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 原因id
     */
    @Schema(description = "原因id")
    private Long reasonTextId;

    /**
     * 原因内容
     */
    @Schema(description = "原因内容")
    private String reasonTips;

    /**
     * 是否要求上传凭证
     */
    @Schema(description = "是否要求上传凭证")
    private Boolean proofRequired;

    /**
     * 是否要求留言
     */
    @Schema(description = "是否要求留言")
    private Boolean refundDescRequired;
}
