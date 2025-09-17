package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.GoodsCateSortDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 修改商品分类排序信息请求对象
 * @author daiyitian
 * @dateTime 2018/11/1 下午4:54
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateBatchModifySortRequest extends BaseRequest {

    private static final long serialVersionUID = 3621716873478690923L;

    /**
     * 批量修改商品分类排序 {@link GoodsCateSortDTO}
     */
    @Schema(description = "批量修改商品分类排序")
    @NotEmpty
    private List<GoodsCateSortDTO> goodsCateSortDTOList;
}
