package com.wanmi.sbc.customer.api.request.points;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>会员积分明细通用查询请求参数</p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPointsDetailQueryRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Schema(description = "用户id", hidden = true)
    private String customerId;

    /**
     * 用户id列表
     */
    @Schema(description = "用户id列表", hidden = true)
    private List<String> customerIdList;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号", hidden = true)
    private String customerAccount;

    /**
     * 用户名
     */
    @Schema(description = "用户名", hidden = true)
    private String customerName;

    /**
     * 操作类型 0:扣除 1:增长
     */
    @Schema(description = "操作类型 0:扣除 1:增长", hidden = true)
    private OperateType type;

    /**
     * 会员积分业务类型 0签到 1注册 2分享商品 3分享注册 4分享购买  5评论商品 6晒单 7上传头像/完善个人信息 8绑定微信
     * 9添加收货地址 10关注店铺 11订单完成 12订单抵扣 13优惠券兑换 14积分兑换 15退单返还 16订单取消返还 17过期扣除
     */
    @Schema(description = "会员积分业务类型", hidden = true)
    private PointsServiceType serviceType;

    /**
     * 内容备注
     */
    @Schema(description = "内容备注", hidden = true)
    private String content;

    /**
     * 搜索条件:操作时间开始
     */
    @Schema(description = "搜索条件:操作时间开始", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime opTimeBegin;

    /**
     * 搜索条件:操作时间截止
     */
    @Schema(description = "搜索条件:操作时间截止", hidden = true)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime opTimeEnd;

    /**
     * 负责业务员
     */
    @Schema(description = "负责业务员", hidden = true)
    private String employeeId;

    /**
     * 负责业务员ID集合
     */
    @Schema(description = "负责业务员ID集合", hidden = true)
    private List<String> employeeIds;
}