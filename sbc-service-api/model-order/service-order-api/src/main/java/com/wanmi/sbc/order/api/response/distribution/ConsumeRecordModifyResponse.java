package com.wanmi.sbc.order.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 更新消费记录返回体
 * @Autho qiaokang
 * @Date：2019-03-06 11:02:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class ConsumeRecordModifyResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "更新数量")
    private int count;
}
