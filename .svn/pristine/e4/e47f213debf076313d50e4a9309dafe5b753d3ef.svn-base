package com.wanmi.sbc.empower.api.response.sellplatform.promoter;

import com.wanmi.sbc.empower.bean.vo.sellplatform.promoter.PlatformPromoterInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @description  WxChannelsPromoterListResponse 查询推广员
 * @author  wur
 * @date: 2022/4/13 15:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class PlatformPromoterListResponse implements Serializable {

    /**
     * 推广员列表
     */
    @Schema(description = "推广员列表")
    private List<PlatformPromoterInfoVO> promoters;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Long total_num;
}
