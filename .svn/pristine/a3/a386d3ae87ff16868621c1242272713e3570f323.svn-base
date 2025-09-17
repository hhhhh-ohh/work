package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.AccountDetailsVO;
import com.wanmi.sbc.common.base.MicroServicePage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>对账明细分页返回结构</p>
 * Created by of628-wenzhi on 2018-10-13-下午2:20.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsPageResponse extends BasicResponse {

    private static final long serialVersionUID = -1468173359010491232L;
    /**
     * 对账明细分页列表 {@link AccountDetailsVO}
     */
    @Schema(description = "对账明细分页列表")
    private MicroServicePage<AccountDetailsVO> accountDetailsVOPage;
}
