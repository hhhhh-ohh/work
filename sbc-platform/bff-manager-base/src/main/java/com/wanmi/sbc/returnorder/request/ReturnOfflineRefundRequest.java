package com.wanmi.sbc.returnorder.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;

import lombok.Data;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * 线下退款
 * Created by wj on 24/5/2017.
 */
@Schema
@Data
@Validated
public class ReturnOfflineRefundRequest extends BaseRequest {

    /**
     * 退款单外键
     */
    @Schema(description = "退款单外键")
    private String refundId;

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 客户的线下账户  0代表新增线下账户，使用 customerAccountName,customerBankName,customerAccountNo 进行新增
     */
    @Schema(description = "客户的线下账户 0代表新增线下账户，使用 customerAccountName,customerBankName,customerAccountNo 进行新增")
    private String customerAccountId;

    /**
     * 客户账户名字
     */
    @Schema(description = "客户账户名字")
    private String customerAccountName;

    /**
     * 客户银行账号
     */
    @Schema(description = "客户银行账号")
    private String customerAccountNo;

    /**
     * 客户开户行
     */
    @Schema(description = "客户开户行")
    private String customerBankName;

    /**
     * 线下账户
     */
    @Schema(description = "线下账户")
    private Long offlineAccountId;

    /**
     * 退款评论
     */
    @Schema(description = "退款评论")
    @Length(max = 100)
    private String refundComment;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 实付金额
     */
    @Schema(description = "实付金额")
    @Min(value = 0)
    private BigDecimal actualReturnPrice;

    /**
     * 实退积分
     */
    @Schema(description = "实退积分")
    @Min(value = 0)
    private Long actualReturnPoints;


    /**
     * 实付运费
     */
    @Schema(description = "实付运费")
    @Min(value = 0)
    private BigDecimal fee;

}
