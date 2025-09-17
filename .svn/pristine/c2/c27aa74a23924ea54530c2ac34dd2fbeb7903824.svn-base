package com.wanmi.sbc.setting.api.response.flashsalesetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.FlashSaleSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>秒杀设置列表结果</p>
 * @author yxz
 * @date 2019-06-11 13:48:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleSettingListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 秒杀设置列表结果
     */
    @Schema(description = "秒杀设置列表结果")
    private List<FlashSaleSettingVO> flashSaleSettingVOList;
}
