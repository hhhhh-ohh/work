package com.wanmi.sbc.customer.agent.model.root;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "crm_oa_auth")
@Data
public class CrmOaAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crm_oa_auth_id")
    private Long crmOaAuthId;
    /**
     * oa账号
     */
    @Column(name = "oa_account", length = 255)
    private String oaAccount;

    /**
     * oa姓名
     */
    @Column(name = "oa_name", length = 255)
    private String oaName;

    /**
     * 账号类型 1-市级管理 2-区县级管理
     */
    @Column(name = "account_type")
    private Integer accountType;

    /**
     * 区域id
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 区域名称
     */
    @Column(name = "area_name", length = 2000)
    private String areaName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
}
