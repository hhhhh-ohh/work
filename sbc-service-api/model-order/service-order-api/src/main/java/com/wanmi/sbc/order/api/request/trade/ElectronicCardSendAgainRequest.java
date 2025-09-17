package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicCardSendAgainRequest
 * @description
 * @date 2022/1/27 4:13 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardSendAgainRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 记录id
     */
    @Schema(description = "记录id")
    @NotEmpty
    private List<String> recordIds;
}
