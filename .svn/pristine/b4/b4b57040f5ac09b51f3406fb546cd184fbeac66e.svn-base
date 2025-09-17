package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-20 14:03
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullGiftDetailDTO implements Serializable {

    private static final long serialVersionUID = 7705576839385824888L;
    /**
     *  满赠赠品Id
     */
    @Schema(description = "满赠主键Id")
    private Long giftDetailId;

    /**
     *  满赠多级促销Id
     */
    @Schema(description = "满赠多级促销Id")
    private Long giftLevelId;

    /**
     *  赠品Id
     */
    @Schema(description = "赠品Id")
    private String productId;

    /**
     *  赠品数量
     */
    @Schema(description = "赠品数量")
    private Long productNum;

    /**
     *  营销id
     */
    @Schema(description = "营销id")
    private Long marketingId;

    @Schema(description = "赠品库存")
    private Long productStock;

}
