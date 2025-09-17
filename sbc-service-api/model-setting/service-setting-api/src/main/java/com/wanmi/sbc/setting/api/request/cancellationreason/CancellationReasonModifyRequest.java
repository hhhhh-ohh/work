package com.wanmi.sbc.setting.api.request.cancellationreason;

import com.wanmi.sbc.setting.bean.vo.CancellationReasonVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author houshuai
 * @date 2022/3/29 15:27
 * @description <p> 注销原因 </p>
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancellationReasonModifyRequest implements Serializable {

    /**
     * 注销原因集合
     */
    @Schema(description = "注销原因集合")
    @Valid
    private List<CancellationReasonVO> cancellationReasonVOList;
}
