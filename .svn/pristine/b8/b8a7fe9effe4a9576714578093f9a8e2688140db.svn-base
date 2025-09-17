package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @description 运费模板更新
 * @author  wur
 * @date: 2022/7/9 17:20
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsFreightTemplateRequest extends BaseRequest {

    private static final long serialVersionUID = -7570905176121732444L;

    /**
     * SUPId
     */
    @Schema(description = "属性id")
    private List<String> goodsIdList;

    /**
     * 运费模板Id
     */
    @Schema(description = "属性id")
    @NotNull
    private Long freightTemplateId;

    /**
     * 运费模板Id
     */
    @Schema(description = "属性id")
    private Long oldFreightTemplateId;

    @Override
    public void checkParam(){
        if (CollectionUtils.isEmpty(goodsIdList) && Objects.isNull(oldFreightTemplateId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
