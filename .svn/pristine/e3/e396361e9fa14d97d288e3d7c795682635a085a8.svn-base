package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.ElectronicSendRecordAddDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicSendRecordAddRequest
 * @description 添加发放记录请求
 * @date 2022/2/11 10:48 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordAddRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "发放数据-商品纬度")
    @NotEmpty
    private List<ElectronicSendRecordAddDTO> dtoList;
}
