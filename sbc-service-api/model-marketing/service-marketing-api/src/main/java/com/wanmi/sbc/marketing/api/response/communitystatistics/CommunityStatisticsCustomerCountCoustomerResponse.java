package com.wanmi.sbc.marketing.api.response.communitystatistics;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>社区团购活动统计会员信息表列表结果</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatisticsCustomerCountCoustomerResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分组发展人数
     */
    @Schema(description = "分组发展人数 <团长id, 发展人数>")
    private Map<String, Long> result;
}
