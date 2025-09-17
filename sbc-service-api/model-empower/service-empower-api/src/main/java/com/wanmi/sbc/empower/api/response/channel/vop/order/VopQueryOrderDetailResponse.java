package com.wanmi.sbc.empower.api.response.channel.vop.order;

import com.alibaba.fastjson2.JSONArray;
import com.wanmi.sbc.empower.bean.vo.channel.order.VopSuborderSkuVO;
import com.wanmi.sbc.empower.bean.vo.channel.order.VopSuborderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 京东订单详情基础响应参数
 *
 * @author wur
 * @date: 2021/5/17 13:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class VopQueryOrderDetailResponse implements Serializable {

    private static final long serialVersionUID = -1197211196196252421L;

    @Schema(description = "父订单号，为0时，此订单为父订单，如果是有子单的父单，则此属性为json数组")
    private String pOrder;

    @Schema(description = "子订单详情（json数组）-父单独有")
    private String cOrder;

    @Schema(description = "订单状态。0为取消订单，1有效")
    private Integer orderState;

    @Schema(description = "京东订单编号")
    private Long jdOrderId;

    @Schema(description = "物流状态，0新建，1妥投，2拒收")
    private Integer state;

    @Schema(description = "预占确认状态 0 没确认 1确认")
    private Integer submitState;

    @Schema(description = "订单类型 1 父订单 2子订单")
    private Integer type;

    @Schema(description = "运费")
    private BigDecimal freight;

    @Schema(description = "商品列表 - json")
    private String sku;

    @Schema(description = "订单总金额")
    private BigDecimal orderPrice;

    @Schema(description = "订单未含税金额")
    private BigDecimal orderNakedPrice;

    @Schema(description = "订单税额")
    private BigDecimal orderTaxPrice;

    @Schema(description = "订单类别")
    private Integer orderType;

    @Schema(description = "订单创建时间：yyyy-MM-dd hh:mm:ss， 查询参数queryExts中包含createOrderTime")
    private LocalDateTime createOrderTime;

    @Schema(description = "订单完成时间， 查询参数queryExts中包含finishTime")
    private LocalDateTime finishTime;

    /**
     * 1.新单 2.等待支付 3.等待支付确认 4.延迟付款确认 5.订单暂停 6.店长最终审核
     * 7.等待打印 8.等待出库 9.等待打包 10.等待发货 11.自提途中 12.上门提货
     * 13.自提退货 14.确认自提 16.等待确认收货 17.配送退货 18.货到付款确认
     * 19.已完成 21.收款确认 22.锁定 29.等待三方出库 30.等待三方发货
     * 31.等待三方发货完成
     */
    @Schema(description = "京东状态，参数中包含queryExts=jdOrderState")
    private Integer jdOrderState;

    @Schema(description = "加密后的收货地址")
    private String address;

    @Schema(description = "加密后的姓名")
    private String name;

    @Schema(description = "加密后的联系方式")
    private String mobile;

    @Schema(description = "支付方式")
    private Integer paymentType;

    @Schema(description = "支付明细")
    private String payDetails;

    /**
     * 获取子单信息
     *
     * @author wur
     * @date: 2021/5/18 14:36
     * @return 子单列表
     */
    public List<VopSuborderVO> toSubOrder() {
        if (StringUtils.isBlank(cOrder)) {
            return Collections.emptyList();
        }
        return JSONArray.parseArray(cOrder, VopSuborderVO.class);
    }

    /**
     * 获取订单中商品的信息
     * @author  wur
     * @date: 2021/5/18 14:27
     * @return 商品列表
     **/
    public List<VopSuborderSkuVO> toSkuList() {
        if (StringUtils.isBlank(sku)) {
            return toSubOrder().stream().map(VopSuborderVO::toSkuList).flatMap(List::stream).collect(Collectors.toList());
        }
        return JSONArray.parseArray(sku, VopSuborderSkuVO.class);
    }
}
