package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:49 2018/12/13
 * @Description: 店铺商品模板
 */
@Schema
@Data
public class StoreGoodsTabDTO implements Serializable {

    private static final long serialVersionUID = -4305343922603844025L;

    /**
     * 店铺分类标识
     */
    @Schema(description = "店铺分类标识")
    private Long tabId;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识")
    private Long storeId;

    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    private String tabName;


    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 默认标记
     */
    @Schema(description = "默认标记，0: 否, 1: 是")
    private DefaultFlag isDefault;

}
