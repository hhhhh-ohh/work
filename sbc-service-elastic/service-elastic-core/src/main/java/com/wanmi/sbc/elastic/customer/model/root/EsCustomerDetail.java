package com.wanmi.sbc.elastic.customer.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelBaseVO;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(indexName = EsConstants.DOC_CUSTOMER_DETAIL)
@Data
public class EsCustomerDetail implements Serializable {

    /**
     * 会员ID
     */
    @Id
    private String customerId;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 账户
     */
    private String customerAccount;

    /**
     * 省
     */
    private Long provinceId;

    /**
     * 市
     */
    private Long cityId;

    /**
     * 区
     */
    private Long areaId;

    /**
     * 街道
     */
    private Long streetId;

    /**
     * 详细地址
     */
    private String customerAddress;

    /**
     * 联系人名字
     */
    private String contactName;

    /**
     * 客户等级ID
     */
    private Long customerLevelId;


    /**
     * 联系方式
     */
    private String contactPhone;


    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    private CheckState checkState;

    /**
     * 可用积分
     */
    private Long pointsAvailable;

    /**
     * 账号状态 0：启用中  1：禁用中
     */
    private CustomerStatus customerStatus;


    /**
     * 客户类型（0:平台客户,1:商家客户）
     */
    private CustomerType customerType;

    /**
     * 负责业务员
     */
    private String employeeId;


    /**
     * 是否为分销员 0：否  1：是
     */
    private DefaultFlag isDistributor;


    /**
     * 审核驳回理由
     */
    private String rejectReason;

    /**
     * 禁用原因
     */
    private String forbidReason;

    /**
     * 店铺等级标识
     */
    private List<EsStoreCustomerRela> esStoreCustomerRelaList = new ArrayList<>();

    private EsEnterpriseInfo enterpriseInfo;

    /**
     * 企业购会员审核状态  0：无状态 1：待审核 2：已审核 3：审核未通过
     */
    private EnterpriseCheckState enterpriseCheckState;

    /**
     * 企业购会员审核拒绝原因
     */
    private String enterpriseCheckReason;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 注销状态 0:正常 1:注销中 2:已注销
     */
    private Long logOutStatus;

    /**
     * 注销原因Id
     */
    private String cancellationReasonId;

    /**
     * 注销原因
     */
    private String cancellationReason;

    /**
     * 付费会员等级名称
     */
    private List<PayingMemberLevelBaseVO> payingMemberLevelList;



    public List<EsStoreCustomerRela> getEsStoreCustomerRelaList() {
        return CollectionUtils.isEmpty(esStoreCustomerRelaList) ? Lists.newArrayList() : esStoreCustomerRelaList;
    }
}
