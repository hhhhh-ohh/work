package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 邀请记录导出查询参数
 * Created by of2975 on 2019/4/30.
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInviteNewExportResponse extends BasicResponse {
    /**
     * 邀新记录导出结果
     */
    @Schema(description = "邀新记录导出结果")
    private List<DistributionInviteNewForPageVO> recordList;
}
