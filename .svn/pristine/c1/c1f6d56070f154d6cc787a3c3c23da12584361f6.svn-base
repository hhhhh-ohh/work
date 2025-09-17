package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author wur
 * @className GiftCardPageRequest
 * @description 礼品卡分页查询
 * @date 2022/12/8 16:29
 **/
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -2952406761735818859L;
    /**
     * 礼品卡名
     */
    @Schema(description = "礼品卡名")
    private String name;

    /**
     * 适用商品  0：全部商品、1：部分商品、2：指定商品
     * 0：全部 1:按品牌 2：按分类 3：按店铺 4：自定义商品
     */
    @Schema(description = "适用商品  0：全部商品、1：部分商品、2：指定商品")
    private Integer scopeType;

    /**
     * 状态 0：正常 1：已过期
     */
    @Schema(description = "状态 0：正常 1：已过期")
    private Integer status;

    /**
     * 开始面值
     */
    @Schema(description = "开始面值")
    private BigDecimal parBegin;

    /**
     * 结束面值
     */
    @Schema(description = "结束面值")
    private BigDecimal parEnd;

    /**
     * 开始库存
     */
    @Schema(description = "开始库存")
    private Long stockBegin;

    /**
     * 结束库存
     */
    @Schema(description = "结束库存")
    private Long stockEnd;

    @Schema(description = "礼品卡类型")
    private GiftCardType giftCardType;

    @Schema(description = "提货卡适用商品范围：-1可选一种 -99可全选 其他代表N种")
    private Integer scopeGoodsNum;


}