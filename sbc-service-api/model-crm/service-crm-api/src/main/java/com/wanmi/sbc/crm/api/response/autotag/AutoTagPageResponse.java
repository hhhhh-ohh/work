package com.wanmi.sbc.crm.api.response.autotag;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.crm.bean.vo.AutoTagVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>自动标签分页结果</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoTagPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 自动标签分页结果
     */
    @Schema(description = "自动标签分页结果")
    private MicroServicePage<AutoTagVO> autoTagVOPage;
}
