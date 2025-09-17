package com.wanmi.sbc.elastic.customerInvoice.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = EsConstants.ES_CUSTOMER_INVOICE)
@Data
public class EsCustomerInvoice {

    /**
     * 增专资质ID
     */
    @Id
    private Long customerInvoiceId;

    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 单位全称
     */
    private String companyName;

    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;

    /**
     * 单位电话
     */
    private String companyPhone;

    /**
     * 单位地址
     */
    private String companyAddress;

    /**
     * 银行基本户号
     */
    private String bankNo;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 营业执照复印件
     */
    private String businessLicenseImg;

    /**
     * 一般纳税人认证资格复印件
     */
    private String taxpayerIdentificationImg;

    /**
     * 增票资质审核状态  0:待审核 1:已审核通过 2:审核未通过
     */
    private CheckState checkState;

    /**
     * 审核未通过原因
     */
    private String rejectReason;

    /**
     * 增专资质是否作废 0：否 1：是
     */
    private InvalidFlag invalidFlag;

    /**
     * 删除标志 0未删除 1已删除
     */
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 修改时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    private String updatePerson;

    /**
     * 删除时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    private String deletePerson;

    private InvoiceStyle invoiceStyle;
}
