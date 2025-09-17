package com.wanmi.sbc.setting.api.response.recommend;

import com.wanmi.sbc.setting.bean.vo.RecommendPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>种草信息表列表结果</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 种草信息表列表结果
     */
    @Schema(description = "种草信息表列表结果")
    private List<RecommendPageVO> recommendList;
}
