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
 * 账号管理列表响应
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerAccountListResponse extends BasicResponse {

    private static final long serialVersionUID = -2749663902735072773L;
    /**
     * 账号管理VO列表 {@link OfflineAccountVO}
     */
    @Schema(description = "账号管理VO列表")
    private List<OfflineAccountVO> voList;
}
