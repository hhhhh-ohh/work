package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailJoinVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>礼品卡单卡明细分页结果</p>
 * @author edy
 * @date 2023-10-24 20:38:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailJoinPageResponse implements Serializable {
    private static final long serialVersionUID = -5339439361659110898L;

    /**
     * 礼品卡单卡明细分页结果
     */
    @Schema(description = "礼品卡单卡明细分页结果")
    private MicroServicePage<GiftCardDetailJoinVO> GiftCardDetailJoinVOPage;
}
