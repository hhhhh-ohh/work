package com.wanmi.sbc.marketing.api.request.giftcard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardContactType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author wur
 * @className GiftCardSaveRequest
 * @description TODO
 * @date 2022/12/8 16:29
 **/
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -2952406761735818859L;

    /**
     * 礼品卡Id
     */
    @Schema(description = "礼品卡Id")
    @NotNull
    private Long giftCardId;

    @Schema(description = "礼品卡名")
    private String name;

    @Schema(description = "库存")
    private Long stock;

    @Schema(description = "背景类型，0：颜色 1：图片")
    private DefaultFlag backgroundType;

    @Schema(description = "背景封面信息")
    @Size(min = 1,max = 255)
    private String backgroundDetail;

    @Schema(description = "指定领取多少月内有效， rangeDayType = 1 必填")
    @Max(99L)
    @Min(1L)
    private Long rangeMonth;

    @Schema(description = "指定具体失效时间， rangeDayType = 2 必填")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expirationTime;

    @Schema(description = "适用须知")
    private String useDesc;

    @Schema(description = "联系方式类型")
    private GiftCardContactType contactType;

    @Schema(description = "联系方式")
    private String contactPhone;

    @Override
    public void checkParam() {
        if (StringUtils.isNotEmpty(name) && name.length() > 20) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (Objects.nonNull(this.stock) && this.stock.compareTo(999999L) > 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (StringUtils.isNotEmpty(contactPhone) && contactPhone.length() > 20) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 验证固定电话参数
        if (GiftCardContactType.FIXED_PHONE.equals(this.contactType)) {
            String[] phoneList = contactPhone.split("-");
            if (phoneList.length!=2 || phoneList[0].length() > 4 || phoneList[1].length() > 8) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}