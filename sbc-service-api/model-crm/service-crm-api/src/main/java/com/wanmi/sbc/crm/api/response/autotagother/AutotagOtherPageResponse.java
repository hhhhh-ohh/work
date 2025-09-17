package com.wanmi.sbc.crm.api.response.autotagother;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.crm.bean.vo.AutotagOtherVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>偏好标签明细分页结果</p>
 * @author dyt
 * @date 2020-03-11 14:58:07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutotagOtherPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    @Schema(description = "标签明细分页结果")
    private MicroServicePage<AutotagOtherVO> autotagOtherVOS;
}
