package com.wanmi.sbc.vas.bean.vo.sellplatform;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author wur
 * @className ChannelsGoodsInfoAttrVO
 * @description 商品属性
 * @date 2022/4/1 19:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class SellPlatformGoodsInfoAttrVO implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 商品属性项
     */
    @Schema(description = "商品属性项（自定义），字符类型，最长不超过40")
    @NotEmpty
    private String attr_key;
    /**
     * 属性值
     */
    @Schema(description = "销售属性value（自定义），字符类型，最长不超过40，相同key下不能超过100个不同value")
    private String attr_value;

}