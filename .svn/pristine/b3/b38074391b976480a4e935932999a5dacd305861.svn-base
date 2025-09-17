package com.wanmi.sbc.logout;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@Schema(description = "LogoffRequest")
public class LogoffRequest {

    /**
     * 会员ID
     */
    @Schema(description = "会员ID列表")
    @NotEmpty
    private List<String> customerIds;
}
