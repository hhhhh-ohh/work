package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午5:21 2019/6/13
 * @Description:
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorLevelListAllResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 分销员等级列表
     */
    @Schema(description = "分销员等级列表")
    private List<DistributorLevelVO> distributorLevelList;

}
