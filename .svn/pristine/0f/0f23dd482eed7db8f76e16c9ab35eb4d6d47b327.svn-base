package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品批量导入参数
 * 增加虚拟goodsId，表示与其他商品相关类的数据关联
 * Created by dyt on 2017/4/11.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchGoodsDTO extends GoodsDTO{

    private static final long serialVersionUID = 1110173610463703552L;

    /**
     * 模拟goodsId
     */
    @Schema(description = "模拟goodsId")
    private String mockGoodsId;

}
