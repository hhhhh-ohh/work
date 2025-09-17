package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-11-15
 * \* Time: 14:09
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class CustomGroupRelVo extends BasicResponse {

    private Integer groupId;

    private String groupName;

    private String definition;

    private String customerId;

}
