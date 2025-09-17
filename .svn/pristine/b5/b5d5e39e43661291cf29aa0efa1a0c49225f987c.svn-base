package com.wanmi.sbc.empower.api.request.sellplatform.goods;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.bean.vo.sellplatform.goods.PlatformGoodsInfoNoAuditVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description  商品免审信息更新
 * @author  wur
 * @date: 2022/4/19 9:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUpdateNoAuditRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商家自定义商品ID，与product_id二选一
     */
    @Schema(description = "商家自定义商品ID，与product_id二选一")
    private String out_product_id;

    /**
     * 交易组件平台内部商品ID，与out_product_id二选一
     */
    @Schema(description = "交易组件平台内部商品ID，与out_product_id二选一")
    private String product_id;

    /**
     * 免审商品信息
     */
    @Schema(description = "免审商品信息")
    private List<PlatformGoodsInfoNoAuditVO> skus;

}
