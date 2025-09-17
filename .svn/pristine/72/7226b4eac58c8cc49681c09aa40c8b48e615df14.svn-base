package com.wanmi.sbc.marketing.api.request.giftcard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardContactType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardScopeType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author wur
 * @className GiftCardNewRequest
 * @description 礼品卡编辑
 * @date 2022/12/8 16:29
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCardNewRequest extends BaseRequest {

    private static final long serialVersionUID = -2952406761735818859L;

    @Schema(description = "礼品卡名称")
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;

    @Schema(description = "背景类型，0：颜色 1：图片")
    @NotNull
    private DefaultFlag backgroundType;

    @Schema(description = "背景封面信息")
    @NotBlank
    @Size(min = 1, max = 200)
    private String backgroundDetail;

    @Schema(description = "面值")
    @Max(999999L)
    @Min(1L)
    private Long parValue;

    @Schema(description = "库存标识 0：有限制 1:无限制")
    @NotNull
    private DefaultFlag stockType;

    @Schema(description = "库存")
    @Max(999999L)
    @Min(1L)
    private Long stock;

    @Schema(description = "有效期类型")
    private ExpirationType expirationType;

    @Schema(description = "指定领取多少月内有效， rangeDayType = 1 必填")
    @Max(99L)
    @Min(1L)
    private Long rangeMonth;

    @Schema(description = "指定具体失效时间， rangeDayType = 2 必填")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expirationTime;

    @Schema(description = "指定目标商品类型")
    private GiftCardScopeType scopeType;

    @Schema(description = "目标商品类型")
    private List<String> scopeIdList;

    @Schema(description = "适用须知")
    @NotBlank
    private String useDesc;

    @Schema(description = "联系方式类型")
    @NotNull
    private GiftCardContactType contactType;

    @Schema(description = "联系方式")
    @NotBlank
    @Size(min = 1, max = 20)
    private String contactPhone;

    @Schema(description = "礼品卡类型")
    @NotNull
    private GiftCardType giftCardType;

    @Schema(description = "适用商品数量 -1可选一种 -99可全选 其他代表N种")
    private Integer scopeGoodsNum;

    @Override
    public void checkParam() {

        if (Objects.equals(DefaultFlag.NO, this.stockType) && Objects.isNull(this.stock)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (Objects.equals(ExpirationType.SPECIFIC_TIME, this.expirationType)
                && (Objects.isNull(this.getExpirationTime())
                        || LocalDateTime.now().isAfter(this.getExpirationTime()))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (Objects.equals(ExpirationType.MONTH, this.expirationType)
                && Objects.isNull(this.rangeMonth)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        // 验证固定电话参数
        if (GiftCardContactType.FIXED_PHONE.equals(this.contactType)) {
            String[] phoneList = contactPhone.split("-");
            if (phoneList.length!=2 || phoneList[0].length() > 4 || phoneList[1].length() > 8) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }


        if (Objects.equals(GiftCardType.CASH_CARD, giftCardType)){
            if (parValue == null || scopeType == null){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (!Objects.equals(GiftCardScopeType.ALL, this.scopeType)
                    && CollectionUtils.isEmpty(this.scopeIdList)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else {
            if (scopeGoodsNum == null || CollectionUtils.isEmpty(scopeIdList)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}
