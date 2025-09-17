package com.wanmi.sbc.marketing.api.response.drawprize;

import com.wanmi.sbc.marketing.bean.vo.DrawPrizeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）抽奖活动奖品表信息response</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawPrizeByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 抽奖活动奖品表信息
     */
    @Schema(description = "抽奖活动奖品表信息")
    private DrawPrizeVO drawPrizeVO;
}
