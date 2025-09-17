package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author baijianhzong
 * @dateTime 2018/11/1 下午4:26
 *
 * 商品限售校验请求的实体
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsRestrictedValidateVO extends BasicResponse {

    private static final long serialVersionUID = 4293196980549267670L;

    /**
     * goodsInfoId
     */
    @Schema(description = "goodsInfoId")
    private String skuId;

    /**
     * 购买的数量
     */
    @Schema(description = "购买的数量")
    private Long num;
}
