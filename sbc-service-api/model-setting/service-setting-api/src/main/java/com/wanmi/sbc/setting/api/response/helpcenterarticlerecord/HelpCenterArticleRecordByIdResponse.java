package com.wanmi.sbc.setting.api.response.helpcenterarticlerecord;

import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleRecordVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）帮助中心文章记录信息response</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleRecordByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帮助中心文章记录信息
     */
    @Schema(description = "帮助中心文章记录信息")
    private HelpCenterArticleRecordVO helpCenterArticleRecordVO;
}
