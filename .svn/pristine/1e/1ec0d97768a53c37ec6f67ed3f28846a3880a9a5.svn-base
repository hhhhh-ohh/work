package com.wanmi.sbc.crm.api.request.recommendcatemanage;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RecommendCateManageAddListRequest
 * @Description 分类推荐管理新增参数List
 * @Author lvzhenwei
 * @Date 2020/11/24 18:26
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendCateManageAddListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量新增List
     */
    @Schema(description = "批量新增List")
    List<RecommendCateManageAddRequest> recommendCateManageAddRequestList;
}
