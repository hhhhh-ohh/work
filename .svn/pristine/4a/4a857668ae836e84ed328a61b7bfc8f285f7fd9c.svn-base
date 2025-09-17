package com.wanmi.sbc.vas.api.request.recommend.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.NoPushType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RecommendCateManageUpdateNoPushTypeRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/11/19 15:17
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateManageUpdateNoPushTypeRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 主键idList
     */
    @Schema(description = "主键idList")
    private List<Long> ids;

    /**
     * 类目id
     */
    @Schema(description = "类目id")
    private Long cateId;

    /**
     * 禁推类型
     */
    @Schema(description = "禁推类型")
    private NoPushType noPushType;
}
