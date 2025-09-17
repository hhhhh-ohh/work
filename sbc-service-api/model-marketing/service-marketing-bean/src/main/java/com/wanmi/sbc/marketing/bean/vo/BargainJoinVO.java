package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>帮砍记录VO</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@Schema
@Data
public class BargainJoinVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainJoinId
     */
    @Schema(description = "bargainJoinId")
    private Long bargainJoinId;

    /**
     * 砍价记录id
     */
    @Schema(description = "砍价记录id")
    private Long bargainId;

    /**
     * 砍价商品id
     */
    @Schema(description = "砍价商品id")
    private String goodsInfoId;

    /**
     * 砍价的发起人
     */
    @Schema(description = "砍价的发起人")
    private String customerId;

    /**
     * 帮砍人id
     */
    @Schema(description = "帮砍人id")
    private String joinCustomerId;

    /**
     * 帮砍人用户名
     */
    @Schema(description = "帮砍人用户名")
    private String customerName;

    /**
     * 帮砍人账号
     */
    @Schema(description = "帮砍人账号")
    private String customerAccount;

    /**
     * 帮砍人头像
     */
    @Schema(description = "帮砍人头像")
    private String headImg;

    /**
     * 砍价随机语
     */
    @Schema(description = "砍价随机语")
    private String barginGoodsRandomWords;

    /**
     * 帮砍金额
     */
    @Schema(description = "帮砍金额")
    private BigDecimal bargainAmount;

    /**
     * createTime
     */
    @Schema(description = "createTime")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    public String getCustomerAccount() {
        if (StringUtils.isBlank(customerAccount)) {
            return customerAccount;
        }
        if (customerAccount.length() == 11) {
            return StringUtils.substring(customerAccount, 0, 3).concat("****").concat(StringUtils.substring(customerAccount, 7));
        } else {
            return StringUtils.substring(customerAccount, 0, 1).concat("****");
        }
    }

    public String getCustomerName() {
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