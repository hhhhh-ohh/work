package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.AccountGatherVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>对账列表汇总response</p>
 * Created by of628-wenzhi on 2018-10-13-下午2:10.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountGatherListResponse extends BasicResponse {

    private static final long serialVersionUID = 8046442669225572303L;
    /**
     * 对账汇总列表 {@link AccountGatherVO}
     */
    @Schema(description = "对账汇总列表")
    private List<AccountGatherVO> accountGatherVOList;
}
