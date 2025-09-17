package com.wanmi.sbc.order.api.response.leadertradedetail;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.LeaderTradeDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>团长订单分页结果</p>
 * @author Bob
 * @date 2023-08-03 14:16:52
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderTradeDetailPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 团长订单分页结果
     */
    @Schema(description = "团长订单分页结果")
    private MicroServicePage<LeaderTradeDetailVO> leaderTradeDetailVOPage;
}
