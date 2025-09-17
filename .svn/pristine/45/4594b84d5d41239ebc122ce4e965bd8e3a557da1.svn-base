package com.wanmi.sbc.customer.api.response.payingmemberprice;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.PayingMemberPriceVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>付费设置表分页结果</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayingMemberPricePageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 付费设置表分页结果
     */
    @Schema(description = "付费设置表分页结果")
    private MicroServicePage<PayingMemberPriceVO> payingMemberPriceVOPage;
}
