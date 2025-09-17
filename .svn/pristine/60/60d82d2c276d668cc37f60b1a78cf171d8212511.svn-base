package com.wanmi.sbc.elastic.api.response.systemresource;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author houshuai
 * @date 2020/12/14 10:21
 * @description <p> </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Schema
public class EsSystemRessourcePageResponse extends BasicResponse {

    /**
     * 平台素材资源分页结果
     */
    @Schema(description = "平台素材资源分页结果")
    private MicroServicePage<EsSystemResourceVO> systemResourceVOPage;

}
