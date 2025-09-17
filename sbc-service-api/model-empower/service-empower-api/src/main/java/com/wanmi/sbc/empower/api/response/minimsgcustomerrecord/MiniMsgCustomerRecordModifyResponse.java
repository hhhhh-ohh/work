package com.wanmi.sbc.empower.api.response.minimsgcustomerrecord;

import com.wanmi.sbc.empower.bean.vo.MiniMsgCustomerRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>客户订阅消息信息表修改结果</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgCustomerRecordModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的客户订阅消息信息表信息
     */
    @Schema(description = "已修改的客户订阅消息信息表信息")
    private MiniMsgCustomerRecordVO miniMsgCustomerRecordVO;
}
