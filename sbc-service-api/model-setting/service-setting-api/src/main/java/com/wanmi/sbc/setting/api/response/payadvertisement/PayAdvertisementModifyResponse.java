package com.wanmi.sbc.setting.api.response.payadvertisement;

import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>支付广告页配置修改结果</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAdvertisementModifyResponse implements Serializable {

    private static final long serialVersionUID = 5728180734460052444L;
    /**
     * 已修改的支付广告页配置信息
     */
    @Schema(description = "已修改的支付广告页配置信息")
    private PayAdvertisementVO payAdvertisementVO;
}
