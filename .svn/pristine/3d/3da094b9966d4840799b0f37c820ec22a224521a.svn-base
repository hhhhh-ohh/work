package com.wanmi.sbc.vas.api.response.iep.iepsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>企业购设置分页结果</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IepSettingPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 企业购设置分页结果
     */
    @Schema(description = "企业购设置分页结果")
    private MicroServicePage<IepSettingVO> iepSettingVOPage;
}
