package com.wanmi.sbc.setting.api.response.helpcenterarticle;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>帮助中心文章信息分页结果</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticlePageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帮助中心文章信息分页结果
     */
    @Schema(description = "帮助中心文章信息分页结果")
    private MicroServicePage<HelpCenterArticleVO> helpCenterArticleVOPage;
}
