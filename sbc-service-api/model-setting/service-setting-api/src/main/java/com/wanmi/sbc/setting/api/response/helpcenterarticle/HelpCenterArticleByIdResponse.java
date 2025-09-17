package com.wanmi.sbc.setting.api.response.helpcenterarticle;

import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）帮助中心文章信息信息response</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帮助中心文章信息信息
     */
    @Schema(description = "帮助中心文章信息信息")
    private HelpCenterArticleVO helpCenterArticleVO;
}
