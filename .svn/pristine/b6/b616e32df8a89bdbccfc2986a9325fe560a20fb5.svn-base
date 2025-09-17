package com.wanmi.sbc.crm.api.response.autotag;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.AutoTagVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）自动标签信息response</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoTagByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 自动标签信息
     */
    @Schema(description = "自动标签信息")
    private AutoTagVO autoTagVO;
}
