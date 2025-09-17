package com.wanmi.sbc.empower.bean.vo.sellplatform.goods;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className WxChannelsGoodsDescInfoVO
 * @description 商品图文详情信息
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformGoodsDescInfoVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商品详情图文，字符类型，最长不超过2000
     */
    @Schema(description = "商品详情，字符类型，最长不超过2000")
    private Integer desc;
    /**
     * 商品详情图片，图片类型，最多不超过50张
     */
    @Schema(description = "商品详情图片，图片类型，最多不超过50张")
    private List<String> imgs;
}