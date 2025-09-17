package com.wanmi.sbc.empower.api.response.minimsgcustomerrecord;

import com.wanmi.sbc.empower.bean.vo.MiniMsgCustomerRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>客户订阅消息信息表列表结果</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgCustomerRecordListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户订阅消息信息表列表结果
     */
    @Schema(description = "客户订阅消息信息表列表结果")
    private List<MiniMsgCustomerRecordVO> miniMsgCustomerRecordVOList;
}
