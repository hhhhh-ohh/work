package com.wanmi.sbc.empower.api.response.sellplatform.goods;

import com.wanmi.sbc.empower.bean.vo.sellplatform.goods.PlatformWxGoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className ChannelsAddGoodsResponse
 * @description TODO
 * @date 2022/4/1 19:30
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformAddGoodsResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 类目ID
     */
    @Schema(description = "交易组件平台内部商品ID")
    private Integer product_id;

    /**
     * 类目名称
     */
    @Schema(description = "商家自定义商品ID")
    private String out_product_id;

    /**
     * 类目资质
     */
    @Schema(description = "类目资质")
    private List<PlatformWxGoodsInfoVO> skus;

}