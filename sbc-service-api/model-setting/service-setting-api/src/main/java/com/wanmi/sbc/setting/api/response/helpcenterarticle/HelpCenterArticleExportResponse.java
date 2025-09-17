package com.wanmi.sbc.setting.api.response.helpcenterarticle;

import com.wanmi.sbc.setting.bean.vo.HelpCenterArticlePageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>帮助中心文章信息列表结果</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帮助中心文章信息列表结果
     */
    @Schema(description = "帮助中心文章信息列表结果")
    private List<HelpCenterArticlePageVO> helpCenterArticleList;
}
