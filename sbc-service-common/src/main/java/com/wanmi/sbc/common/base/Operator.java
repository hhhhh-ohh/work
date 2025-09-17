package com.wanmi.sbc.common.base;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 * Created by jinwei on 27/3/2017.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作方
     */
    @Schema(description = "操作方")
    private Platform platform;

    @Schema(description = "操作人")
    private String name;

    @Schema(description = "管理员Id")
    private String adminId;

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "操作所在的Ip地址")
    private String ip;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private String storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private BoolFlag companyType;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    private String account;

    /**
     * 公司Id
     */
    @Schema(description = "公司Id")
    private Long companyInfoId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 是否首次登陆
     */
    @Schema(description = "是否首次登陆")
    private Boolean firstLogin;

    @Schema(description = "增值服务")
    private List<VASEntity> services = new ArrayList<>();

    /**
     * 用户的登陆的terminal token
     */
    @Schema(description = "terminal token")
    private String terminalToken;

    /**
     * 会员详情ID
     */
    @Schema(description = "会员详情ID")
    private String customerDetailId;

    /**
     * 角色
     */
    @Schema(description = "角色")
    private String roleName;

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型0品牌商城，1商家，2直营店")
    private StoreType storeType;

    /**
     * 版本号
     * 员工版本，不一致：token失效
     * 变更事件：密码修改
     */
    @Schema(description = "版本号")
    private Long versionNo;


    @Schema
    private Boolean isNew;

    /**
     * 操作UserAgent
     */
    @Schema(description = "操作UserAgent")
    private String opUserAgent;
}
