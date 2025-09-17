package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>抢购商品表修改参数</p>
 *
 * @author xufeng
 * @date 2022-02-10 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashPromotionActivityModifyStatusRequest extends GoodsBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 活动名称
     */
    @Schema(description = "activityId")
    @NotNull
    private String activityId;

    /**
     * 状态 0:开始 1:暂停
     */
    @NotNull
    private Integer status;

    @Override
    public void checkParam() {
        if (status!=0&&status!=1){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}