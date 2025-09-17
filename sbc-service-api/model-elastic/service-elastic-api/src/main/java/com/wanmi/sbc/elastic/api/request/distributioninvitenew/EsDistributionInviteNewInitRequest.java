package com.wanmi.sbc.elastic.api.request.distributioninvitenew;

import com.wanmi.sbc.common.base.EsInitRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 邀请记录分页查询参数
 *
 * @author feitingting
 * @date 2019/2/21
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsDistributionInviteNewInitRequest extends EsInitRequest {

    private static final long serialVersionUID = 8146304890751556770L;
    /**
     * 批量邀新记录表主键List
     */
    @Schema(description = "邀新记录表主键List")
    private List<String> idList;

}