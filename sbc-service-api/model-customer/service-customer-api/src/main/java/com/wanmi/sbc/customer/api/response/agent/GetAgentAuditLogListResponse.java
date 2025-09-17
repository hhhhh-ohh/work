package com.wanmi.sbc.customer.api.response.agent;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.AgentAuditLogBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


/**
 * @program: sbc-micro-service
 * @description: 审核记录代理商返回结果
 * @create: 2020-04-01 15:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAgentAuditLogListResponse extends BasicResponse {

    @Schema(name = "审核记录列表")
    private List<AgentAuditLogBaseVO> list;

}
