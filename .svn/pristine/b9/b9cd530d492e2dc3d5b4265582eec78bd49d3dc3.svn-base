package com.wanmi.sbc.setting.api.response.systemprivacypolicy;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.bean.enums.PrivacyType;
import com.wanmi.sbc.setting.bean.vo.SystemPrivacyPolicyVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>隐私政策信息response</p>
 * @author yangzhen
 * @date 2020-09-23 14:52:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemPrivacyPolicyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 隐私政策id
     */
    @Schema(description = "隐私政策id")
    private String privacyPolicyId;

    /**
     * 隐私政策
     */
    @Schema(description = "隐私政策")
    private String privacyPolicy;

    /**
     * 隐私政策弹窗
     */
    @Schema(description = "隐私政策弹窗")
    private String privacyPolicyPop;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updatePerson;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 是否删除标志 0：否，1：是
     */
    @Schema(description = "是否删除标志 0：否，1：是")
    private DeleteFlag delFlag;

    /**
     * 隐私政策类型 0:APP 1:小程序
     */
    @Schema(description = "隐私政策类型 0:APP 1:小程序")
    private PrivacyType privacyType;
}
