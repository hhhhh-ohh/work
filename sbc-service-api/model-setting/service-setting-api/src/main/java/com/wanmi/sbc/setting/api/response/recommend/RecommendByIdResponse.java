package com.wanmi.sbc.setting.api.response.recommend;

import com.wanmi.sbc.setting.bean.vo.RecommendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）种草信息表信息response</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 种草信息表信息
     */
    @Schema(description = "种草信息表信息")
    private RecommendVO recommendVO;
}
