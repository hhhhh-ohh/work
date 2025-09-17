package com.wanmi.sbc.marketing.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.bean.enums.CommissionPriorityType;
import com.wanmi.sbc.marketing.bean.enums.DistributionLimitType;
import com.wanmi.sbc.marketing.bean.enums.RegisterLimitType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: gaomuwei
 * @Date: Created In 上午9:43 2019/2/23
 * @Description: 分销基础设置请求对象
 */
@Schema
@Data
public class DistributionBasicSettingSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -2100769189448293816L;

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Schema(description = "是否开启社交分销")
    @NotNull
    private DefaultFlag openFlag;

    /**
     * 分销员名称
     */
    @Schema(description = "分销员名称")
    @NotBlank
    @Size(min = 1,max = 5)
    private String distributorName;

    /**
     * 是否开启分销小店 0：关闭，1：开启
     */
    @Schema(description = "是否开启分销小店")
    @NotNull
    private DefaultFlag shopOpenFlag;

    /**
     * 小店名称
     */
    @Schema(description = "小店名称")
    private String shopName;

    /**
     * 店铺分享图片
     */
    @Schema(description = "店铺分享图片")
    private String shopShareImg;

    /**
     * 注册限制
     */
    @Schema(description = "注册限制")
    @NotNull
    private RegisterLimitType registerLimitType;

    /**
     * 基础邀新奖励限制 0：不限，1：仅限有效邀新
     */
    @Schema(description = "基础邀新奖励限制")
    @NotNull
    private DistributionLimitType baseLimitType;

    /**
     * 佣金返利优先级
     */
    @Schema(description = "佣金返利优先级")
    @NotNull
    private CommissionPriorityType commissionPriorityType;

    /**
     * 是否开启分销商品审核 0：关闭，1：开启
     */
    @Schema(description = "是否开启分销商品审核")
    @NotNull
    private DefaultFlag goodsAuditFlag;

    /**
     * 分销业绩规则说明
     */
    @Schema(description = "分销业绩规则说明")
    private String performanceDesc;

    @Override
    public void checkParam() {

        if (shopOpenFlag == DefaultFlag.YES) {

            if (StringUtils.isEmpty(shopName)  || shopName.length() > 5) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (StringUtils.isEmpty(shopShareImg)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

        }

    }

}
