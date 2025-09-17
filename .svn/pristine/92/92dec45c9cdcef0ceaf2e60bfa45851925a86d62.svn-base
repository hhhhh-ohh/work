package com.wanmi.sbc.marketing.api.response.communitysetting;

import com.wanmi.sbc.marketing.bean.vo.CommunitySettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）社区拼团商家设置表信息response</p>
 * @author dyt
 * @date 2023-07-20 11:30:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySettingByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区拼团商家设置表信息
     */
    @Schema(description = "社区拼团商家设置表信息")
    private CommunitySettingVO communitySetting;
}
