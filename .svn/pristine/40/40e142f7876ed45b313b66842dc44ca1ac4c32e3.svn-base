package com.wanmi.sbc.customer.api.response.agent;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentGetByUniqueCodeResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 代理商主键ID（UUID）
     */
    @Schema(description = "代理商主键ID")
    private String agentId;

    /**
     * 代理商名称
     */
    @Schema(description = "代理商名称")
    private String agentName;

    /**
     * 系统唯一码（不可重复）
     */
    @Schema(description = "系统唯一码（不可重复）")
    private String agentUniqueCode;

    /**
     * 省ID
     */
    @Schema(description = "省ID")
    // Deleted:@CanEmpty
    private Long provinceId;

    /**
     * 市ID
     */
    @Schema(description = "市ID")
    // Deleted:@CanEmpty
    private Long cityId;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 街道
     */
    @Schema(description = "街道")
    private Long streetId;

    /**
     * 省名称
     */
    @CanEmpty
    private String provinceName;

    /**
     * 市名称
     */
    @CanEmpty
    private String cityName;

    /**
     * 区名称
     */
    private String areaName;


    /**
     * 学校名称
     */
    @Schema(description = "学校名称")
    private String schoolName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 店铺属性
     */
    @Schema(description = "店铺属性")
    private String shopAttribute;

    /**
     * 店铺地址
     */
    @Schema(description = "店铺地址")
    private String shopAddress;

    /**
     * 店铺负责人
     */
    @Schema(description = "店铺负责人")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String contactPhone;

    /**
     * 营业执照URL/编号
     */
    @Schema(description = "营业执照URL/编号")
    private String businessLicense;

    /**
     * 开户行
     */
    @Schema(description = "开户行")
    private String bankOpen;

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    private String bankAccount;

    /**
     * 有效开始时间
     */
    @Schema(description = "有效开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime validStart;

    /**
     * 有效结束时间
     */
    @Schema(description = "有效结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime validEnd;

    /**
     * 首单银卡会员等级佣金价格
     */
    @Schema(description = "首单银卡会员等级佣金价格")
    private BigDecimal silverCardLevelPrice;

    /**
     * 首单金卡会员等级佣金价格
     */
    @Schema(description = "首单金卡会员等级佣金价格")
    private BigDecimal goldCardLevelPrice;

    /**
     * 首单钻卡会员等级佣金价格
     */
    @Schema(description = "首单钻卡会员等级佣金价格")
    private BigDecimal diamondCardLevelPrice;

    /**
     * 续购佣金比例
     */
    @Schema(description = "续购佣金比例")
    private BigDecimal renewalRatio;

    /**
     * 审核状态 0已创建 1待审核 2通过 3驳回
     */
    @Schema(description = "审核状态 0已创建 1待审核 2通过 3驳回")
    private Integer auditStatus;

    /**
     * 驳回原因
     */
    @Schema(description = "驳回原因")
    private String rejectReason;

    /**
     * 二维码地址
     */
    @Schema(description = "二维码地址")
    private String qrCodeAddress;

    /**
     * 海报地址
     */
    @Schema(description = "海报地址")
    private String posterAddress;

    /**
     * 删除标志 0正常 1已删除
     */
    @Schema(description = "删除标志 0正常 1已删除")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updatePerson;


    public BigDecimal getPriceByMemberLevel(Integer memberLevel) {
        if (memberLevel == null) {
            return BigDecimal.ZERO;
        } else if (memberLevel == 1) {
            return getSilverCardLevelPrice();
        } else if (memberLevel == 2) {
            return getGoldCardLevelPrice();
        } else if (memberLevel == 3) {
            return getDiamondCardLevelPrice();
        } else {
            return BigDecimal.ZERO;
        }
    }
}
