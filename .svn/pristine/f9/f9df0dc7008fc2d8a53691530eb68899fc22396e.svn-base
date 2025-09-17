package com.wanmi.sbc.setting.api.response.flashsalesetting;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>秒杀设置被取消的列表结果</p>
 * @author yxz
 * @date 2019-06-11 13:48:53
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashSaleSettingCancelListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 秒杀设置被取消的列表结果
     */
    @Schema(description = "秒杀设置被取消的列表结果")
    private List<String> canceledTimeList;
}
