package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.GiftCardVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
 * @description
 * @author  wur
 * @date: 2022/12/8 18:45
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardPageResponse extends BasicResponse {

    /**
     * 分页列表
     */
    @Schema(description = "分页列表")
    private MicroServicePage<GiftCardVO> giftCardVOS;

}
