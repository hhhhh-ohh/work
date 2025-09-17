package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicImportCheckRequest
 * @description
 * @date 2022/2/4 12:42 上午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicImportCheckRequest extends BaseRequest {
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

    /**
     * 卡券id
     */
    @Schema(description = "卡券id")
    private Long couponId;
}
