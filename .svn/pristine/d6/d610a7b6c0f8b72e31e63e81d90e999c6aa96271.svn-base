package com.wanmi.sbc.elastic.storeInformation.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

/**
 * @Author yangzhen
 * @Description //商品店铺持久化到es
 * @Date 9:51 2020/12/8
 * @Param
 * @return
 **/
@Document(indexName = EsConstants.DOC_STORE_INFORMATION_TYPE)
@Data
public class StoreInformation {

    @Id
    private String id;

    /**
     * 公司信息ID
     */
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 商家名称
     */
    private String supplierName;

    /**
     * 商家编号
     */
    private String companyCode;

    /**
     * 商家账号
     */
    private String accountName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    private Integer companyType;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    private CheckState auditState;

    /**
     * 审核未通过原因
     */
    private String auditReason;

    /**
     * 账号状态
     */
    private AccountState accountState;

    /**
     * 账号禁用原因
     */
    private String accountDisableReason;

    /**
     * 店铺状态 0、开启 1、关店
     */
    private StoreState storeState;

    /**
     * 账号关闭原因
     */
    private String storeClosedReason;

    /**
     * 商家类型0品牌商城，1商家
     */
    private StoreType storeType;

    /**
     * 是否是主账号
     */
    private Integer isMasterAccount;

    /**
     * 店铺删除状态
     */
    private DeleteFlag storeDelFlag;

    /**
     * 公司删除状态
     */
    private DeleteFlag companyInfoDelFlag;

    /**
     * 员工删除状态
     */
    private DeleteFlag employeeDelFlag;

    /**
     * 是否确认打款 (-1:全部,0:否,1:是)
     */
    private Integer remitAffirm;

    /**
     * 创建结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTime;

    /**
     * 创建结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /**
     * 创建结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /**
     * 二次签约审核状态
     */
    @Schema(description = "二次签约审核状态")
    private CheckState contractAuditState;

    /**
     * 二次签约拒绝原因
     */
    @Schema(description = "二次签约拒绝原因")
    private String contractAuditReason;

    /**
     * 关联公司信息ID
     */
    @Schema(description = "关联公司信息ID")
    private Long relCompanyInfoId;

    /**
     * 关联店铺ID
     */
    @Schema(description = "关联店铺ID")
    private Long relStoreId;

}
