package com.wanmi.sbc.customer.invoice.model.entity;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

/**
 * 会员增专票
 * Created by CHENLI on 2017/4/21.
 */
@Data
public class CustomerInvoiceSaveRequest extends BaseRequest {
    /**
     * 增专资质ID
     */
    private Long customerInvoiceId;

    /**
     * 会员ID
     */
    private String customerId;

    /**
     * 单位全称
     */
    @NotBlank
    private String companyName;

    /**
     * 纳税人识别号
     */
//    @NotBlank
    private String taxpayerNumber;

    /**
     * 单位电话
     */
//    @NotBlank
    private String companyPhone;

    /**
     * 单位地址
     */
//    @NotBlank
    private String companyAddress;

    /**
     * 银行基本户号
     */
//    @NotBlank
    private String bankNo;

    /**
     * 开户行
     */
//    @NotBlank
    private String bankName;

    /**
     * 营业执照复印件
     */
//    @NotBlank
    private String businessLicenseImg;

    /**
     * 一般纳税人认证资格复印件
     */
//    @NotBlank
    private String taxpayerIdentificationImg;

    /**
     * 增票资质审核状态
     */
    private CheckState checkState;

    private InvoiceStyle invoiceStyle=InvoiceStyle.SPECIAL;

    @Override
    public void checkParam() {
        if(StringUtils.isBlank(companyName)||companyName.length()>50){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(invoiceStyle.equals(InvoiceStyle.SPECIAL)){

            if(StringUtils.isBlank(taxpayerNumber) || taxpayerNumber.length()<15||taxpayerNumber.length()>20){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(StringUtils.isBlank(companyPhone) || companyPhone.length()>20){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(StringUtils.isBlank(companyAddress) || companyAddress.length()>100){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(StringUtils.isBlank(bankNo) || bankNo.length()>45){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(StringUtils.isBlank(bankName)||bankName.length()>60){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(StringUtils.isBlank(businessLicenseImg)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if(StringUtils.isBlank(taxpayerIdentificationImg)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        if(invoiceStyle.equals(InvoiceStyle.COMPANY)){
            if(StringUtils.isBlank(taxpayerNumber)||taxpayerNumber.length()<15||taxpayerNumber.length()>20){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

        }
    }
}
