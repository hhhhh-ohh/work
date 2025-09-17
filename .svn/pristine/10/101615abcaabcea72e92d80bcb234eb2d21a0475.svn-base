package com.wanmi.sbc.setting.api.response.payadvertisement;

import com.wanmi.sbc.setting.bean.vo.PayAdvertisementStoreVO;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）支付广告页配置信息response</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAdvertisementByIdResponse implements Serializable {

    private static final long serialVersionUID = -8750377897507059100L;
    /**
     * 支付广告页配置信息
     */
    @Schema(description = "支付广告页配置信息")
    private PayAdvertisementVO payAdvertisementVO;

    /**
     * 支付广告页配置信息
     */
    @Schema(description = "支付广告页配置信息")
    private List<PayAdvertisementStoreVO> storeVOList;
}
