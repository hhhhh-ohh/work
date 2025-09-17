package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.ElectronicSendRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>卡密发放记录表分页结果</p>
 * @author 许云鹏
 * @date 2022-01-26 17:37:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicSendRecordPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 卡密发放记录表分页结果
     */
    @Schema(description = "卡密发放记录表分页结果")
    private MicroServicePage<ElectronicSendRecordVO> electronicSendRecordVOPage;
}
