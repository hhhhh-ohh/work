package com.wanmi.sbc.goods.api.request.customer;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wanggang
 * @createDate: 2018/11/6 10:08
 * @version: 1.0
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCustomerNumRequest extends BaseRequest {

    private static final long serialVersionUID = 7664687238332575235L;
    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * 会员编号
     */
    @Schema(description = "会员编号")
    private String customerId;

    /**
     * 商品数量
     */
    @Schema(description = "商品数量")
    private Long goodsNum;

    /**
     * 叠加标识
     * 1:在原有基础上叠加
     * 默认:全改
     */
    @Schema(description = "叠加标识，com.wanmi.sbc.goods.bean.enums.AddedFlag")
    private Integer addFlag;
}
