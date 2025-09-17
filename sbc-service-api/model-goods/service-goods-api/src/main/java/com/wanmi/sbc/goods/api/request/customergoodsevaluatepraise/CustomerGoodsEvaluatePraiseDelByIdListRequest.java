package com.wanmi.sbc.goods.api.request.customergoodsevaluatepraise;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除会员商品评价点赞关联表请求参数</p>
 *
 * @author lvzhenwei
 * @date 2019-05-07 14:25:25
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGoodsEvaluatePraiseDelByIdListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量删除-主键List
     */
    @Schema(description = "批量删除-主键List")
    @NotEmpty
    private List<String> idList;
}