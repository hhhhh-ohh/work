package com.wanmi.sbc.customer.api.response.agent;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @program: sbc-micro-service
 * @description: 审核代理商返回结果
 * @create: 2020-04-01 15:05
 **/
@Schema
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class AuditAgentResponse extends BasicResponse {

}
