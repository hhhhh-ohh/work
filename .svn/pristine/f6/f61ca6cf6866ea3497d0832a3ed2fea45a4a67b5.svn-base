package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>分销设置缓存VO</p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionSettingCacheVO extends BasicResponse {

    private static final long serialVersionUID = -377128448597230486L;

    /**
     * 分销配置
     */
    @Schema(description = "分销配置")
    private DistributionSettingVO distributionSetting;

    /**
     * 分销员等级列表
     */
    @Schema(description = "分类员等级列表")
    private List<DistributorLevelVO> distributorLevels = new ArrayList<>();

}
