package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerRankingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>用户分销排行榜初始化数据</p>
 * @author lq
 * @date 2019-04-19 10:05:05
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCustomerRankingInitResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的用户分销排行榜信息
     */
    private List<DistributionCustomerRankingVO> distributionCustomerRankingVOList;
}
