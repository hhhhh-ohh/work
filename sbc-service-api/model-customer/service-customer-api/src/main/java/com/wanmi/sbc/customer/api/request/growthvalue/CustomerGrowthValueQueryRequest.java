package com.wanmi.sbc.customer.api.request.growthvalue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.wanmi.sbc.common.enums.OperateType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>客户成长值明细表查询请求</p>
 *
 * @author yang
 * @since 2019/2/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGrowthValueQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    private String customerId;

    /**
     * 操作类型 0:扣除 1:增长
     */
    @Schema(description = "操作类型")
    private OperateType type;

    /**
     * 成长值获取业务类型、0签到 1注册 2分享商品 3分享注册 4分享购买  5评论商品 6晒单 7上传头像/完善个人信息 8绑定微信 9添加收货地址 10关注店铺 11订单完成
     */
    @Schema(description = "成长值获取业务类型")
    private GrowthValueServiceType growthValueServiceType;

    /**
     * 内容备注
     */
    @Schema(description = "内容备注")
    private String content;

    /**
     * 大于或等于获取开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "大于或等于获取开始时间")
    private LocalDateTime gteGainStartDate;

    /**
     * 小于或等于获取结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Schema(description = "小于或等于获取结束时间")
    private LocalDateTime lteGainEndDate;

    /**
     * 是否排除成长值为0的情况
     */
    @Schema(description = "是否排除成长值为0的情况")
    private DefaultFlag excludeZeroFlag = DefaultFlag.NO;
}
