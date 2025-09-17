package com.wanmi.sbc.setting.api.response.helpcenterarticlecate;

import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleCateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）帮助中心文章信息信息response</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleCateByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帮助中心文章信息信息
     */
    @Schema(description = "帮助中心文章信息信息")
    private HelpCenterArticleCateVO helpCenterArticleCateVO;
}
