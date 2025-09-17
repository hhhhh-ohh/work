package com.wanmi.sbc.marketing.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: xufeng
 * @Description: 营销满返多级优惠实体
 * @Date: 2022-04-06 14:02
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullReturnDetailDTO implements Serializable {

    private static final long serialVersionUID = 7705576839385824888L;
    /**
     *  满返赠券Id
     */
    @Schema(description = "满返主键Id")
    private Long returnDetailId;

    /**
     *  满返多级促销Id
     */
    @Schema(description = "满返多级促销Id")
    private Long returnLevelId;

    /**
     *  赠券Id
     */
    @Schema(description = "赠券Id")
    private String couponId;

    /**
     *  赠券数量
     */
    @Schema(description = "赠券数量")
    private Long couponNum;

    /**
     *  营销id
     */
    @Schema(description = "营销id")
    private Long marketingId;

}
