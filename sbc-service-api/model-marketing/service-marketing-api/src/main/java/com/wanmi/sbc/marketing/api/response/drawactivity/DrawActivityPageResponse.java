package com.wanmi.sbc.marketing.api.response.drawactivity;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.DrawActivityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>抽奖活动表分页结果</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawActivityPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 抽奖活动表分页结果
     */
    @Schema(description = "抽奖活动表分页结果")
    private MicroServicePage<DrawActivityVO> drawActivityVOPage;
}
