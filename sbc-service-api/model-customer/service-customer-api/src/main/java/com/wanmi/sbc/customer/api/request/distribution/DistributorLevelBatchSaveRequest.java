package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.dto.DistributorLevelDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午5:32 2019/6/13
 * @Description:
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributorLevelBatchSaveRequest extends BaseRequest {

    /**
     * 分销员等级列表
     */
    @Schema(description = "分销员等级列表")
    @NotEmpty
    private List<DistributorLevelDTO> distributorLevelList;

}
