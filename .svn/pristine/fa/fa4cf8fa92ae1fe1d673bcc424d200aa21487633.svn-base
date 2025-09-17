package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * es删除分类平台request
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsCateDeleteRequest extends BaseRequest {

    /**
     * 需要删除的idList
     */
    @Schema(description = "需要删除的idList")
    private List<Long> deleteIds;

    /**
     * 替换的分类
     */
    @Schema(description = "替换的分类")
    private GoodsCateVO insteadCate;

    /**
     * 获取默认分类
     */
    @Schema(description = "获取默认分类")
    private boolean isDefault;

}
