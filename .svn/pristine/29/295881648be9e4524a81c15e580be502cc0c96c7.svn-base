package com.wanmi.sbc.empower.api.request.deliveryrecord;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>达达配送记录分页查询请求参数</p>
 *
 * @author zhangwenchang
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-订单号List
     */
    @Schema(description = "批量查询-订单号List")
    private List<String> orderNoList;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 第三方店铺编码
     */
    @Schema(description = "第三方店铺编码")
    private String shopNo;

    /**
     * 城市编码
     */
    @Schema(description = "城市编码")
    private String cityCode;

    /**
     * 订单金额;不含配送费
     */
    @Schema(description = "订单金额;不含配送费")
    private BigDecimal cargoPrice;

    /**
     * 是否需要垫付;1:是 0:否 (垫付订单金额，非运费)
     */
    @Schema(description = "是否需要垫付;1:是 0:否 (垫付订单金额，非运费)")
    private Integer isPrepay;

    /**
     * 配送距离;单位:米
     */
    @Schema(description = "配送距离;单位:米")
    private BigDecimal distance;

    /**
     * 实际运费
     */
    @Schema(description = "实际运费")
    private BigDecimal fee;

    /**
     * 运费
     */
    @Schema(description = "运费")
    private BigDecimal deliverFee;

    /**
     * 小费
     */
    @Schema(description = "小费")
    private BigDecimal tipsFee;

    /**
     * 是否使用保价费;0:不使用,1:使用
     */
    @Schema(description = "是否使用保价费;0:不使用,1:使用")
    private Integer isUseInsurance;

    /**
     * 保价费
     */
    @Schema(description = "保价费")
    private BigDecimal insuranceFee;

    /**
     * 0:接受订单1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败
     */
    @Schema(description = "0:接受订单1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败")
    private Integer deliveryStatus;

    /**
     * 删除标识;0:未删除1:已删除
     */
    @Schema(description = "删除标识;0:未删除1:已删除")
    private DeleteFlag delFlag;

    /**
     * 搜索条件:创建时间开始
     */
    @Schema(description = "搜索条件:创建时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /**
     * 搜索条件:创建时间截止
     */
    @Schema(description = "搜索条件:创建时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 搜索条件:更新时间开始
     */
    @Schema(description = "搜索条件:更新时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /**
     * 搜索条件:更新时间截止
     */
    @Schema(description = "搜索条件:更新时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;

}