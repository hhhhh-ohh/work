package com.wanmi.sbc.marketing.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.enums.GoodsEditFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:16
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsEditSynRequest extends BaseRequest {

    private static final long serialVersionUID = -1073643849079919962L;

    @Schema(description = "spuId")
    private List<String> goodsIds;

    @Schema(description = "skuId")
    private List<String> goodsInfoIds;

    @Schema(description = "商家Id")
    private Long storeId;

    /**
     * 商品变更类型
     */
    @Schema(description = "商品变更类型")
    private GoodsEditFlag flag;

    @Schema(description = "是否是供应商商家商品")
    private Boolean isProvider = Boolean.FALSE;

}
