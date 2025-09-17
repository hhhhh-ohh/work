package com.wanmi.sbc.account.api.response.finance.record;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.AccountRecordVO;
import com.wanmi.sbc.common.base.MicroServicePage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>分页查询对账记录返回结构</p>
 * Created by of628-wenzhi on 2018-10-12-下午5:48.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRecordPageResponse extends BasicResponse {

    private static final long serialVersionUID = -7449781184720281262L;
    /**
     * 分页对账记录数据结构
     */
    @Schema(description = "分页对账记录数据结构")
    private MicroServicePage<AccountRecordVO> accountRecordVOPage;
}
