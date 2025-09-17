package com.wanmi.sbc.crm.api.request.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.bean.enums.PositionOpenFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName RecommendPositionConfigurationModifyIsOpenRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/17 16:43
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionConfigurationModifyIsOpenRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 坑位开关，0：关闭；1：开启
     */
    @Schema(description = "坑位开关，0：关闭；1：开启")
    private PositionOpenFlag isOpen;

}
