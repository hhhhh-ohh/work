package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wanggang
 * @createDate: 2018/12/3 11:37
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ReturnOrderModifyAutoAuditRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "天")
    private Integer day;
}
