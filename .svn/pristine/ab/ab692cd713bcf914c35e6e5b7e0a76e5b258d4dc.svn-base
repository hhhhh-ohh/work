package com.wanmi.sbc.marketing.api.response.drawactivity;

import com.wanmi.sbc.marketing.bean.vo.DrawActivityVO;
import com.wanmi.sbc.marketing.bean.vo.DrawPrizeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @author: qiyong
 * @create: 2021/4/16 17:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawActivityGetDetailsByIdResponse implements Serializable {

    private static final long serialVersionUID = 9214311978097072018L;

    /**
     * 抽奖活动表信息
     */
    @Schema(description = "抽奖活动表信息")
    private DrawActivityVO drawActivity;

    /**
     * 奖品对象
     */
    @Schema(description = "奖品对象")
    private List<DrawPrizeVO> drawPrizeList;
}
