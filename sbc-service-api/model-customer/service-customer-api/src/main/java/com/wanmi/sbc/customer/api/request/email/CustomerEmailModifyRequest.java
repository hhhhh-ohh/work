package com.wanmi.sbc.customer.api.request.email;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmailModifyRequest extends BaseRequest {

    /**
     * 邮箱配置Id
     */
    @Schema(description = "邮箱配置Id")
    @NotNull
    private String customerEmailId;

    /**
     * 邮箱所属客户Id
     */
    @Schema(description = "邮箱所属客户Id")
    @NotNull
    private String customerId;

    /**
     * 发信人邮箱地址
     */
    @Schema(description = "发信人邮箱地址")
    @NotEmpty
    @Length(max = 32)
    private String emailAddress;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Override
    public void checkParam() {
        if (this.customerEmailId == null || this.customerId == null
                || StringUtils.isEmpty(this.emailAddress) || this.emailAddress.length() > 32) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

}
