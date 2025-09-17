package com.wanmi.sbc.order.api.response.payingmemberpayrecord;

import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）付费会员支付记录表信息response</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPayRecordByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 付费会员支付记录表信息
     */
    @Schema(description = "付费会员支付记录表信息")
    private PayingMemberPayRecordVO payingMemberPayRecordVO;
}
