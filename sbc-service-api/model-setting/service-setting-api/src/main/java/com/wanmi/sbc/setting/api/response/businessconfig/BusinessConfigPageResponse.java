package com.wanmi.sbc.setting.api.response.businessconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.BusinessConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>招商页设置分页结果</p>
 * @author lq
 * @date 2019-11-05 16:09:10
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessConfigPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 招商页设置分页结果
     */
    @Schema(description = "招商页设置分页结果")
    private MicroServicePage<BusinessConfigVO> businessConfigVOPage;
}
