package com.wanmi.sbc.crm.api.request.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.crm.bean.enums.NoPushType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import java.util.List;

/**
 * @ClassName RecommendGoodsManageUpdateNoPushRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/18 14:31
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendGoodsManageUpdateNoPushRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @Max(9223372036854775807L)
    private Long id;

    /**
     * 主键
     */
    @Schema(description = "主键List")
    private List<Long> ids;

    /**
     * 禁推标识 0：可推送；1:禁推
     */
    @Schema(description = "禁推标识 0：可推送；1:禁推")
    private NoPushType noPushType;

}
