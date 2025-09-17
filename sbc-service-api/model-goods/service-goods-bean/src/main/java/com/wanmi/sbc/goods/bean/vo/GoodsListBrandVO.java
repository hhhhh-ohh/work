package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品品牌实体类
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class GoodsListBrandVO extends BasicResponse {

    private static final long serialVersionUID = -7003826536130557348L;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 拼音
     */
    @Schema(description = "拼音")
    private String pinYin;

    /**
     * 品牌logo
     */
    @Schema(description = "品牌logo")
    private String logo;

    /**
     * 首字母
     */
    @Schema(description = "首字母")
    private String first;

    /**
     * 是否推荐品牌
     */
    @Schema(description = "是否推荐品牌")
    private DefaultFlag recommendFlag;

    /**
     * 品牌排序
     */
    @Schema(description = "品牌排序")
    private Long brandSort;

    /**
     * 品牌别名
     */
    @Schema(description = "品牌别名")
    private String nickName;

}
