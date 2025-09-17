package com.wanmi.sbc.empower.api.response.minimsgcustomerrecord;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询客户订阅消息信息表信息response</p>
 * @author xufeng
 * @date 2022-08-26 10:26:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgCustomerRecordByActivityIdResponse implements Serializable {
    private static final long serialVersionUID = 3566104931527495422L;

    /**
     * 实际推送人数
     */
    @Schema(description = "实际推送人数")
    private Long returnNum;

    /**
     * 实际推送成功人数
     */
    @Schema(description = "实际推送成功人数")
    private Long returnSuccessNum;
}
