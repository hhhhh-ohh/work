package com.wanmi.sbc.goods.api.request.storetobeevaluate;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除店铺服务待评价请求参数</p>
 *
 * @author lzw
 * @date 2019-03-20 17:01:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreTobeEvaluateDelByIdListRequest extends BaseRequest {

    private static final long serialVersionUID = 2106874123530242645L;

    /**
     * 批量删除-idList
     */
    @Schema(description = "批量删除-idList")
    @NotEmpty
    private List<String> idList;
}