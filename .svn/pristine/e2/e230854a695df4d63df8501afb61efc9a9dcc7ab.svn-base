package com.wanmi.sbc.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanmi.sbc.common.annotation.ApiEnum;
import com.wanmi.sbc.common.annotation.ApiEnumProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: songhanlin
 * @Date: Created In 14:13 2020/3/2
 * @Description: 增值服务枚举
 */
@ApiEnum(dataType = "java.lang.String")
public enum VASConstants {

    /**
     * 暂时无用, 有计划合并进来
     */
    @ApiEnumProperty("增值服务-CRM-设置")
    VAS_CRM_SETTING("vas_crm_setting"),


    /**
     * 增值服务-企业购-设置
     */
    @ApiEnumProperty("增值服务-企业购-设置")
    VAS_IEP_SETTING("vas_iep_setting"),

    /**
     * 增值服务-linkedmall-设置
     */
    @ApiEnumProperty("第三方平台-linkedMall")
    THIRD_PLATFORM_LINKED_MALL("third_platform_linked_mall"),

    /**
     * 增值服务-VOP-设置
     */
    @ApiEnumProperty("第三方平台-vop")
    THIRD_PLATFORM_VOP("third_platform_vop"),

    /**
     * 增值服务-智能推荐-设置
     */
    @ApiEnumProperty("增值服务-智能推荐-设置")
    VAS_RECOMMEND_SETTING("vas_recommend_setting"),

    /**
     * 增值服务-o2o
     */
    @ApiEnumProperty("增值服务-o2o-设置")
    VAS_O2O_SETTING("vas_o2o_setting"),

    /**
     * 增值服务-微信视频号
     */
    @ApiEnumProperty("增值服务-微信视频号")
    VAS_WECHAT_CHANNELS("vas_wechat_channels");

    private final String value;

    VASConstants(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }

    @JsonCreator
    public static VASConstants fromValue(String value) {
        for (VASConstants constants : VASConstants.values()) {
            if (StringUtils.equals(value, constants.value)) {
                return constants;
            }
        }
        return null;
    }

    public static VASConstants getBySellPlatformType(SellPlatformType sellPlatformType) {
        switch (sellPlatformType.toValue()){
            case 0:
                return VAS_WECHAT_CHANNELS;
            default:
                return null;
        }
    }
}
