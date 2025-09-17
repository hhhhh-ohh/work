package com.wanmi.sbc.customer.agent.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 代理商实体类
 */
@Entity
@Table(name = "agent")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Agent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "代理商主键ID（UUID）")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "agent_id")
    private String agentId;

    @Schema(description = "代理商名称")
    @Column(name = "agent_name")
    private String agentName;

    @Schema(description = "系统唯一码（不可重复）")
    @Column(name = "agent_unique_code")
    private String agentUniqueCode;

    @Schema(description = "代理商类型：1小B 2一级代理商 3二级代理商 4一级合作商")
    @Column(name = "type")
    private Integer type;

    @Schema(description = "代理商上级唯一码")
    @Column(name = "agent_parent_unique_code")
    private String agentParentUniqueCode;

    @Schema(description = "客户ID")
    @Column(name = "customer_id")
    private String customerId;

    @Schema(description = "省ID")
    @CanEmpty
    @Column(name = "province_id")
    private Long provinceId;

    @Schema(description = "市ID")
    @CanEmpty
    @Column(name = "city_id")
    private Long cityId;

    @Schema(description = "区ID")
    @Column(name = "area_id")
    private Long areaId;

    @Schema(description = "街道ID")
    @Column(name = "street_id")
    private Long streetId;

    @Schema(description = "省名称")
    @Column(name = "province_name")
    @CanEmpty
    private String provinceName;

    @Schema(description = "市名称")
    @CanEmpty
    @Column(name = "city_name")
    private String cityName;

    @Schema(description = "区名称")
    @Column(name = "area_name")
    private String areaName;

    @Schema(description = "街道名称")
    @Column(name = "street_name")
    private String streetName;

    @Schema(description = "学校名称")
    @Column(name = "school_name")
    private String schoolName;

    @Schema(description = "店铺名称")
    @Column(name = "shop_name")
    private String shopName;

    @Schema(description = "店铺属性")
    @Column(name = "shop_attribute")
    private String shopAttribute;

    @Schema(description = "店铺地址")
    @Column(name = "shop_address")
    private String shopAddress;

    @Schema(description = "店铺负责人")
    @Column(name = "contact_person")
    private String contactPerson;

    @Schema(description = "联系电话")
    @Column(name = "contact_phone")
    private String contactPhone;

    @Schema(description = "营业执照URL/编号")
    @Column(name = "business_license")
    private String businessLicense;

    @Schema(description = "开户行")
    @Column(name = "bank_open")
    private String bankOpen;

    @Schema(description = "银行卡号")
    @Column(name = "bank_account")
    private String bankAccount;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "有效开始时间")
    @Column(name = "valid_start")
    private LocalDateTime validStart;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "有效结束时间")
    @Column(name = "valid_end")
    private LocalDateTime validEnd;

    @Schema(description = "首单银卡会员等级佣金价格")
    @Column(name = "silver_card_level_price")
    private BigDecimal silverCardLevelPrice;

    @Schema(description = "首单金卡会员等级佣金价格")
    @Column(name = "gold_card_level_price")
    private BigDecimal goldCardLevelPrice;

    @Schema(description = "首单钻卡会员等级佣金价格")
    @Column(name = "diamond_card_level_price")
    private BigDecimal diamondCardLevelPrice;

    @Schema(description = "续购佣金比例")
    @Column(name = "renewal_ratio")
    private BigDecimal renewalRatio;

    @Schema(description = "审核状态 0已创建 1待审核 2通过 3驳回")
    @Column(name = "audit_status")
    private Integer auditStatus;

    @Schema(description = "二维码地址")
    @Column(name = "qr_code_address")
    private String qrCodeAddress;

    @Schema(description = "海报地址")
    @Column(name = "poster_address")
    private String posterAddress;

    @Schema(description = "驳回原因")
    @Column(name = "reject_reason")
    private String rejectReason;

    @Schema(description = "展示状态 0不显示 1显示")
    @Column(name = "is_show")
    private Integer isShow;

    @Schema(description = "删除标志 0正常 1已删除")
    @Enumerated
    @Column(name = "del_flag")
    private DeleteFlag delFlag;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "创建时间")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    @Column(name = "create_person")
    private String createPerson;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "更新时间")
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    @Column(name = "update_person")
    private String updatePerson;
}
