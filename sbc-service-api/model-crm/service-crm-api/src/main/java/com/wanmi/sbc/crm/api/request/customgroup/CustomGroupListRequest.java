package com.wanmi.sbc.crm.api.request.customgroup;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.crm.bean.enums.GroupType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-11-12
 * \* Time: 18:37
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomGroupListRequest extends BaseQueryRequest {

    private String sortType;

    @Schema(description = "分组类型，0-自定义分群；1-生命周期分群")
    private GroupType groupType = GroupType.CUSTOM;
}
