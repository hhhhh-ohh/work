package com.wanmi.sbc.setting.api.response.systemresource;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lvzhenwei
 * @className SystemResourceMaxSortResponse
 * @description TODO
 * @date 2023/4/24 4:51 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceMaxSortResponse extends BasicResponse {

    /**
     * 最大排序号
     */
    @Schema(description = "最大排序号")
    private Integer sort;
}
