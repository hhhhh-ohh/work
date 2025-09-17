package com.wanmi.sbc.goods.api.response.linkedmall;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ThirdPlatformGoodsDelResponse extends BasicResponse {

    private static final long serialVersionUID = 2585450399748912158L;

    @Schema(description = "商品Skuid")
    private List<String> goodsInfoIds;

    @Schema(description = "商品库id")
    private List<String> standardIds;

    @Schema(description = "删除的商品库id")
    private List<String> delStandardIds;
}
