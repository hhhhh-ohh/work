package com.wanmi.sbc.setting.api.response.pagemanage;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.PageInfoExtendVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>页面投放数据</p>
 * @author dyt
 * @date 2019-11-05 16:10:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoExtendByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 页面投放数据
     */
    @Schema(description = "页面投放数据")
    private PageInfoExtendVO pageInfoExtend;
}
