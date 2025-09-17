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
 * 有效线下账户列表响应
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidAccountListResponse extends BasicResponse {

    private static final long serialVersionUID = 5730687267277230571L;
    /**
     * 线下账户列表 {@link OfflineAccountVO}
     */
    @Schema(description = "线下账户列表")
    private List<OfflineAccountVO> voList;
}
