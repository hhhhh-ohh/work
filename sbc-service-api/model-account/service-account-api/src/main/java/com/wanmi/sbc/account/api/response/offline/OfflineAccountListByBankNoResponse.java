package com.wanmi.sbc.account.api.response.offline;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.account.bean.vo.OfflineAccountVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 线下账户获取响应
 */
@Schema
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OfflineAccountListByBankNoResponse extends BasicResponse {

    private static final long serialVersionUID = 6168696034660328160L;
    /**
     * 线下账户VO列表 {@link OfflineAccountVO}
     */
    @Schema(description = "线下账户VO列表")
    private List<OfflineAccountVO> voList;
}
