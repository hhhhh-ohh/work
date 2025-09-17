package com.wanmi.sbc.setting.api.request.recommendcate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.vo.RecommendCateSortVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 王超
 * @Date: 2022/5/16
 * @Description: 内容分类排序request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendCateSortRequest extends BaseRequest {

    private static final long serialVersionUID = -3986782696398283510L;

    /**
     * 分类排序
     */
    @Schema(description = "分类排序")
    @NotNull
    List<RecommendCateSortVO> recommendCateSortVOS;
}
