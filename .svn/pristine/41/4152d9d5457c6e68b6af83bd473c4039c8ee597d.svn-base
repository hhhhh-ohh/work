package com.wanmi.sbc.order.api.response.payingmemberpayrecord;

import com.wanmi.sbc.order.bean.vo.PayingMemberPayRecordPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费会员支付记录表列表结果</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPayRecordExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 付费会员支付记录表列表结果
     */
    @Schema(description = "付费会员支付记录表列表结果")
    private List<PayingMemberPayRecordPageVO> payingMemberPayRecordList;
}
