package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BossGoodsOutOfStockShowResponse extends BasicResponse {

    private static final long serialVersionUID = 482786291371551904L;

    /**
     * true:展示 false:不展示
     */
    @Schema(description = "无货商品是否展示")
    private boolean outOfStockShow;
}
