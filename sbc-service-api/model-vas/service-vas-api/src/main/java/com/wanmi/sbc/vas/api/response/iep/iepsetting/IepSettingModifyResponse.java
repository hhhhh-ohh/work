package com.wanmi.sbc.vas.api.response.iep.iepsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>企业购设置修改结果</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IepSettingModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的企业购设置信息
     */
    @Schema(description = "已修改的企业购设置信息")
    private IepSettingVO iepSettingVO;
}
