package com.wanmi.sbc.marketing.api.response.giftcard;

import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchPageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>礼品卡批次列表结果</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBatchExportResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 礼品卡批次列表结果
     */
    @Schema(description = "礼品卡批次列表结果")
    private List<GiftCardBatchPageVO> giftCardBatchList;
}
