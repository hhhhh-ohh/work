package com.wanmi.sbc.setting.api.response.pickupsetting;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className PickupSettingIdsResponse
 * @description TODO
 * @author 黄昭
 * @date 2021/9/10 17:35
 **/
@Data
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class PickupSettingIdsResponse extends BasicResponse {

    private List<Long> pickupIds;
}
