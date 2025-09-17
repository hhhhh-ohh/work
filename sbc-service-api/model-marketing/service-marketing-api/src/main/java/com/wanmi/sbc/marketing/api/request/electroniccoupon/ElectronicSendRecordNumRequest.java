package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.ElectronicSendRecordNumDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicSendRecordNumRequest
 * @description
 * @date 2022/2/10 11:44 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordNumRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "卡券商品信息")
    @NotEmpty
    private List<ElectronicSendRecordNumDTO> dtos;
}
