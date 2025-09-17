package com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsCorrelationModelSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>列表结果</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCorrelationModelSettingListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 列表结果
     */
    @Schema(description = "列表结果")
    private List<GoodsCorrelationModelSettingVO> goodsCorrelationModelSettingVOList;
}
