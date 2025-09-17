package com.wanmi.sbc.vas.api.response.sellplatform.goods;

import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformGoodsInfoVO;

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
public class SellPlatformAddGoodsResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 类目ID
     */
    @Schema(description = "交易组件平台内部商品ID")
    private Integer product_id;

    @Schema(description = "商家自定义商品ID")
    private String out_product_id;

    private List<SellPlatformGoodsInfoVO> skus;

}