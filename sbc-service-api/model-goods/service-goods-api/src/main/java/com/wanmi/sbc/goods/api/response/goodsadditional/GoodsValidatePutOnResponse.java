package com.wanmi.sbc.goods.api.response.goodsadditional;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品上下架校验
 * @author  wur
 * @date: 2021/6/8 16:28
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsValidatePutOnResponse extends BasicResponse {

    /**
     * 是否验证通过
     */
    @Schema(description = "是否验证通过")
    private Boolean validatePass;

}
