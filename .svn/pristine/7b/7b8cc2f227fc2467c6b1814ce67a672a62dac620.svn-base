package com.wanmi.sbc.vas.api.response.sellplatform.promoter;

import com.wanmi.sbc.vas.bean.vo.sellplatform.SellPlatformPromoterInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * @description SellPlatformPromoterListResponse 查询推广员
 * @author  wur
 * @date: 2022/4/18 20:00
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellPlatformPromoterListResponse implements Serializable {

    private static final long serialVersionUID = 6463109967855670808L;
    /**
     * 推广员列表
     */
    @Schema(description = "推广员列表")
    private List<SellPlatformPromoterInfoVO> promoters;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Long total_num;
}
