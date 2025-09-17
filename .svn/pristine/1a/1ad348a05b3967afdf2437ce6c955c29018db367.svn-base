package com.wanmi.sbc.setting.api.response.recommendcate;

import com.wanmi.sbc.setting.bean.vo.RecommendCatePageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>笔记分类表列表结果</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateExportResponse implements Serializable {

    private static final long serialVersionUID = -6651168627291699580L;

    /**
     * 笔记分类表列表结果
     */
    @Schema(description = "笔记分类表列表结果")
    private List<RecommendCatePageVO> recommendCateList;
}
