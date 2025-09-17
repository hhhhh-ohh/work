package com.wanmi.sbc.elastic.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class EsBrandUpdateRequest extends BaseRequest {

    @Schema(description = " 是否是删除品牌，false时表示更新品牌")
    private boolean isDelete;

    @Schema(description = "操作品牌实体")
    private GoodsBrandVO goodsBrand;

}
