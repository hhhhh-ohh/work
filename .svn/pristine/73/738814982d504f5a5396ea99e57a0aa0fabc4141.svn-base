package com.wanmi.sbc.elastic.api.response.distributioninvitenew;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 邀请记录分页查询参数
 *
 * @author feitingting
 * @date 2019/2/21
 */
@Schema
@Data
public class EsDistributionInviteNewPageResponse extends BasicResponse {
    /**
     * 会员分页
     */
    @Schema(description = "邀新记录类，分页")
    private List<DistributionInviteNewForPageVO> recordList;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private Long total;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Integer currentPage;
}