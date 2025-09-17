package com.wanmi.sbc.order.trade.model.entity.value;

import com.wanmi.sbc.order.bean.vo.FreightTemplateGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeFreightTemplateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wur
 * @className Freight
 * @description TODO
 * @date 2021/9/9 14:05
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Freight implements Serializable {

    /**
     * 用户承担的运费  根据商家代销运费设置来计算
     */
    private BigDecimal freight;

    /**
     * 商家承担运费
     */
    private BigDecimal supplierBearFreight;

    /**
     * 商家运费
     */
    private BigDecimal supplierFreight;

    /**
     * 供应商运费
     */
    private BigDecimal providerFreight;

    /**
     * 供应商运费列表
     */
    private List<ProviderFreight> providerFreightList;

    /**
     * 包邮标志，0：不包邮 1:包邮
     */
    private Integer postage;

    /**
     * 运费模板信息
     */
    private List<TradeFreightTemplateVO>  templateVOList = new ArrayList<>();

    /**
     * 运费模板分组信息 用于单品运费 + 非免邮
     * 单品运费模板分组信息  用于确认订单页运费详情展示使用
     */
    private List<FreightTemplateGroupVO> groupVO = new ArrayList<>();

}