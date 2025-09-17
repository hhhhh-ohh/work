package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @discription 商品简易信息，存储redis中
 * @author yangzhen
 * @date 2020/9/8 18:54
 * @param
 * @return
 */
@Schema
@Data
public class GoodsSimpleVO extends BasicResponse {

    private static final long serialVersionUID = -4957513433530520757L;

    /**
     * 商品编号，采用UUID
     */
    @Schema(description = "商品编号，采用UUID")
    private String goodsId;

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    @CanEmpty
    private Long brandId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 商品副标题
     */
    @Schema(description = "商品副标题")
    private String goodsSubtitle;

    /**
     * SPU编码
     */
    @Schema(description = "SPU编码")
    private String goodsNo;

    /**
     * 商品主图
     */
    @Schema(description = "商品主图")
    @CanEmpty
    private String goodsImg;

}
