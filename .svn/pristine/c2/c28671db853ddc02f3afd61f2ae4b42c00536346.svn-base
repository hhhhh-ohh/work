package com.wanmi.sbc.customer.api.response.agent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @program: sbc-micro-service
 * @description: 代理商返回结果
 * @create: 2020-04-01 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class GetAgentResponse extends BasicResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "代理商主键ID")
    private String agentId;

    @Schema(description = "代理商名称")
    private String agentName;

    @Schema(description = "系统唯一码（不可重复）")
    private String agentUniqueCode;

    @Schema(description = "代理商类型：1小B 2一级代理商 3二级代理商 4一级合作商")
    private Integer type;

    @Schema(description = "父系统唯一码")
    private String agentParentUniqueCode;

    @Schema(description = "省ID")
    // Deleted:@CanEmpty
    private Long provinceId;

    @Schema(description = "市ID")
    // Deleted:@CanEmpty
    private Long cityId;

    @Schema(description = "区")
    private Long areaId;

    @Schema(description = "街道")
    private Long streetId;

    @Schema(description = "省名称")
    private String provinceName;

    @Schema(description = "市名称")
    private String cityName;

    @Schema(description = "区名称")
    private String areaName;

    @Schema(description = "街道名称")
    private String streetName;

    @Schema(description = "学校名称")
    private String schoolName;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "店铺属性")
    private String shopAttribute;

    @Schema(description = "店铺地址")
    private String shopAddress;

    @Schema(description = "店铺负责人")
    private String contactPerson;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "营业执照URL/编号")
    private String businessLicense;

    @Schema(description = "开户行")
    private String bankOpen;

    @Schema(description = "银行卡号")
    private String bankAccount;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "有效开始时间")
    private LocalDateTime validStart;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "有效结束时间")
    private LocalDateTime validEnd;

    @Schema(description = "首单银卡会员等级佣金价格")
    private BigDecimal silverCardLevelPrice;

    @Schema(description = "首单金卡会员等级佣金价格")
    private BigDecimal goldCardLevelPrice;

    @Schema(description = "首单钻卡会员等级佣金价格")
    private BigDecimal diamondCardLevelPrice;

    @Schema(description = "续购佣金比例")
    private BigDecimal renewalRatio;

    @Schema(description = "审核状态 0已创建 1待审核 2通过 3驳回")
    private Integer auditStatus;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "二维码地址")
    private String qrCodeAddress;

    @Schema(description = "海报地址")
    private String posterAddress;

    @Schema(description = "展示状态 0不显示 1显示")
    private Integer isShow;

    @Schema(description = "删除标志 0正常 1已删除")
    private DeleteFlag delFlag;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private String createPerson;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private String updatePerson;
}
