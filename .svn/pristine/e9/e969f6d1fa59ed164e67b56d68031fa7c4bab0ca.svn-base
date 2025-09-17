package com.wanmi.sbc.message.api.response.pushcustomerenable;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.PushCustomerEnableVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>会员推送通知开关新增结果</p>
 * @author Bob
 * @date 2020-01-07 15:31:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushCustomerEnableAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的会员推送通知开关信息
     */
    @Schema(description = "已新增的会员推送通知开关信息")
    private PushCustomerEnableVO pushCustomerEnableVO;
}
