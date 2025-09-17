package com.wanmi.sbc.vas.api.request.sellplatform.goods;

import com.wanmi.sbc.common.enums.EditStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description  商品更新
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformUpdateGoodsRequest extends SellPlatformAddGoodsRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 交易组件平台内部商品ID，与out_product_id二选一
     */
    @Schema(description = "交易组件平台内部商品ID，与goodsId二选一")
    private String product_id;

    /**
     * 更新的字段是否要审核
     */
    private Boolean auditUpdate;

    private EditStatus editStatus;

}
