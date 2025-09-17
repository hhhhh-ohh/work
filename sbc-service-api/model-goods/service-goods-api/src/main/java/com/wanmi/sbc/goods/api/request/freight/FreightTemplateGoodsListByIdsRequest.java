package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 根据批量单品运费模板ids查询单品运费模板请求
 * Created by daiyitian on 2018/10/31.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreightTemplateGoodsListByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 720290633754788177L;

    /**
     * 批量单品运费模板ids
     */
    @Schema(description = "批量单品运费模板ids")
    @NotEmpty
    private List<Long> freightTempIds;

}
