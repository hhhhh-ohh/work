package com.wanmi.sbc.setting.api.response.cancellationreason;

import com.wanmi.sbc.setting.bean.vo.CancellationReasonVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * @date 2022/3/29 15:30
 * @description <p> 注销原因返回体 </p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancellationReasonQueryResponse implements Serializable {

    private static final long serialVersionUID = 611640901281404974L;

    @Schema(description = "注销原因集合")
    private List<CancellationReasonVO> cancellationReasonVOList;
}
