package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 根据拼团活动ID删除拼团活动商品-request对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class GrouponGoodsInfoDeleteByGrouponActivityIdRequest extends BaseRequest {

    private static final long serialVersionUID = 4449911275417136247L;

    /**
     * 拼团活动ID
     */
    @Schema(description = "拼团活动ID")
    @NotBlank
    private String grouponActivityId;

}
