package com.wanmi.sbc.empower.bean.vo.channel.order;

import com.alibaba.fastjson2.JSONArray;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 子订单信息
 *
 * @author wur
 * @className VopSuborderVO
 * @date 2021/5/17 17:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VopSuborderVO implements Serializable {

    private static final long serialVersionUID = -6010525423753732619L;

    /** 父订单号 */
    private String pOrder;

    /** 订单状态 */
    private Integer orderState;

    /** 京东状态 */
    private Integer jdOrderState;

    /** 物流状态 */
    private Integer state;

    /** 京东订单编号 */
    private String jdOrderId;

    /** 预占确认状态 */
    private Integer submitState;

    /** 订单类型 */
    private Integer type;

    /** 商品列表 */
    private String sku;

    /** 运费 */
    private BigDecimal freight;

    /** 订单总金额 */
    private BigDecimal orderPrice;

    /** 订单未含税金额 */
    private BigDecimal orderNakedPrice;

    /** 订单税额 */
    private BigDecimal orderTaxPrice;

    /**
     * 获取订单中商品的信息
     * @author  wur
     * @date: 2021/5/18 14:27
     * @return 商品列表
     **/
    public List<VopSuborderSkuVO> toSkuList() {
        if (StringUtils.isBlank(sku)) {
            return Collections.emptyList();
        }
        return JSONArray.parseArray(sku, VopSuborderSkuVO.class);
    }
}
