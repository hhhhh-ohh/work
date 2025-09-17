package com.wanmi.sbc.goods.api.response.flashsaleactivity;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.FlashSaleActivityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>抢购活动列表结果</p>
 * @author yxz
 * @date 2019-06-11 14:54:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleActivityListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 抢购活动列表结果
     */
    @Schema(description = "抢购活动列表结果")
    private List<FlashSaleActivityVO> flashSaleActivityVOList;

    /**
     * 最近场次日期
     */
    private String recentDate;

    /**
     * 最近场次时间
     */
    private String recentTime;
}
