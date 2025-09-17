package com.wanmi.sbc.setting.openapisetting.model.root;

import com.wanmi.sbc.common.enums.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * @description 开放平台api设置实体类
 * @author lvzhenwei
 * @date 2021/4/14 3:10 下午
 */
@Data
@Entity
@Table(name = "open_api_setting")
public class OpenApiSetting extends BaseEntity {
    private static final long serialVersionUID = 3114041801528663328L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 店铺id */
    @Column(name = "store_id")
    private Long storeId;

    /** 店铺名称 */
    @Column(name = "store_name")
    private String storeName;

    /** 商家名称 */
    @Column(name = "supplier_name")
    private String supplierName;

    /** 商家类型：0:供应商；1:商家； */
    @Column(name = "store_type")
    @Enumerated
    private StoreType storeType;

    /** 签约开始日期 */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "contract_start_date")
    private LocalDateTime contractStartDate;

    /** 签约结束日期 */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "contract_end_date")
    private LocalDateTime contractEndDate;

    /** 审核状态 0、待审核 1、已审核 2、审核未通过 */
    @Column(name = "audit_state")
    @Enumerated
    private AuditStatus auditState;

    /** 审核未通过原因 */
    @Column(name = "audit_reason")
    private String auditReason;

    /** 禁用状态:0:禁用；1:启用 */
    @Column(name = "disable_state")
    @Enumerated
    private EnableStatus disableState;

    /** 禁用原因 */
    @Column(name = "disable_reason")
    private String disableReason;

    /** app_key */
    @Column(name = "app_key")
    private String appKey;

    /** app_secret */
    @Column(name = "app_secret")
    private String appSecret;

    /** 限流值 */
    @Column(name = "limiting_num")
    private Long limitingNum;

    /** 是否删除 0 否 1 是 */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /** 平台类型：0:boss；1:商家； */
    @Column(name = "platform_type")
    @Enumerated
    private PlatformType platformType;

    /**
     * 平台描述
     */
    @Column(name = "platform_desc")
    private String platformDesc;
}
