package com.wanmi.sbc.goods.api.request.storecate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.dto.StoreCateSortDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 修改店铺分类排序信息请求对象
 * Author: bail
 * Time: 2017/11/13.10:22
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCateBatchModifySortRequest extends BaseRequest {

    private static final long serialVersionUID = 6913151999130370539L;

    /**
     * 批量修改分类排序 {@link StoreCateSortDTO}
     */
    @Schema(description = "批量修改分类排序")
    @NotEmpty
    private List<StoreCateSortDTO> storeCateSortDTOList;

}
