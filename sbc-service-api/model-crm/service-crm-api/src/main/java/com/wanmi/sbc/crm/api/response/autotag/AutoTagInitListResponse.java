package com.wanmi.sbc.crm.api.response.autotag;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.AutoTagInitVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>自动标签列表结果</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoTagInitListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 自动标签列表结果
     */
    @Schema(description = "自动标签列表结果")
    private List<AutoTagInitVO> autoTagVOList;
}
