package com.wanmi.sbc.system.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description 查询订单设置基本信息
 * @author malianfeng
 * @date 2022/11/17 10:40
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemOrderConfigSimplifyQueryResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 买家自助修改收货地址开关
     */
    @Schema(description = "买家自助修改收货地址开关")
    private Integer buyerModifyConsigneeFlag;
}
