package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by Administrator on 2017/5/1.
 */
@Data
@Schema
public class BuyerVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 购买人编号
     */
    @Schema(description = "购买人编号")
    private String id;

    /**
     * 购买人姓名
     */
    @Schema(description = "购买人姓名")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String name;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String account;

    /**
     * 等级编号
     */
    @Schema(description = "等级编号")
    private Long levelId;

    /**
     * 等级名称
     */
    @Schema(description = "等级名称")
    private String levelName;

    /**
     * 标识用户是否属于当前订单所属商家,true|false
     */
    @Schema(description = "标识用户是否属于当前订单所属商家",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private boolean customerFlag = true;

    /**
     *
     */
    @Schema(description = "手机号")
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String phone;

    /**
     * 买家关联的业务员id
     */
    @Schema(description = "买家关联的业务员id")
    private String employeeId;
    /**
     * 身份证号码18位
     */
    @Schema(description = "身份证号码18位")
    @SensitiveWordsField(signType = SignWordType.IDCARD)
    private String customerCardNo;

    /**
     * 身份证姓名
     */
    @Schema(description = "身份证姓名")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerCardName;

    /**
     * 第三方平台的登录ID
     */
    private String thirdLoginOpenId;
    /**
     * @param customer
     * @param level
     * @return
     */
    public static BuyerVO fromCustomer(CustomerVO customer, Optional<CustomerLevelVO> level, boolean customerFlag) {
        BuyerVO b = new BuyerVO();
        b.setId(customer.getCustomerId());
        b.setName(customer.getCustomerDetail().getCustomerName());
        b.setLevelId(customer.getCustomerLevelId());
        b.setPhone(customer.getCustomerDetail().getContactPhone());
        b.setAccount(customer.getCustomerAccount());
        b.setEmployeeId(customer.getCustomerDetail().getEmployeeId());
        if (level.isPresent()) {
            CustomerLevelVO customerLevel = level.get();
            b.setLevelName(customerLevel.getCustomerLevelName());
        }
        b.setCustomerFlag(customerFlag);
        return b;
    }
}
