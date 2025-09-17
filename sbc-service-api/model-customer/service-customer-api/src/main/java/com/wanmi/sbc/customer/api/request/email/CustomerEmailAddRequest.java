package com.wanmi.sbc.customer.api.request.email;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

/**
 * 新增客户邮箱
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmailAddRequest extends BaseRequest {

    /**
     * 邮箱所属客户Id
     */
    @Schema(description = "邮箱所属客户Id")
    private String customerId;

    /**
     * 发信人邮箱地址
     */
    @Schema(description = "发信人邮箱地址")
    @NotEmpty
    @Length(max = 32)
    private String emailAddress;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Override
    public void checkParam() {
        if (StringUtils.isEmpty(this.emailAddress) || this.emailAddress.length() > Constants.NUM_32) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    /**
     * 客户邮箱对象
     *
     * @return
     */
//    public CustomerEmail generateCoupon() {
//        CustomerEmail customerEmail = new CustomerEmail();
//        customerEmail.setCustomerId(this.getCustomerId());
//        customerEmail.setEmailAddress(this.getEmailAddress());
//        customerEmail.setCreatePerson(this.getCreatePerson());
//        customerEmail.setCreateTime(LocalDateTime.now());
//        customerEmail.setDelFlag(DeleteFlag.NO);
//        return customerEmail;
//    }

}
