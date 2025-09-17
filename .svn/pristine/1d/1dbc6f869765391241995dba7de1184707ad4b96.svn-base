package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.AccountDetailsVO;
import com.wanmi.sbc.account.bean.vo.RefundDetailsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>对账明细(收入/退款)导出返回结构</p>
 * Created by of628-wenzhi on 2018-10-13-下午2:34.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsExportResponse extends BasicResponse {

    private static final long serialVersionUID = -8054466287933815475L;
    /**
     * 对账明细（收入/退款）导出返回的数据结构 {@link AccountDetailsVO}
     * 如果是退款，数据类型则为 {@link RefundDetailsVO}
     */
    @Schema(description = "对账明细（收入/退款）导出结构")
    private List<AccountDetailsVO> accountDetailsVOList;
}
