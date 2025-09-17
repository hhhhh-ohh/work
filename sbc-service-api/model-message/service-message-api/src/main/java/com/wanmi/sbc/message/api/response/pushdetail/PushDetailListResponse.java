package com.wanmi.sbc.message.api.response.pushdetail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.PushDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>推送详情列表结果</p>
 * @author Bob
 * @date 2020-01-08 17:16:17
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushDetailListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 推送详情列表结果
     */
    @Schema(description = "推送详情列表结果")
    private List<PushDetailVO> pushDetailVOList;
}
