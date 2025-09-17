package com.wanmi.sbc.setting.api.response.recommendcate;

import com.wanmi.sbc.setting.bean.vo.RecommendCateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）笔记分类表信息response</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateByIdResponse implements Serializable {

    private static final long serialVersionUID = -3888211007030800821L;

    /**
     * 笔记分类表信息
     */
    @Schema(description = "笔记分类表信息")
    private RecommendCateVO recommendCateVO;
}
