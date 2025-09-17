package com.wanmi.sbc.setting.api.response.helpcenterarticlerecord;

import com.wanmi.sbc.setting.bean.vo.HelpCenterArticleRecordPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>帮助中心文章记录列表结果</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpCenterArticleRecordExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 帮助中心文章记录列表结果
     */
    @Schema(description = "帮助中心文章记录列表结果")
    private List<HelpCenterArticleRecordPageVO> helpCenterArticleRecordList;
}
