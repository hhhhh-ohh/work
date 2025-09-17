package com.wanmi.sbc.crm.api.request.crmgroup;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.crm.api.request.CrmBaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-12-9
 * \* Time: 16:04
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \会员分组信息查询
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrmGroupRequest extends BaseQueryRequest {

    @Schema(description = "系统人群ids")
    private List<Long> sysGroupList;


    @Schema(description = "自定义人群ids")
    private List<Long> customGroupList;

}
