package com.wanmi.sbc.customer.api.request.email;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmailDeleteRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 客户邮箱Id
     */
    @Schema(description = "客户邮箱Id")
    @NotBlank
    private String customerEmailId;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private String delPerson;

    @Override
    public void checkParam() {
        // 客户邮箱id不能为空
        if (StringUtils.isBlank(this.customerEmailId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

//    public CustomerEmail generateCustomerEmail() {
//        CustomerEmail customerEmail = new CustomerEmail();
//        customerEmail.setCustomerEmailId(customerEmailId);
//        customerEmail.setDelFlag(DeleteFlag.YES);
//        customerEmail.setDelPerson(delPerson);
//        customerEmail.setDelTime(LocalDateTime.now());
//        return customerEmail;
//    }

}
