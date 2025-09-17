package com.wanmi.sbc.customer.api.request.growthvalue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>客户增长值明细表新增参数</p>
 *
 * @author yang
 * @since 2019/2/22
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGrowthValueAddRequest extends CustomerBaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @NotNull
    @Length(max = 32)
    @Schema(description = "用户id")
    private String customerId;

    /**
     * 操作类型 0:扣除 1:增长
     */
    @NotNull
    @Schema(description = "操作类型")
    private OperateType type;

    /**
     * 业务类型 0签到 1注册 2分享商品 3分享注册 4分享购买 5评论商品 6晒单 7上传头像/完善个人信息 8绑定微信 9添加收货地址 10关注店铺 11订单完成'
     */
    @NotNull
    @Schema(description = "业务类型")
    private GrowthValueServiceType serviceType;

    /**
     * 成长值
     */
    @Schema(description = "成长值")
    private Long growthValue;

    /**
     * 相关单号
     */
    @Length(max = 45)
    @Schema(description = "相关单号")
    private String tradeNo;

    /**
     * 内容备注
     */
    @Schema(description = "内容备注")
    private String content;

    /**
     * 操作时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime opTime;

}
