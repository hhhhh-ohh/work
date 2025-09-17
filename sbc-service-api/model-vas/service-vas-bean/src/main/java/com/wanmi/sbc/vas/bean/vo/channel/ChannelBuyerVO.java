package com.wanmi.sbc.vas.bean.vo.channel;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 购买人DTO 与BuyerVO保持一致
 * @author daiyitian
 * @date 2021/5/11 11:12
 */
@Data
@Schema
public class ChannelBuyerVO extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "购买人编号")
    private String id;

    @Schema(description = "购买人姓名")
    private String name;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "等级编号")
    private Long levelId;

    @Schema(description = "等级名称")
    private String levelName;

    @Schema(description = "标识用户是否属于当前订单所属商家",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private Boolean customerFlag;

    @Schema(description = "购买人联系电话")
    private String phone;

    @Schema(description = "买家关联的业务员id")
    private String employeeId;
}
