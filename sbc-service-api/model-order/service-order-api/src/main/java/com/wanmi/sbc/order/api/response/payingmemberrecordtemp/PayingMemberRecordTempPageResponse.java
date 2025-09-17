package com.wanmi.sbc.order.api.response.payingmemberrecordtemp;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordTempVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费记录临时表分页结果</p>
 * @author zhanghao
 * @date 2022-05-13 15:28:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberRecordTempPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 付费记录临时表分页结果
     */
    @Schema(description = "付费记录临时表分页结果")
    private MicroServicePage<PayingMemberRecordTempVO> payingMemberRecordTempVOPage;
}
