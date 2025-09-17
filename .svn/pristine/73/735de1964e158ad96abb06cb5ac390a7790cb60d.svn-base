package com.wanmi.sbc.marketing.api.request.communityregionsetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * <p>社区拼团区域设置表新增参数</p>
 *
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingAddRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id", hidden = true)
    private Long storeId;

    /**
     * 区域名称
     */
    @Schema(description = "区域名称")
    @NotBlank
    private String regionName;

    /**
     * 省市区Id列表
     */
    @Schema(description = "省市区Id列表")
    private List<String> areaIdList;

    /**
     * 省市区名称列表
     */
    @Schema(description = "省市区名称列表")
    private List<String> areaNameList;

    /**
     * 自提点Id列表
     */
    @Schema(description = "自提点Id列表")
    private List<String> pickupPointIdList;

    @Override
    public void checkParam() {
        if (CollectionUtils.isEmpty(areaIdList) && CollectionUtils.isEmpty(pickupPointIdList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}