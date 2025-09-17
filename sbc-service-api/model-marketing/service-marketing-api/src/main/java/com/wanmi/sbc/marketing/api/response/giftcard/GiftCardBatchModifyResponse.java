package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>礼品卡批次修改结果</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBatchModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的礼品卡批次信息
     */
    @Schema(description = "已修改的礼品卡批次信息")
    private GiftCardBatchVO giftCardBatchVO;
}
