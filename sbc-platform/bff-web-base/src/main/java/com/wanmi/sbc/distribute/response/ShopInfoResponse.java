package com.wanmi.sbc.distribute.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员中心返回数据
 * Created by CHENLI on 2017/7/17.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopInfoResponse extends BasicResponse {

    /**
     * 精选店铺名称
     */
    @Schema(description = "精选店铺名称")
    private String shopName;

    /**
     * 精选店铺头像
     */
    @Schema(description = "精选店铺头像")
    private String headImg;
}
