package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员中心返回数据
 * Created by CHENLI on 2017/7/17.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCenterResponse extends BasicResponse {

    /**
     * 客户编号
     */
    @Schema(description = "客户编号")
    private String customerId;

    /**
     * 客户账号
     */
    @Schema(description = "客户账号")
    private String customerAccount;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String customerName;


    /**
     * 客户名称-脱敏
     */
    @Schema(description = "客户名称-脱敏")
    private String customerNameSign;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

    /**
     * 等级徽章图
     */
    @Schema(description = "等级徽章图")
    private String rankBadgeImg;

    /**
     * 客户成长值
     */
    @Schema(description = "客户成长值")
    private Long growthValue;

    /**
     * 客户头像
     */
    @Schema(description = "客户头像")
    private String headImg;

    /**
     * 可用积分
     */
    @Schema(description = "可用积分")
    private Long pointsAvailable;

    /**
     * 已用积分
     */
    @Schema(description = "已用积分")
    private Long pointsUsed;

    /**
     * 考虑到后面可能会有很多类似“企业会员”的标签，用List存放标签内容
     */
    @Schema(description = "会员标签")
    private List<String> customerLabelList = new ArrayList<>();

    /**
     * 企业会员名称
     */
    @Schema(description = "企业会员名称")
    private String enterpriseCustomerName;

    /**
     * 企业会员logo 
     */
    @Schema(description = "企业会员logo")
    private String enterpriseCustomerLogo;

    public String getCustomerNameSign() {
        if (StringUtils.isBlank(customerName)) {
            return customerName;
        }
        if (customerName.length() == 11) {
            return StringUtils.substring(customerName, 0, 3).concat("****").concat(StringUtils.substring(customerName, 7));
        } else {
            return StringUtils.substring(customerName, 0, 1).concat("****");
        }
    }
}
