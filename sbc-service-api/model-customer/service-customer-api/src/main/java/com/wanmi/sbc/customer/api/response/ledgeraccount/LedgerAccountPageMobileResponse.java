package com.wanmi.sbc.customer.api.response.ledgeraccount;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.LedgerRelMobileVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className LedgerAccountPageMobileResponse
 * @description
 * @date 2022/9/14 11:38 AM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerAccountPageMobileResponse implements Serializable {
    private static final long serialVersionUID = -1450536515414652830L;

    /**
     * 分页数据
     */
    @Schema(description = "分页数据")
    private MicroServicePage<LedgerRelMobileVO> ledgerRelMobileVOS;
}
