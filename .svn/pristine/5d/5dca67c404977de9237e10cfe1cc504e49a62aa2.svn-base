package com.wanmi.sbc.setting.api.response.pickupsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>pickup_setting分页结果</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupSettingPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * pickup_setting分页结果
     */
    @Schema(description = "pickup_setting分页结果")
    private MicroServicePage<PickupSettingVO> pickupSettingVOS;
}
