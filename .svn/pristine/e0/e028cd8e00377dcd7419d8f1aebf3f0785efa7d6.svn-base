package com.wanmi.sbc.marketing.api.response.plugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-27
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingPluginGetCustomerLevelsResponse extends BasicResponse {

    private static final long serialVersionUID = 949414125676798005L;

    @Schema(description = "店铺会员等级map<key为店铺id，value为会员等级>")
    private HashMap<Long, CommonLevelVO> commonLevelVOMap;
}
