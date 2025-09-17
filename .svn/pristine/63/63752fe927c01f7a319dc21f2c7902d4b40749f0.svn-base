package com.wanmi.sbc.customer.bean.enums;

import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;

/**
 * 短信模板
 *
 * @author aqlu
 * @date 15/12/4
 */
@ApiEnum(dataType = "java.lang.String")
public enum SmsTemplate {
    @ApiEnumProperty("验证码短信模板")
    VERIFICATION_CODE("验证码：%s，有效期5分钟，请勿泄露给他人。"),
    @ApiEnumProperty("用户密码短信模板")
    CUSTOMER_PASSWORD("您本次账号是：%1s，密码是: %2s"),
    @ApiEnumProperty("企业会员密码短信模板")
    ENTERPRISE_CUSTOMER_PASSWORD("恭喜您成为企业会员，您可享受企业会员专享价，您的账号是：%1s，密码是:  %2s，快去商城采购吧~"),
    @ApiEnumProperty("员工密码短信模板")
    EMPLOYEE_PASSWORD("您本次账号是：%1s，密码是: %2s"),
    @ApiEnumProperty("会员导入成功信息模板")
    CUSTOMER_IMPORT_SUCCESS("欢迎加入SBC商城，您的万米账号创建成功，您可使用短信验证码进行快捷登录，默认密码为手机号后6位，登陆后请及时更改！"),
    @ApiEnumProperty("授信审核成功通知模板")
    CREDIT_AUDIT_PASS_NOTICE("恭喜您，您的授信申请已处理成功，请登陆商城查看详情"),
    @ApiEnumProperty("授信申请驳回通知")
    CREDIT_AUDIT_REJECTED_NOTICE("很抱歉，您的授信申请被驳回，原因是：%s，请登陆商城查看详情"),
    @ApiEnumProperty("授信变更成功通知")
    CREDIT_CHANGE_PASS_NOTICE("恭喜您，您的授信变更申请已处理成功，请登陆商城查看详情"),
    @ApiEnumProperty("授信变更驳回通知")
    CREDIT_CHANGE_REJECTED_NOTICE("很抱歉，您的授信申请被驳回，原因是：%s，请登陆商城查看详情"),
    @ApiEnumProperty("授信还款提醒")
    CREDIT_REPAY_NOTICE("您的授信当前有待还金额%s元，请在3天内还款，以免逾期影响使用"),
    @ApiEnumProperty("授信逾期提醒")
    CREDIT_OVERDUE_NOTICE("您的授信已逾期，为了不影响您的使用， 请尽快还款");

    private String content;

    SmsTemplate(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }
}
