package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicImportCheckResponse
 * @description
 * @date 2022/2/4 12:45 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicImportCheckResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 卡号
     */
    @Schema(description = "卡号")
    private List<String> numbers;

    /**
     * 卡密
     */
    @Schema(description = "卡密")
    private List<String> passwords;

    /**
     * 优惠码
     */
    @Schema(description = "优惠码")
    private List<String> codes;
}
