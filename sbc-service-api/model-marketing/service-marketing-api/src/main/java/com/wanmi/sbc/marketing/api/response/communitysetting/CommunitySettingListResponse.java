package com.wanmi.sbc.marketing.api.response.communitysetting;

import com.wanmi.sbc.marketing.bean.vo.CommunitySettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区拼团商家设置表列表结果</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySettingListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区拼团商家设置表列表结果
     */
    @Schema(description = "社区拼团商家设置表列表结果")
    private List<CommunitySettingVO> communitySettingVOList;
}
