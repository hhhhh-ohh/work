package com.wanmi.sbc.setting.api.response.pickupsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.PickupEmployeeRelaVO;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据id查询任意（包含已删除）pickup_setting信息response</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupSettingByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * pickup_setting信息
     */
    @Schema(description = "pickup_setting信息")
    private PickupSettingVO pickupSettingVO;

    /**
     * pickup_employee_rela信息
     */
    @Schema(description = "pickup_employee_rela信息")
    private List<PickupEmployeeRelaVO> pickupEmployeeRelaVOS;
}
