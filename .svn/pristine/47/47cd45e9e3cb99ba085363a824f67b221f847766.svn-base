package com.wanmi.sbc.setting.api.response.recommendcate;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.RecommendCateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>笔记分类表分页结果</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCatePageResponse implements Serializable {

    private static final long serialVersionUID = -8041463634303156231L;

    /**
     * 笔记分类表分页结果
     */
    @Schema(description = "笔记分类表分页结果")
    private MicroServicePage<RecommendCateVO> recommendCateVOPage;
}
