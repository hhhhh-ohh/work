package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicSendRecordModifyRequest
 * @description
 * @date 2022/2/11 3:33 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordModifyRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    @Schema(description = "记录id")
    private List<String> recordIds;
}
