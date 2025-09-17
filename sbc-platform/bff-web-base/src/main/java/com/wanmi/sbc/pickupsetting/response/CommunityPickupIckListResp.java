package com.wanmi.sbc.pickupsetting.response;

import com.wanmi.sbc.marketing.bean.enums.CommunityLogisticsType;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author edz
 * @className CommunityPickupIckListResp
 * @description TODO
 * @date 2023/8/4 16:04
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityPickupIckListResp implements Serializable {
    @Schema(description = "团长自提点表列表结果")
    private List<PickupSettingVO> pickupSettingVOList;

    @Schema(description = "物流方式 0:自提 1:快递")
    private List<CommunityLogisticsType> logisticsTypes;
}
