package com.wanmi.sbc.empower.bean.vo.channel.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 子订单信息
 * @author wur
 * @className VopSuborderVO
 * @date 2021/5/17 17:15
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VopSuborderSkuVO implements Serializable {

    private static final long serialVersionUID = -6010525423753732619L;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编号
     */
    private Long skuId;

    /**
     * 商品数据
     */
    private Integer num;

    /**
     * 京东三级分类
     */
    private Integer category;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品税率
     */
    private BigDecimal tax;

    /**
     * 主商品ID
     */
    private Integer oid;

    /**
     * 商品类型。0 普通、1 附件、2 赠品、3延保
     */
    private Integer type;

    /**
     * 运费拆分价格
     */
    private Integer splitFreight;

    /**
     * 商品税额
     */
    private Integer taxPrice;

    /**
     * 商品未含税金额
     */
    private Integer nakedPrice;

}