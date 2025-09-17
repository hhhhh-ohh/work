package com.wanmi.sbc.marketing.api.response.communityassist;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>分组帮卖团长人数 <活动id, 帮卖团长人数></p>
 * @author dyt
 * @date 2023-08-01 15:45:58
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityAssistRecordCountLeaderResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分组帮卖团长人数
     */
    @Schema(description = "分组帮卖团长人数 <活动id, 帮卖团长人数>")
    private Map<String, Long> result;
}
