package com.wanmi.sbc.setting.api.request.pagemanage;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema
public class GoodsInfoExtendModifyRequest extends SettingBaseRequest {


    private static final long serialVersionUID = 1L;
    /**
     * pageId
     */
    @Schema(description = "goodsInfoId")
    @NotBlank
    private String goodsInfoId;

    /**
     * 页面背景图
     */
    @Schema(description = "页面背景图")
    private String backGroundPic;

    /**
     * 使用类型  0:小程序 1:二维码
     */
    @Schema(description = "使用类型  0:小程序 1:二维码 2:H5")
    private Integer useType;

    /**
     * 来源
     */
    @Schema(description = "来源")
    private List<String> sources;

    @Override
    public void checkParam() {
        if (CollectionUtils.isNotEmpty(sources)) {
            sources.forEach(source -> {
                if(StringUtils.isNotBlank(source)) {
                    //只允许字母、数字、上下划线和*
                    boolean matches = StringUtils.defaultString(source, StringUtils.EMPTY).matches("^[A-Za-z0-9\\-\\_\\*]+");
                    if (!matches) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                }
            });
        }
    }



}
