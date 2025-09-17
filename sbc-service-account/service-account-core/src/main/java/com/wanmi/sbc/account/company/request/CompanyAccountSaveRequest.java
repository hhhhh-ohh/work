package com.wanmi.sbc.account.company.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商家收款账户保存请求
 * Created by CHENLI on 2017/4/27.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -4986935519385104049L;
    /**
     * 主键
     */
    private Long accountId;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 账号
     */
    private String bankNo;

    /**
     * 银行账号编码
     */
    private String bankCode;

    /**
     * 公司信息ID
     */
    private Long companyInfoId;

    /**
     * 支行信息
     */
    private String bankBranch;

    /**
     * 打款金额
     */
    private BigDecimal remitPrice;

    /**
     * 商家类型
     */
    private StoreType storeType;

    /**
     * 打款标记
     */
    private DefaultFlag isReceived = DefaultFlag.NO;

}
