package com.wanmi.sbc.crm.api.response.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.GoodsCorrelationModelSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

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
