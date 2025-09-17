package com.wanmi.sbc.empower.api.request.deliveryrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>达达配送记录查询配送费参数</p>
 *
 * @author zhangwenchang
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaReasonListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "达达商户编号")
    private String sourceId;
}