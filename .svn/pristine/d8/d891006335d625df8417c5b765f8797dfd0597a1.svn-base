package com.wanmi.sbc.goods.api.response.flashsaleactivity;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.FlashPromotionActivityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>限时购活动列表结果</p>
 * @author xufeng
 * @date 2022-02-15 10:11:15
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashPromotionActivityPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 秒杀活动列表结果
     */
    @Schema(description = "秒杀活动列表结果")
    private MicroServicePage<FlashPromotionActivityVO> flashPromotionActivityVOS;
}
